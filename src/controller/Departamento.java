package controller;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Departamento {
    private List<Solicitacao> solicitacoesPendentes;
    private ArrayList<EspacoFisico> espacosFisicos;
    private Map<EspacoFisico, List<Solicitacao>> alocacoes = null;

    public Departamento() {
        solicitacoesPendentes = new ArrayList<>();
        espacosFisicos = new ArrayList<>();
        carregarArquivos("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes");
        alocacoes = new HashMap<>();
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
    public void limparArquivo(String fileName) {
        try {
            // Cria um objeto FileWriter com o caminho do arquivo
            FileWriter fileWriter = new FileWriter(fileName);

            // Escreve uma string vazia no arquivo para limpá-lo
            fileWriter.write("");

            // Fecha o FileWriter
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Erro ao limpar o arquivo: " + e.getMessage());
        }
    }
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
                            String linha = String.format("eventual;%d;%s;%s;%s;%d;%s;%s;%s;",
                                    eventual.getAno(), eventual.getSemestre(), eventual.getCurso(),
                                    eventual.getFinalidade(), eventual.getVagas(), eventual.getHorario(),
                                    eventual.getDataInicio(), eventual.getDataFim());
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
    public void carregarArquivos(String fileName){
        solicitacoesPendentes = lerSolicitacoes(fileName);
    }
    public EspacoFisico adicionarSala(String nome, int capacidade){
        EspacoFisico espacoFisico = new Sala(nome, capacidade);
        espacosFisicos.add(espacoFisico);
        return espacoFisico;
    }
    public EspacoFisico adicionarAuditorio(String nome, int capacidade){
        EspacoFisico espacoFisico = new Auditorio(nome, capacidade);
        espacosFisicos.add(espacoFisico);
        return  espacoFisico;
    }

    public void alocarSolicitacao() throws IOException {
        if (!solicitacoesPendentes.isEmpty()){
            Solicitacao solicitacao = solicitacoesPendentes.get(0);
            EspacoFisico melhorEspacoFisico = null;
            int menor = Integer.MAX_VALUE;
            int i = 0;
            if (solicitacao instanceof Fixa){
                for (EspacoFisico key : espacosFisicos){
                    if (key.getEventosAlocados().isEmpty()){
                        System.out.println("Aaaaaa");
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
                            System.out.println("Houve conflito!");
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
                if (melhorEspacoFisico != null){
                    melhorEspacoFisico.addEvento(solicitacao);
                    adicionarAlocacao(melhorEspacoFisico, solicitacao);
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    escreverAlocacoesEmArquivo("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesAlocadas.bin");
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
                if (melhorEspacoFisico != null){
                    melhorEspacoFisico.addEvento(solicitacao);
                    adicionarAlocacao(melhorEspacoFisico, solicitacao);
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    escreverAlocacoesEmArquivo("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesAlocadas.bin");
                }
            }
        }
    }

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
    public void escreverAlocacoesEmArquivo(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(alocacoes);
        } catch (IOException e) {
            throw new RuntimeException("Error writing allocations to the file: " + e.getMessage(), e);
        }
    }

    // Method to add an allocation to its respective key without overwriting the rest of the elements in the list
    public void adicionarAlocacao(EspacoFisico espacoFisico, Solicitacao solicitacao) {
        if (alocacoes.containsKey(espacoFisico)) {
            List<Solicitacao> eventosAlocados = new ArrayList<>(alocacoes.get(espacoFisico));
            eventosAlocados.add(solicitacao);
            alocacoes.put(espacoFisico, eventosAlocados);
        } else {
            // If the key does not exist yet, create a new entry in the map
            alocacoes.put(espacoFisico, new ArrayList<>(Collections.singletonList(solicitacao)));
        }
        // After adding the allocation, write to the file to keep the updates persistent
        escreverAlocacoesEmArquivo("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesAlocadas.bin");
    }

//    @SuppressWarnings("unchecked") // Suppressing unchecked warning for casting
//    public Map<EspacoFisico, List<Solicitacao>> lerAlocacoesDoArquivo(String fileName) {
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
//            return (Map<EspacoFisico, List<Solicitacao>>) inputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException("Error reading allocations from the file: " + e.getMessage(), e);
//        }
//    }
    @SuppressWarnings("unchecked")
    public Map<EspacoFisico, List<Solicitacao>> lerAlocacoesDoArquivo(String fileName, List<EspacoFisico> espacosFisicos) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            Map<EspacoFisico, List<Solicitacao>> alocacoesLidas = (Map<EspacoFisico, List<Solicitacao>>) inputStream.readObject();

            // Iterar sobre as alocações lidas do arquivo
            for (Map.Entry<EspacoFisico, List<Solicitacao>> entry : alocacoesLidas.entrySet()) {
                EspacoFisico espacoFisico = espacoFisicoExistente(entry.getKey(), espacosFisicos);
                if (espacoFisico != null) {
                    // Atribuir a lista de solicitações ao EspacoFisico correspondente
                    espacoFisico.setEventosAlocados(entry.getValue());
                }
            }
            return alocacoesLidas;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao ler alocações do arquivo: " + e.getMessage(), e);
        }
    }

    private EspacoFisico espacoFisicoExistente(EspacoFisico novoEspacoFisico, List<EspacoFisico> espacosFisicos) {
        for (EspacoFisico espacoFisicoExistente : espacosFisicos) {
            if (espacoFisicoExistente.getNome().equals(novoEspacoFisico.getNome()) &&
                    espacoFisicoExistente.getCapacidade() == novoEspacoFisico.getCapacidade()) {
                return espacoFisicoExistente;
            }
        }
        return null;
    }
}




