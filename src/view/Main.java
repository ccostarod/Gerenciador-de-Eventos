package view;

import controller.Departamento;
import model.Solicitacao;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Departamento dep = new Departamento();
        ArrayList<Solicitacao> solicitacaos;
        solicitacaos = dep.lerSolicitacoes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitoesCriadas");
        for (Solicitacao x : solicitacaos){
            System.out.println(x);
        }
        dep.atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", solicitacaos);
        solicitacaos.remove(0);
        dep.atualizarSolicitacoesPendentes("C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes", solicitacaos);
    }



}