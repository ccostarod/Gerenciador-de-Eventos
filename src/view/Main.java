package view;

import controller.Departamento;
import model.EspacoFisico;
import model.Solicitacao;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Departamento dep = new Departamento();

        ArrayList<Solicitacao> solicitacaos;
        solicitacaos = dep.lerSolicitacoes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitoesCriadas");

        dep.limparArquivo("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitoesCriadas");
        dep.atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", solicitacaos);

        EspacoFisico espacoFisico =  dep.adicionarSala("Sala 203", 30);
        EspacoFisico espacoFisico2 =  dep.adicionarAuditorio("Auditorio 203", 30);
//        fixa;2023;2023.1;CC;ED1;23;23M34;
        dep.alocarSolicitacao();
        dep.alocarSolicitacao();


    for (Map.Entry<Integer, Hashtable<EspacoFisico, Solicitacao>> entry : dep.getSolicitacoesAlocadas().entrySet()) {
            // Imprimir cada par chave-valor
            System.out.println("Chave: " + entry.getKey() + "\nValor: " + entry.getValue() + "\n\n");
        }
    }



}