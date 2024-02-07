package view;

import controller.Departamento;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
public class VisualizacaoElementos extends javax.swing.JFrame {
    Departamento departamento = new Departamento();
    ArrayList<Solicitacao> solicitacoes = new ArrayList<>();

    public VisualizacaoElementos() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        btnListaPend = new javax.swing.JButton();
        btnListaAloc = new javax.swing.JButton();
        ListaEspacos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        btnRetornar = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Visualização");

        btnListaPend.setText("Lista de Pendentes");
        btnListaPend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaPendActionPerformed(evt);
            }
        });

        btnListaAloc.setText("Lista de Alocados");
        btnListaAloc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaAlocActionPerformed(evt);
            }
        });

        ListaEspacos.setText("Lista de Espaços");
        ListaEspacos.setToolTipText("");
        ListaEspacos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaEspacosActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Visualizar");

        btnRetornar.setText("Retornar");
        btnRetornar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRetornarMouseClicked(evt);
            }
        });
        jMenuBar1.add(btnRetornar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(252, 252, 252)
                                                .addComponent(jLabel1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(144, 144, 144)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnListaPend, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(ListaEspacos, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                                                                .addComponent(btnListaAloc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(351, 351, 351)
                                                .addComponent(jLabel2)))
                                .addContainerGap(148, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(49, 49, 49)
                                .addComponent(btnListaPend, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnListaAloc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ListaEspacos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void btnRetornarMouseClicked(java.awt.event.MouseEvent evt) {
        MenuDeOpcoes tela = new MenuDeOpcoes();
        this.dispose();
        tela.setVisible(true);
    }

    private void btnListaPendActionPerformed(java.awt.event.ActionEvent evt) {
        if (departamento.getSolicitacoesPendentes().isEmpty()) {
            JOptionPane.showMessageDialog(null, "A lista de solicitações pendentes está vazia");
        } else {
            StringBuilder mensagem = new StringBuilder();

            for (Solicitacao solicitacao : departamento.getSolicitacoesPendentes()) {
                mensagem.append(solicitacao).append("\n");
            }

            JOptionPane.showMessageDialog(null, mensagem.toString());
        }
    }

    private void btnListaAlocActionPerformed(java.awt.event.ActionEvent evt) {

        if (departamento.getAlocacoes() == null) {
            JOptionPane.showMessageDialog(null, "A lista de alocacoes está vazia");
        } else {
            StringBuilder mensagem = new StringBuilder();

            for (Map.Entry<EspacoFisico, List<Solicitacao>> entry : departamento.getAlocacoes().entrySet()) {
                EspacoFisico espacoFisicoAtual = entry.getKey();
                List<Solicitacao> solicitacoesAlocadas = entry.getValue();

                mensagem.append(espacoFisicoAtual).append("\n");
                mensagem.append("Solicitações:\n");

                for (Solicitacao solicitacao : solicitacoesAlocadas) {
                    mensagem.append(" - ").append(solicitacao).append("\n");
                }
                mensagem.append("\n");
            }

            JOptionPane.showMessageDialog(null, mensagem.toString());
        }
    }

    private void ListaEspacosActionPerformed(java.awt.event.ActionEvent evt) {
        List<EspacoFisico> espacos = departamento.lerEspacosFisicos("src/espacosFisicos");

        if (espacos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "A lista de espaços físicos está vazia");
        } else {

            StringBuilder mensagem = new StringBuilder();

            for (EspacoFisico x : espacos) {
                mensagem.append(x).append("\n");
            }

            JOptionPane.showMessageDialog(null, mensagem.toString());
        }
    }


    // Variables declaration - do not modify
    private javax.swing.JButton ListaEspacos;
    private javax.swing.JButton btnListaAloc;
    private javax.swing.JButton btnListaPend;
    private javax.swing.JMenu btnRetornar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration
}
