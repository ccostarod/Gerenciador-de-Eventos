package view;

import controller.Departamento;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.*;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

public class MenuDeOpcoes extends javax.swing.JFrame {
    Departamento departamento = new Departamento();
    ArrayList<Solicitacao> solicitacoes = new ArrayList<>();

    public MenuDeOpcoes() {
        initComponents();

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        btnCarregarNS = new javax.swing.JButton();
        btnInserirEspaco = new javax.swing.JButton();
        btnAlocarEspaco = new javax.swing.JButton();
        btnVisuArquivos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnCriarSolicitacao = new javax.swing.JButton();
        btnGerarRelat = new javax.swing.JButton();
        btnLimparArquivos = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuRetornar = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Opções");
        setResizable(false);

        btnCarregarNS.setText("Carregar novas solicitações");
        btnCarregarNS.setToolTipText("Transfere as solicitações novas para a lista de pendentes ");
        btnCarregarNS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarregarNSActionPerformed(evt);
            }
        });

        btnInserirEspaco.setText("Inserir Espaço");
        btnInserirEspaco.setToolTipText("Cria um novo espaço físico");
        btnInserirEspaco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirEspacoActionPerformed(evt);
            }
        });

        btnAlocarEspaco.setText("Alocar Solicitações");
        btnAlocarEspaco.setToolTipText("Aloca solicitações automaticamente");
        btnAlocarEspaco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlocarEspacoActionPerformed(evt);
            }
        });

        btnVisuArquivos.setText("Visualizar Elementos");
        btnVisuArquivos.setToolTipText("Abre uma janela para visualizar todos os arquivos");
        btnVisuArquivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisuArquivosActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Painel de Opções");

        btnCriarSolicitacao.setText("Criar Solicitação");
        btnCriarSolicitacao.setToolTipText("Cria uma nova solicitação pendente");
        btnCriarSolicitacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarSolicitacaoActionPerformed(evt);
            }
        });

        btnGerarRelat.setText("Gerar Relatorios");
        btnGerarRelat.setToolTipText("Gera relatorios com base no curso ou no espaço físico escolhido");
        btnGerarRelat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarRelatActionPerformed(evt);
            }
        });

        btnLimparArquivos.setText("Limpar Arquivos");
        btnLimparArquivos.setToolTipText("Os arquivos que serão limpos:\nsolicitacoesAlocadas, solicitacoesPendentes, EspacosFisicos");
        btnLimparArquivos.setName(""); // NOI18N
        btnLimparArquivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparArquivosActionPerformed(evt);
            }
        });

        btnSair.setText("Sair");
        btnSair.setToolTipText("Fecha o programa");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jMenuRetornar.setText("Retornar");
        jMenuRetornar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuRetornarMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuRetornar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(316, 316, 316)
                                                .addComponent(jLabel1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(177, 177, 177)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnLimparArquivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnCarregarNS, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                                        .addComponent(btnInserirEspaco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnGerarRelat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(83, 83, 83)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnVisuArquivos, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                                        .addComponent(btnCriarSolicitacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnAlocarEspaco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(177, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel1)
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCriarSolicitacao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCarregarNS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAlocarEspaco, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnInserirEspaco, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnGerarRelat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnVisuArquivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnLimparArquivos, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                        .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(61, 61, 61))
        );

        btnLimparArquivos.getAccessibleContext().setAccessibleDescription("Limpar arquivos");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>


    private void jMenuRetornarMouseClicked(java.awt.event.MouseEvent evt) {
        TelaDeInicio tela = new TelaDeInicio();
        this.dispose();
        tela.setVisible(true);
    }

    private void btnInserirEspacoActionPerformed(java.awt.event.ActionEvent evt) {
        String tipoEspaco = null;
        String nomeEsp = null;

        String sc = JOptionPane.showInputDialog(this,"Informe o tipo de espaço (SALA ou AUDITORIO","Tipo de Espaço",QUESTION_MESSAGE);
        if(sc!=null)
            tipoEspaco = sc.toUpperCase();
        else return;

        String nomeEspaco = JOptionPane.showInputDialog(this,"Informe o nome do espaço: ");
        if(nomeEspaco!=null) nomeEsp = nomeEspaco;
        else return;

        String capacidadeEspaco = JOptionPane.showInputDialog(this,"Informe a capacidade do espaço: ");
        int capacidadeEsp = Integer.parseInt(capacidadeEspaco);

        boolean insercaoBemSucedida = departamento.inserirEspacoFisico(tipoEspaco, nomeEsp, capacidadeEsp, "src/espacosFisicos");
        if (insercaoBemSucedida){
            JOptionPane.showMessageDialog(null,"Espaço físico inserido com sucesso!");
        }
        else{
            JOptionPane.showMessageDialog(null,"Falha ao inserir o espaço físico. Verifique se já nao existe um espaço com o mesmo nome.");
        }
    }

    private void btnCarregarNSActionPerformed(java.awt.event.ActionEvent evt) {

        solicitacoes = departamento.lerSolicitacoes("src/solicitacoesNovas");
        try {
            if (verificarSeEhVazio("src/solicitacoesNovas")){
                JOptionPane.showMessageDialog(null,"O arquivo com novas solicitações está vazio!");
            }
            else{
                JOptionPane.showMessageDialog(null,"Solicitacoes adicionadas a lista de pendentes!");


            }
        } catch (IOException ex) {
            Logger.getLogger(MenuDeOpcoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        departamento.atualizarSolicitacoesPendentes("src/solicitacoesPendentes", solicitacoes);
        departamento.limparArquivo("src/solicitacoesNovas");

    }

    private void btnAlocarEspacoActionPerformed(java.awt.event.ActionEvent evt){

        EspacoFisico espacoFisico = departamento.alocarSolicitacao() ;

        if (espacoFisico != null) {
            JOptionPane.showMessageDialog(null,"Alocacao bem sucedida no local: " + espacoFisico.getNome() + ", com capacidade de " + espacoFisico.getCapacidade());
        }
        else{
            JOptionPane.showMessageDialog(null,"Houve erro ao alocar: Veja se contem elementos na lista de Pendentes, ou espaco valido");

        }
    }

    private void btnVisuArquivosActionPerformed(java.awt.event.ActionEvent evt) {
        VisualizacaoElementos tela = new VisualizacaoElementos();
        this.dispose();
        tela.setVisible(true);
    }

    private void btnGerarRelatActionPerformed(java.awt.event.ActionEvent evt) {
        String nomeCurso = null;
        String nomeEspacoRelatorio = null;
        Object[] opcoes = {"Relatório por Curso", "Relatório por Espaço para Solicitações Fixas"};
        int escolha = JOptionPane.showOptionDialog(this,
                "Escolha o tipo de relatório:",
                "Tipo de Relatório",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (escolha == JOptionPane.YES_OPTION) {
            String nomeCur = JOptionPane.showInputDialog(this, "Informe o nome do curso para gerar o relatório: ");
            if(nomeCur!=null) nomeCurso = nomeCur.toUpperCase();
            else return;

            ArrayList<Solicitacao> relatorioCurso = departamento.relatorioPorCurso(nomeCurso);

            if (relatorioCurso.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Não foram encontradas solicitações para o curso informado.");
            } else {
                StringBuilder mensagem = new StringBuilder("Relatório por Curso:\n");
                for (Solicitacao solicitacao : relatorioCurso) {
                    mensagem.append(solicitacao).append("\n");
                }
                JOptionPane.showMessageDialog(null, mensagem.toString());
            }
        } else if (escolha == JOptionPane.NO_OPTION) {
            String nomeEsp = JOptionPane.showInputDialog(this, "Informe o nome do espaço para gerar o relatório: ");
            if(nomeEsp!=null) nomeEspacoRelatorio = nomeEsp.toUpperCase();
            else return;

            ArrayList<Solicitacao> relatorioEspaco = departamento.relatorioPorEspacoParaFixa(nomeEspacoRelatorio);

            if (relatorioEspaco.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Não foram encontradas solicitações fixas para o espaço informado.");
            } else {
                StringBuilder mensagem = new StringBuilder("Relatório por Espaço para Solicitações:\n");
                for (Solicitacao solicitacao : relatorioEspaco) {
                    mensagem.append(solicitacao).append("\n");
                }
                JOptionPane.showMessageDialog(null, mensagem.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
        }
    }

    private void btnCriarSolicitacaoActionPerformed(java.awt.event.ActionEvent evt) {
        String tipoSolicitacao = null;
        String semestreSolicitacao = null;
        String cursoSolicitacao = null;

        String tipoSol = JOptionPane.showInputDialog(null,"Informe o tipo de solicitação (FIXA ou EVENTUAL): ");
        if(tipoSol!=null) tipoSolicitacao = tipoSol.toUpperCase();
        else return;

        String anoSol = JOptionPane.showInputDialog(null,"Informe o ano da solicitação: ");
        int anoSolicitacao = Integer.parseInt(anoSol);

        String semSol = JOptionPane.showInputDialog(null,"Informe o semestre da solicitação: ");
        if(semSol!=null)
            semestreSolicitacao = semSol;
        else return;

        String curSol = JOptionPane.showInputDialog(null,"Informe o curso da solicitação: ");
        if(curSol!=null)
            cursoSolicitacao = curSol;
        else return;

        // Adicionando condição para escolher entre Fixa e Eventual
        if ("FIXA".equals(tipoSolicitacao)) {
            String disciplinaFixa = JOptionPane.showInputDialog(null,"Informe a disciplina da solicitacao fixa: ");

            String capacSol = JOptionPane.showInputDialog(null,"Informe a capacidade da solicitação fixa: ");
            int capacidadeFixa = Integer.parseInt(capacSol);

            String horarioFixa = JOptionPane.showInputDialog(null,"Informe o horário da solicitacao fixa (Padrão SIGAA): ");

            boolean insercaoFixaBemSucedida = departamento.inserirSolicitacaoPendenteFixa(disciplinaFixa, anoSolicitacao, semestreSolicitacao, cursoSolicitacao, capacidadeFixa, horarioFixa, "src/solicitacoesPendentes");
            if (insercaoFixaBemSucedida) {
                departamento.carregarArquivos("src/solicitacoesPendentes");
                JOptionPane.showMessageDialog(null, "Solicitação pendente (Fixa) inserida com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao inserir a solicitação pendente (Fixa). Verifique se já não existe uma solicitação com os mesmos detalhes.");
            }
        } else if ("EVENTUAL".equals(tipoSolicitacao)) {
            String finalidadeEventual = JOptionPane.showInputDialog(null,"Informe a finalidade da solicitação eventual: ");

            String capEventual = JOptionPane.showInputDialog(null,"Informe a capacidade da solicitação eventual: ");
            int capacidadeEventual = Integer.parseInt(capEventual);

            String horarioEventual = JOptionPane.showInputDialog(null,"Informe o horário da solicitação eventual (Padrão SIGAA): ");

            String dataInicioString = JOptionPane.showInputDialog(null,"Informe a data de início (no formato DD/MM/AAAA): ");
            LocalDate dataInicioEventual = LocalDate.parse(dataInicioString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String dataFimString = JOptionPane.showInputDialog(null,"Informe a data de fim (no formato DD/MM/AAAA): ");
            LocalDate dataFimEventual = LocalDate.parse(dataFimString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            boolean insercaoEventualBemSucedida = departamento.inserirSolicitacaoPendenteEventual(finalidadeEventual, anoSolicitacao, semestreSolicitacao, cursoSolicitacao, capacidadeEventual, horarioEventual, dataInicioEventual, dataFimEventual,
                    "src/solicitacoesPendentes");
            if (insercaoEventualBemSucedida) {
                departamento.carregarArquivos("src/solicitacoesPendentes");
                JOptionPane.showMessageDialog(null, "Solicitação pendente (Eventual) inserida com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao inserir a solicitação pendente (Eventual). Verifique se já não existe uma solicitação com os mesmos detalhes.");
            }
        }
    }

    public static boolean verificarSeEhVazio(String caminhoDoArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoDoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void btnLimparArquivosActionPerformed(java.awt.event.ActionEvent evt) {
        limparArquivo("src/solicitacoesAlocadas.bin");
        limparArquivo("src/solicitacoesPendentes");
        limparArquivo("src/espacosFisicos");
        JOptionPane.showMessageDialog(null,"Informações dos arquivos apagados com sucesso!");
    }

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

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


    // Variables declaration - do not modify
    private javax.swing.JButton btnAlocarEspaco;
    private javax.swing.JButton btnCarregarNS;
    private javax.swing.JButton btnCriarSolicitacao;
    private javax.swing.JButton btnGerarRelat;
    private javax.swing.JButton btnInserirEspaco;
    private javax.swing.JButton btnLimparArquivos;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVisuArquivos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuRetornar;
    // End of variables declaration
}
