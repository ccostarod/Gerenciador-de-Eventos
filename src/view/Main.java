package view;

import controller.Departamento;
import model.EspacoFisico;
import model.Solicitacao;

import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Departamento dep = new Departamento();
        ArrayList<Solicitacao> solicitacaos;
        EspacoFisico espacoFisico1 = dep.adicionarSala("Sala 203", 30);
        EspacoFisico espacoFisico2 = dep.adicionarAuditorio("Auditorio 203", 59);
        EspacoFisico espacoFisico3 = dep.adicionarAuditorio("Auditorio 204", 60);
        dep.carregarAlocados("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesAlocadas");

        if (dep.getAlocacoes() != null) {
            for (Map.Entry<EspacoFisico, List<Solicitacao>> entry : dep.getAlocacoes().entrySet()) {
                EspacoFisico chave = entry.getKey();
                List<Solicitacao> solicitacoes = entry.getValue();

                if (chave != null) {
                    System.out.println("Chave: " + chave);
                    if (solicitacoes != null) {
                        System.out.println("Elementos:");
                        for (Solicitacao solicitacao : solicitacoes) {
                            System.out.println(solicitacao);
                        }
                    }
                    System.out.println(); // Adiciona uma linha em branco para separar as entradas no console
                }
            }
        } else {
            System.out.println("O mapa de alocações é nulo.");
        }
        solicitacaos = dep.lerSolicitacoes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitoesCriadas");
        dep.limparArquivo("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitoesCriadas");
        dep.atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", solicitacaos);

        dep.alocarSolicitacao();
        dep.alocarSolicitacao(); //TENHO QUE AJUSTAR O CODIGO PARA NAO SOBRESCREVER UMA ALOCACAO JA FEITA NO ARQUIVO
        dep.alocarSolicitacao();
    }
}