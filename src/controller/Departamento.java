package controller;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Departamento {
    private List<Solicitacao> solicitacoesPendentes;
    private ArrayList<EspacoFisico> espacosFisicos;
    private Map<EspacoFisico, List<Solicitacao>> alocacoes;
    private List<Solicitacao> solicitacoesAlocadas;

    public Departamento() {
        solicitacoesPendentes = new ArrayList<>();
        espacosFisicos = new ArrayList<>();
        carregarArquivos("src/solicitacoesPendentes");
        espacosFisicos = (ArrayList<EspacoFisico>) lerEspacosFisicos("src/espacosFisicos");
        solicitacoesAlocadas = new ArrayList<>();
        alocacoes = new HashMap<>();
        alocacoes = lerAlocacoesDoArquivo("src/solicitacoesAlocadas.bin", espacosFisicos);
    }

    public ArrayList<EspacoFisico> getEspacosFisicos() {
        return espacosFisicos;
    }

    public void setAlocacoes(Map<EspacoFisico, List<Solicitacao>> alocacoes) {
        this.alocacoes = alocacoes;
    }

    public Map<EspacoFisico, List<Solicitacao>> getAlocacoes() {
        return alocacoes;
    }

    public List<Solicitacao> getSolicitacoesPendentes() {
        return solicitacoesPendentes;
    }

    //LerSolicitacoes, ler todas as solicitacoes contidas no arquivo "solicitacoesNovas" e adiciona a lista de solicitacoes pendentes
    public ArrayList<Solicitacao> lerSolicitacoes(String fileName){
        try(BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
            String line;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while ((line = bf.readLine()) != null){
                String[] separado = line.split(";");
                separado[0] = separado[0].toUpperCase();
                if (separado[0].equals("FIXA")) {
                    Solicitacao solicitacao = new Fixa(Integer.parseInt(separado[1]), separado[2], separado[3], separado[4],
                            Integer.parseInt(separado[5]), separado[6]);
                    solicitacoesPendentes.add(solicitacao);
                }
                else if (separado[0].equals("EVENTUAL")){
                    Solicitacao solicitacao = new Eventual(Integer.parseInt(separado[1]), separado[2], separado[3], separado[4],
                            Integer.parseInt(separado[5]), separado[6], LocalDate.parse(separado[7], dtf), LocalDate.parse(separado[8], dtf));
                    solicitacoesPendentes.add(solicitacao);
                }
            }
            bf.close();
            return (ArrayList<Solicitacao>) solicitacoesPendentes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Função para limpar um arquivo, utilizo para limpar o arquivo "solicitacoesNovas" apos ler ela
    public void limparArquivo(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);

            // Escreve uma string vazia no arquivo para limpar
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Erro ao limpar o arquivo: " + e.getMessage());
        }
    }

    //Atualiza o arquivo de solicitacoesPendentes, sobrescrevendo ele com todos os dados da lista de pendentes.
    public void atualizarSolicitacoesPendentes(String fileName, ArrayList<Solicitacao> solicitacaos){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            if (br.readLine() == null){

                try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
                    for (Solicitacao solicitacao : solicitacaos){
                        if (solicitacao instanceof Fixa){
                            Fixa fixa = (Fixa) solicitacao;
                            // Escreve no arquivo para instâncias de Fixa
                            String linha = String.format("fixa;%d;%s;%s;%s;%d;%s;",
                                    fixa.getAno(), fixa.getSemestre(), fixa.getCurso(),
                                    fixa.getDisciplina(), fixa.getVagas(), fixa.getHorario());
                            bw.write(linha);
                            bw.newLine();
                        }
                        else{
                            Eventual eventual = (Eventual) solicitacao;
                            // Escreve no arquivo para instâncias de Eventual
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String linha = String.format("eventual;%d;%s;%s;%s;%d;%s;%s;%s;",
                                    eventual.getAno(), eventual.getSemestre(), eventual.getCurso(),
                                    eventual.getFinalidade(), eventual.getVagas(), eventual.getHorario(),
                                    eventual.getDataInicio().format(formatter), eventual.getDataFim().format(formatter));
                            bw.write(linha);
                            bw.newLine();
                        }
                    }
                }
            }
            else{
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
                    for (Solicitacao solicitacao : solicitacaos){
                        if (solicitacao instanceof Fixa){
                            Fixa fixa = (Fixa) solicitacao;
                            // Escreve no arquivo para instâncias de Fixa
                            String linha = String.format("fixa;%d;%s;%s;%s;%d;%s;",
                                    fixa.getAno(), fixa.getSemestre(), fixa.getCurso(),
                                    fixa.getDisciplina(), fixa.getVagas(), fixa.getHorario());
                            bw.write(linha);
                            bw.newLine();
                        }
                        else{
                            Eventual eventual = (Eventual) solicitacao;
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            // Escreve no arquivo para instâncias de Eventual
                            String linha = String.format("eventual;%d;%s;%s;%s;%d;%s;%s;%s",
                                    eventual.getAno(), eventual.getSemestre(), eventual.getCurso(),
                                    eventual.getFinalidade(), eventual.getVagas(), eventual.getHorario(),
                                    eventual.getDataInicio().format(formatter), eventual.getDataFim().format(formatter));
                            bw.write(linha);
                            bw.newLine();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Limpa solicitacoesPendentes, e depois adiciona tudo que esta no arquivo na lista de novo. Assim pegando dados novos.
    public void carregarArquivos(String fileName){
        solicitacoesPendentes.clear();
        solicitacoesPendentes = lerSolicitacoes(fileName);
    }

    //Metodo responsavel por alocar uma solicitacao, funciona como uma pilha, ou seja, adiciona quem esta no comeco da lista de pendentes.
    // caso nao consiga alocar uma solicitacao, ele faz com que a solicitacao vá para o final da lista
    // caso consiga alocar, a solicitacao sai da lista de pendentes.
    public EspacoFisico alocarSolicitacao() {
        if (!solicitacoesPendentes.isEmpty()){
            Solicitacao solicitacao = solicitacoesPendentes.get(0);
            EspacoFisico melhorEspacoFisico = null;
            int menor = Integer.MAX_VALUE;
            int i = 0;
            if (solicitacao instanceof Fixa){
                for (EspacoFisico key : espacosFisicos){
                    if (key.getEventosAlocados().isEmpty()){
                        int diferenca = key.getCapacidade() - solicitacao.getVagas();
                        if (diferenca >= 0){
                            if (diferenca < menor){
                                menor = diferenca;
                                melhorEspacoFisico = key;
                            }
                        }
                    }
                    else{
                        if (verificarConflito(solicitacao,key)){
                            continue;
                        }
                        else{
                            int diferenca = key.getCapacidade() - solicitacao.getVagas();
                            if (diferenca >= 0){
                                if (diferenca < menor){
                                    menor = diferenca;
                                    melhorEspacoFisico = key;
                                    if (!espacosFisicos.isEmpty())
                                        i = espacosFisicos.size() - 1;
                                    else
                                        i = 0;
                                }
                            }
                        }
                    }
                }
                if (melhorEspacoFisico != null && !existeSolicitacaoAlocada(solicitacao)){
                    melhorEspacoFisico.addEvento(solicitacao);
                    adicionarAlocacao(melhorEspacoFisico, solicitacao);
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("src/solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    escreverAlocacoesEmArquivo("src/solicitacoesAlocadas.bin");
                    return melhorEspacoFisico;
                }
                else if (existeSolicitacaoAlocada(solicitacao)){
                    System.out.println("Ja existe uma solicitacao igual alocada!");
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("src/solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    return null;
                }
                else {
                    System.out.println("Nao ha local para alocar essa Solicitacao");
                    solicitacoesPendentes.remove(0);
                    solicitacoesPendentes.add(solicitacao);
                    atualizarSolicitacoesPendentes("src/solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    return null;
                }
            }
            else if (solicitacao instanceof Eventual){
                for (EspacoFisico key : espacosFisicos){
                    if (key instanceof Auditorio && key.getEventosAlocados().isEmpty()){
                        int diferenca = key.getCapacidade() - solicitacao.getVagas();
                        if (diferenca >= 0){
                            if (diferenca <= menor){
                                menor = diferenca;
                                melhorEspacoFisico = key;
                                if (!espacosFisicos.isEmpty())
                                    i = espacosFisicos.size() - 1 ;
                                else
                                    i = 0;
                            }
                        }
                    }
                    else if(key instanceof Auditorio){
                        if (verificarConflitosEventual(solicitacao, key)){
                            System.out.println("Houve Conflito");
                            break;
                        }
                        else{
                            int diferenca = key.getCapacidade() - solicitacao.getVagas();
                            if (diferenca >= 0) {
                                if (diferenca <= menor){
                                    menor = diferenca;
                                    melhorEspacoFisico = key;
                                    if (!espacosFisicos.isEmpty())
                                        i = espacosFisicos.size() - 1 ;
                                    else
                                        i = 0;
                                }
                            }
                        }
                    }
                }
                if (melhorEspacoFisico != null && !existeSolicitacaoAlocada(solicitacao)){
                    melhorEspacoFisico.addEvento(solicitacao);
                    adicionarAlocacao(melhorEspacoFisico, solicitacao);
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("src/solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    escreverAlocacoesEmArquivo("src/solicitacoesAlocadas.bin");
                    return melhorEspacoFisico;
                }
                else if (existeSolicitacaoAlocada(solicitacao)){
//                    System.out.println("Ja existe uma solicitacao igual alocada!");
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("src/solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    return null;
                }
                else {
//                    System.out.println("Nao ha local para alocar essa Solicitacao");
                    solicitacoesPendentes.remove(0);
                    solicitacoesPendentes.add(solicitacao);
                    atualizarSolicitacoesPendentes("src/solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    return null;
                }
            }
        }
//        System.out.println("Lista de pendentes esta vazia");
        return null;
    }

    //Metodo para ver se ja nao existe uma solicitacao igual no hashmap de alocados, utilizo ele em outros metodos.
    public boolean existeSolicitacaoAlocada(Solicitacao solicitacao) {
        if (alocacoes != null){
            for (Map.Entry<EspacoFisico, List<Solicitacao>> entry : alocacoes.entrySet()) {
                List<Solicitacao> solicitacoesAlocadas = entry.getValue();
                for (Solicitacao solicitacaoAlocada : solicitacoesAlocadas) {
                    if (solicitacaoAlocada.equals(solicitacao)) {
                        return true; // Já existe uma solicitação igual alocada
                    }
                }
            }
        }
        return false; // Não encontrou uma solicitação igual alocada
    }

    //Metodo para verificar conflito de horarios e semestre principalmente, funciona para solicitacao do tipo Fixa
    public boolean verificarConflito(Solicitacao solicitacao, EspacoFisico espacoFisico) {
        boolean dias = false, turnos = false, horarios = false, semestres = false;
        for (Solicitacao x : espacoFisico.getEventosAlocados()) {
            for (String dia1 : x.getDia()){
                for (String dia2 : solicitacao.getDia()){
                    if (dia1.equals(dia2)) {
                        dias = true;
                        break;
                    }
                }
            }
            if (x.getTurno().equals(solicitacao.getTurno())){
                turnos = true;
            }
            for (String horario1 : x.getHorarios()){
                for (String horario2 : solicitacao.getHorarios()){
                    if (horario1.equals(horario2)){
                        horarios = true;
                        break;
                    }
                }
            }
            if (x.getSemestre().equals(solicitacao.getSemestre())){
                semestres = true;
            }
        }
        return dias && turnos && horarios && semestres;
    }

    //Basicamente integra o metodo anterior, mas verifica se as datas conflitem.
    public boolean verificarConflitosEventual(Solicitacao solicitacao, EspacoFisico espacoFisico) {
        boolean conflitosGerais = verificarConflito(solicitacao, espacoFisico);

        if (conflitosGerais && espacoFisico instanceof Auditorio && solicitacao instanceof Eventual) {
            for (Solicitacao x : espacoFisico.getEventosAlocados()) {
                if (x instanceof Eventual) {
                    boolean sobreposicaoDatas = ((Eventual) x).getDataInicio().isBefore(((Eventual) solicitacao).getDataFim()) &&
                            ((Eventual) x).getDataFim().isAfter(((Eventual) solicitacao).getDataInicio());

                    if (sobreposicaoDatas) {
                        return true;  // Há sobreposição de datas, portanto, retorna true
                    }
                }
            }
        }

        return conflitosGerais;
    }

    //Escreve as alocacoes no arquivo "solicitacoesAlocadas" diferenta das outras acima, nesse usei ObjectOutputStream.
    public void escreverAlocacoesEmArquivo(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(alocacoes);
        } catch (IOException e) {
            throw new RuntimeException("Error writing allocations to the file: " + e.getMessage(), e);
        }
    }

    //Passando os parametros EspacoFisico e Solicitacao, ele adiciona no hashmap alocacoes, verificando se a chave ja esta ou nao contida no hashmap
    public void adicionarAlocacao(EspacoFisico espacoFisico, Solicitacao solicitacao) {
        if (alocacoes != null) {
            if (alocacoes.containsKey(espacoFisico)) {
                List<Solicitacao> eventosAlocados = new ArrayList<>(alocacoes.get(espacoFisico));
                eventosAlocados.add(solicitacao);
                alocacoes.put(espacoFisico, eventosAlocados);
            }
            else {
                alocacoes.put(espacoFisico, new ArrayList<>(Collections.singletonList(solicitacao)));
            }
        }
        else {
            alocacoes = new HashMap<>();
            alocacoes.put(espacoFisico, new ArrayList<>(Collections.singletonList(solicitacao)));
        }

        escreverAlocacoesEmArquivo("src/solicitacoesAlocadas.bin");
    }
    // Serve apenas para ver se um arquivo esta vazio, utilizei no metodo abaixo.
    public boolean isFileEmpty(String fileName) {
        File file = new File(fileName);
        return file.length() == 0;
    }

    //Aqui ele ler as alocacoes presentes no arquivo "solicitacoesAlocadas" e traz de volta por arquivo, alem disso, reassocia a solicitacao
    // com seu devido EspacoFisco
    public Map<EspacoFisico, List<Solicitacao>> lerAlocacoesDoArquivo(String fileName, List<EspacoFisico> espacosFisicos) {
        try {
            if (isFileEmpty(fileName)) {
                return null;
            }
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
            // Verificar se o arquivo está vazio
            Map<EspacoFisico, List<Solicitacao>> alocacoesLidas = (Map<EspacoFisico, List<Solicitacao>>) inputStream.readObject();

            // Iterar sobre as alocações lidas do arquivo
            for (Map.Entry<EspacoFisico, List<Solicitacao>> entry : alocacoesLidas.entrySet()) {
                EspacoFisico espacoFisico = espacoFisicoExistente(entry.getKey(), espacosFisicos);
                if (espacoFisico != null) {
                    for (Solicitacao solicitacao : entry.getValue()) {
                        if (!solicitacaoExiste(solicitacao, solicitacoesAlocadas)) {
                            espacoFisico.addEvento(solicitacao);
                            solicitacoesAlocadas.add(solicitacao);
                        }
                    }
                }
            }
            return alocacoesLidas;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao ler alocações do arquivo: " + e.getMessage(), e);
        }
    }
    //Utilizo para saber se o espacoFisico existe na lista de espacosFisicos
    private EspacoFisico espacoFisicoExistente(EspacoFisico novoEspacoFisico, List<EspacoFisico> espacosFisicos) {
        for (EspacoFisico espacoFisicoExistente : espacosFisicos) {
            if (espacoFisicoExistente.getNome().equals(novoEspacoFisico.getNome()) &&
                    espacoFisicoExistente.getCapacidade() == novoEspacoFisico.getCapacidade()) {
                return espacoFisicoExistente;
            }
        }
        return null;
    }
    //Utilizo para saber se o solicitacao existe na lista de solicitacoes
    public Boolean solicitacaoExiste(Solicitacao novaSolicitacao, List<Solicitacao> solicitacoes) {
        for (Solicitacao solicitacaoExistente : solicitacoes) {
            if (solicitacaoExistente.equals(novaSolicitacao)) {
                return true;
            }
        }
        return false;
    }

    //Basicamente esse gera o relatorio por Curso
    public ArrayList<Solicitacao> relatorioPorCurso(String nomeCurso){

        nomeCurso = nomeCurso.toUpperCase();
        ArrayList<Solicitacao> relatorioGerado = new ArrayList<>();
        for (EspacoFisico espacoFisico : espacosFisicos){

            for (Solicitacao solicitacao : espacoFisico.getEventosAlocados()){
                String aux = solicitacao.getCurso();
                solicitacao.setCurso(solicitacao.getCurso().toUpperCase());
                if (solicitacao.getCurso().equalsIgnoreCase(nomeCurso)){
                    relatorioGerado.add(solicitacao);
                }
                solicitacao.setCurso(aux);
            }
        }
        return relatorioGerado;
    }

    //Esse gera o relatorio por espacoFisico
    public ArrayList<Solicitacao> relatorioPorEspacoParaFixa(String nomeEspaco){

        nomeEspaco = nomeEspaco.toUpperCase();

        ArrayList<Solicitacao> relatorioGerado = new ArrayList<>();

        for (EspacoFisico espacoFisico : espacosFisicos){
            String aux = espacoFisico.getNome();
            espacoFisico.setNome(espacoFisico.getNome().toUpperCase());
            if (espacoFisico.getNome().equalsIgnoreCase(nomeEspaco)){
                if (!espacoFisico.getEventosAlocados().isEmpty()){
                    relatorioGerado.addAll(espacoFisico.getEventosAlocados());
                }
                espacoFisico.setNome(aux);
            }
        }

        return  relatorioGerado;
    }


    //Metodo para inserir um espacoFisico ao arquivo "espacosFisicos" alem disso, ele lerEspacoFisicos, alocando todos os espacos
    // la contidos na lista de espacosFisicos
    public boolean inserirEspacoFisico(String tipo, String nome, int capacidade, String fileName) {
        if (salaExistente(nome, fileName)) {
            System.out.println("Já existe uma sala com o nome '" + nome + "'. Não é possível adicionar outra sala com o mesmo nome.");
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String linha = tipo.toUpperCase() + ";" + nome + ";" + capacidade + ";\n";
            writer.write(linha);
            System.out.println("Adicionado com Sucesso");
            lerEspacosFisicos(fileName);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao inserir espaço físico no arquivo: " + e.getMessage());
        }
        return false;
    }

    //Uso na funcao acima para saber se ja existe uma sala com aquele nome no arquivo.
    private boolean salaExistente(String nome, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 2 && partes[1].equalsIgnoreCase(nome)) {
                    return true;  // Já existe uma sala com o mesmo nome
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao verificar existência de sala no arquivo: " + e.getMessage());
        }
        return false;  // Não encontrou sala com o mesmo nome
    }

    //Essa aqui basicamente ler o arquivo de "espacosFisicos" e adiciona todos os elementos de volta a lista de espacos, sobrescrevendo
    // os que estavam la (desatualizados).
    public List<EspacoFisico> lerEspacosFisicos(String fileName) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            espacosFisicos.clear();
            String linha;
            while ((linha = reader.readLine()) != null) {
                EspacoFisico espacoFisico = construirEspacoFisico(linha);
                if (espacoFisico != null) {
                    espacosFisicos.add(espacoFisico);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler espacos fisicos do arquivo: " + e.getMessage());
        }

        return espacosFisicos;
    }

    //Metodo utilizado acima para facilitar a criacao do objeto.
    private EspacoFisico construirEspacoFisico(String linha) {
        String[] partes = linha.split(";");
        if (partes.length >= 3) {
            String tipo = partes[0].trim().toUpperCase();
            String nome = partes[1].trim();
            int capacidade = Integer.parseInt(partes[2].trim());

            if (tipo.equalsIgnoreCase("SALA")) {
                return new Sala(nome, capacidade);
            } else if (tipo.equalsIgnoreCase("AUDITORIO")) {
                return new Auditorio(nome, capacidade);
            }
        }

        return null;
    }

    //Insere uma solicitacao Fixa ao arquivo de pendentes.
    public boolean inserirSolicitacaoPendenteFixa(String disciplina, int ano, String semestre, String curso, int vagas, String horario, String fileName) {
        if (solicitacaoPendenteExistente("fixa", disciplina, ano, semestre, curso, horario, fileName)) {
            System.out.println("Já existe uma solicitação pendente (Fixa) com os mesmos detalhes.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String linha = "fixa;" + ano + ";" + semestre + ";" + curso + ";" + disciplina + ";" + vagas + ";" + horario + ";\n";
            writer.write(linha);
            System.out.println("Solicitação pendente (Fixa) adicionada com sucesso.");
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao inserir solicitação pendente (Fixa) no arquivo: " + e.getMessage());
            return false;
        }
    }

    //Insere uma Eventual
    public boolean inserirSolicitacaoPendenteEventual(String finalidade, int ano, String semestre, String curso, int vagas, String horario, LocalDate dataInicio, LocalDate dataFim, String fileName) {
        if (solicitacaoPendenteExistente("eventual", finalidade, ano, semestre, curso, horario, fileName)) {
            System.out.println("Já existe uma solicitação pendente (Eventual) com os mesmos detalhes.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String linha = "eventual;" + ano + ";" + semestre + ";" + curso + ";" + finalidade + ";" + vagas + ";" + horario + ";" + dataInicio.format(formatter) + ";" + dataFim.format(formatter) + ";\n";
            writer.write(linha);
            System.out.println("Solicitação pendente (Eventual) adicionada com sucesso.");
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao inserir solicitação pendente (Eventual) no arquivo: " + e.getMessage());
            return false;
        }
    }

    //Serve para verificar se já nao tem algo igual na lista de pendentes, assim caso tenho, nao adicionar.
    private boolean solicitacaoPendenteExistente(String tipo, String detalhes, int ano, String semestre, String curso, String horario, String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(";");
                String tipoExistente = partes[0];

                // Comparar detalhes apenas se for do mesmo tipo (Fixa ou Eventual)
                if (tipo.equalsIgnoreCase(tipoExistente) && detalhes.equalsIgnoreCase(partes[4]) && ano == Integer.parseInt(partes[1])
                        && semestre.equals(partes[2]) && curso.equals(partes[3]) && horario.equals(partes[6])) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao verificar duplicata no arquivo: " + e.getMessage());
        }
        return false;
    }

    public void limparArquivoGeral(){
        limparArquivo("src/solicitacoesAlocadas.bin");
        limparArquivo("src/solicitacoesPendentes");
        limparArquivo("src/espacosFisicos");
    }
}
