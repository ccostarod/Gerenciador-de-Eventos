package view;

import controller.Departamento;
import model.EspacoFisico;
import model.Solicitacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Departamento dep = new Departamento();
        ArrayList<Solicitacao> solicitacoes;

        EspacoFisico espacoFisico1 = dep.adicionarSala("Sala 203", 30);
        EspacoFisico espacoFisico2 = dep.adicionarAuditorio("Auditorio 203", 59);
        EspacoFisico espacoFisico3 = dep.adicionarAuditorio("Auditorio 204", 60);
        dep.alocarSolicitacao();
        // Ler e atualizar o mapa de alocações do arquivo
        Map<EspacoFisico, List<Solicitacao>> mapaAtualizado = dep.lerAlocacoesDoArquivo("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesAlocadas.bin", dep.getEspacosFisicos());
        dep.setAlocacoes(mapaAtualizado);
        solicitacoes = dep.lerSolicitacoes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitoesCriadas");
        dep.limparArquivo("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitoesCriadas");
        dep.atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", solicitacoes);

        // Mostrar o conteúdo do mapa de alocações
        Map<EspacoFisico, List<Solicitacao>> alocacoes = dep.getAlocacoes();
        if (alocacoes != null) {
            for (Map.Entry<EspacoFisico, List<Solicitacao>> entry : alocacoes.entrySet()) {
                EspacoFisico chave = entry.getKey();
                List<Solicitacao> solicitacoesList = entry.getValue();

                if (chave != null) {
                    System.out.println("Chave: " + chave);
                    if (solicitacoesList != null) {
                        System.out.println("Elementos:");
                        for (Solicitacao solicitacao : solicitacoesList) {
                            System.out.println(solicitacao);
                        }
                    }
                    System.out.println(); // Adiciona uma linha em branco para separar as entradas no console
                }
            }
        } else {
            System.out.println("O mapa de alocações é nulo.");
        }
        System.out.println("A");

        for (Solicitacao solicitacao : espacoFisico1.getEventosAlocados()){
            System.out.println(solicitacao);
        }



    }
}
