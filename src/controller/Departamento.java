package controller;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Departamento {
    private List<Solicitacao> solicitacoesPendentes;
    private Hashtable<String, EspacoFisico> espacosFisicos;
    private Hashtable<EspacoFisico, Solicitacao> dados;
    private Hashtable<Integer, Hashtable<EspacoFisico, Solicitacao>> solicitacoesAlocadas;

    public Departamento() {
        solicitacoesPendentes = new ArrayList<>();
        espacosFisicos = new Hashtable<>();
        dados = new Hashtable<>();
        solicitacoesAlocadas = new Hashtable<Integer, Hashtable<EspacoFisico, Solicitacao>>();
        carregarArquivos("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes");
    }

    public Hashtable<EspacoFisico, Solicitacao> getDados() {
        return dados;
    }

    public Hashtable<Integer, Hashtable<EspacoFisico, Solicitacao>> getSolicitacoesAlocadas() {
        return solicitacoesAlocadas;
    }

    public Hashtable<String, EspacoFisico> getEspacosFisicos() {
        return espacosFisicos;
    }

    public Hashtable<EspacoFisico, Solicitacao> getEventosAlocados() {
        return dados;
    }

    public List<Solicitacao> getSolicitacoesPendentes() {
        return solicitacoesPendentes;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void carregarArquivos(String fileName){
        solicitacoesPendentes = lerSolicitacoes(fileName);
    }

    public EspacoFisico adicionarSala(String nome, int capacidade){
        EspacoFisico espacoFisico = new Sala(nome, capacidade);
        espacosFisicos.put(espacoFisico.getNome(), espacoFisico);
        return espacoFisico;
    }

    public EspacoFisico adicionarAuditorio(String nome, int capacidade){
        EspacoFisico espacoFisico = new Auditorio(nome, capacidade);
        espacosFisicos.put(espacoFisico.getNome(), espacoFisico);
        return  espacoFisico;
    }


    public void alocarSolicitacao(){
        if (!solicitacoesPendentes.isEmpty()){
            Solicitacao solicitacao = solicitacoesPendentes.get(0);
            EspacoFisico melhorEspacoFisico = null;
            int menor = Integer.MAX_VALUE;
            int i = 0;
            if (solicitacao instanceof Fixa){
                for (String key : espacosFisicos.keySet()){
                    if (espacosFisicos.get(key).getEventosAlocados().isEmpty()){
                        int diferenca = espacosFisicos.get(key).getCapacidade() - solicitacao.getVagas();
                        if (diferenca >= 0){
                            if (diferenca < menor){
                                menor = diferenca;
                                melhorEspacoFisico = espacosFisicos.get(key);
                            }
                        }
                    }
                    else{
                        if (verificarConflito(solicitacao,espacosFisicos.get(key))){
                            System.out.println("Houve conflito!");
                        }
                        else{
                            int diferenca = espacosFisicos.get(key).getCapacidade() - solicitacao.getVagas();
                            if (diferenca >= 0){
                                if (diferenca < menor){
                                    menor = diferenca;
                                    melhorEspacoFisico = espacosFisicos.get(key);
                                    if (!espacosFisicos.isEmpty())
                                        i = espacosFisicos.size() -1 ;
                                    else
                                        i = 0;
                                }
                            }
                        }
                    }
                }
                if (melhorEspacoFisico != null){
                    melhorEspacoFisico.addEvento(solicitacao);
                    dados.put(melhorEspacoFisico, solicitacao);
                    solicitacoesAlocadas.put(i, dados);
                    solicitacoesPendentes.remove(0);
                    atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
                }
            }
            else if (solicitacao instanceof Eventual){
                for (String key : espacosFisicos.keySet()){
                    if (espacosFisicos.get(key) instanceof Auditorio && espacosFisicos.get(key).getEventosAlocados().isEmpty()){
                        int diferenca = espacosFisicos.get(key).getCapacidade() - solicitacao.getVagas();
                        if (diferenca >= 0){
                            if (diferenca <= menor){
                                menor = diferenca;
                                melhorEspacoFisico = espacosFisicos.get(key);
                                if (!espacosFisicos.isEmpty())
                                    i = espacosFisicos.size() -1 ;
                                else
                                    i = 0;
                            }
                        }
                    }
                    else if(espacosFisicos.get(key) instanceof Auditorio){
                        if (verificarConflitosEventual(solicitacao, espacosFisicos.get(key))){
                            System.out.println("Houve Conflito");
                            break;
                        }
                        else{
                            int diferenca = espacosFisicos.get(key).getCapacidade() - solicitacao.getVagas();
                            if (diferenca >= 0) {
                                if (diferenca <= menor){
                                    menor = diferenca;
                                    melhorEspacoFisico = espacosFisicos.get(key);
                                    if (!espacosFisicos.isEmpty())
                                        i = espacosFisicos.size() -1 ;
                                    else
                                        i = 0;
                                }
                            }
                        }
                    }
                }
                if (melhorEspacoFisico != null){
                    melhorEspacoFisico.addEvento(solicitacao);
                    dados.put(melhorEspacoFisico, solicitacao);
                    solicitacoesAlocadas.put(i, dados);
                    solicitacoesPendentes.remove(0);
                    System.out.println("Foi");
                    atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", (ArrayList<Solicitacao>) solicitacoesPendentes);
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
    public void atualizarSolicitacoesAlocadas(String fileName, Hashtable<Integer, Hashtable<EspacoFisico, Solicitacao>>){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            for (Integer x : solicitacoesAlocadas.keySet()){
                String linha = null;
                linha = ("%d|%s;%d|%d%s%s%s");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //todo gerarRelatorio;
    //todo gravarDecisoes;
}

