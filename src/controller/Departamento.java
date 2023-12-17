package controller;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public void alocarSolicitacao(){
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
                    alocacoes.put(melhorEspacoFisico, melhorEspacoFisico.getEventosAlocados());
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    escreverHashMapEmArquivo(alocacoes, "C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesAlocadas");
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

                    alocacoes.put(melhorEspacoFisico, melhorEspacoFisico.getEventosAlocados());
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                    escreverHashMapEmArquivo(alocacoes, "C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesAlocadas");
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
    private void escreverHashMapEmArquivo(Map<EspacoFisico, List<Solicitacao>> alocacoes, String fileName) { //ARRUMAR ESSA AQUI!!!!
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Map.Entry<EspacoFisico, List<Solicitacao>> entry : alocacoes.entrySet()) {
                // Escreve a chave (EspacoFisico) apenas uma vez
                bw.write("key: " + entry.getKey().getNome());
                bw.newLine();

                // Escreve os elementos (Solicitacao) associados à chave
                List<Solicitacao> solicitacoes = entry.getValue();
                for (Solicitacao solicitacao : solicitacoes) {
                    // Escreve o elemento (Solicitacao)
                    if (solicitacao instanceof Fixa) {
                        bw.write("elemento: " + ((Fixa) solicitacao).toStringArquivo());
                        bw.newLine();
                    } else if (solicitacao instanceof Eventual) {
                        bw.write("elemento: " + ((Eventual) solicitacao).toStringArquivo());
                        bw.newLine();
                    }
                }

                // Adiciona uma linha em branco para separar as entradas no arquivo
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void carregarAlocados(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            EspacoFisico chaveAtual = null;
            List<Solicitacao> elementosAtuais = new ArrayList<>();
            ArrayList<String> nomesEspacos = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.startsWith("key: ")) {
                    // Nova chave encontrada
                    String nomeEspacoFisico = line.substring("key: ".length());
                    chaveAtual = encontrarEspacoFisicoPorNome(nomeEspacoFisico);
                    elementosAtuais = new ArrayList<>();
                } else if (line.startsWith("elemento: ")) {
                    // Elemento associado à chave atual
                    String elementoInfo = line.substring("elemento: ".length());
                    Solicitacao solicitacao = criarSolicitacaoAPartirDaString(elementoInfo);
                    elementosAtuais.add(solicitacao);
                } else if (line.isEmpty()) {
                    // Linha em branco indica término de entrada
                    if (chaveAtual != null) {
                        System.out.println("DDDDD");
                        alocacoes.put(chaveAtual, elementosAtuais);
                        chaveAtual = null;
                        elementosAtuais = new ArrayList<>();
                    }
                }
            }
            if (chaveAtual != null && !elementosAtuais.isEmpty()) {
                getAlocacoes().put(chaveAtual, elementosAtuais);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private EspacoFisico encontrarEspacoFisicoPorNome(String nome) {
        for (EspacoFisico espacoFisico : espacosFisicos) {
            if (espacoFisico.getNome().equals(nome)) {
                return espacoFisico;
            }
        }
        return null; // Se nenhum EspacoFisico com o nome fornecido for encontrado
    }

    private Solicitacao criarSolicitacaoAPartirDaString(String info) {
        String[] separado = info.split(";");

        separado[0] = separado[0].toUpperCase();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (separado[0].equals("FIXA")) {
            return new Fixa(Integer.parseInt(separado[1]), separado[2], separado[3], separado[4],
                    Integer.parseInt(separado[5]), separado[6]);
        } else if (separado[0].equals("EVENTUAL")) {
            return new Eventual(Integer.parseInt(separado[1]), separado[2], separado[3], separado[4],
                    Integer.parseInt(separado[5]), separado[6], LocalDate.parse(separado[7], dtf), LocalDate.parse(separado[8], dtf));
        }

        return null; // Caso o tipo de Solicitacao não seja reconhecido
    }


}

