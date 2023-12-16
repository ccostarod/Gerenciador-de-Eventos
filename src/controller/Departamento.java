package controller;

import model.Eventual;
import model.Fixa;
import model.Solicitacao;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Departamento {
    private List<Solicitacao> solicitacoesNovas;

    public Departamento() {
        solicitacoesNovas = new ArrayList<>();

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
                    solicitacoesNovas.add(solicitacao);
                }
                else if (separado[0].equals("EVENTUAL")){
                    Solicitacao solicitacao = new Eventual(Integer.parseInt(separado[1]), separado[2], separado[3], separado[4],
                            Integer.parseInt(separado[5]), separado[6], LocalDate.parse(separado[7], dtf), LocalDate.parse(separado[8], dtf));
                    solicitacoesNovas.add(solicitacao);
                }
            }
            bf.close();
            return (ArrayList<Solicitacao>) solicitacoesNovas;
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //todo carregarArquivos(String fileName);
    //todo alocarSolicitacoes(Solicitacao solicitacoes.get(0));
    //todo gerarRelatorio;
    //todo gravarDecisoes;
}
