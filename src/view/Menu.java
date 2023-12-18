package view;

import controller.Departamento;
import model.EspacoFisico;
import model.Solicitacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static void exibirMenu() {
        System.out.println("\n===== Menu =====");
        System.out.println("1. Ler Solicitações Novas");
        System.out.println("2. Inserir Espaços");
        System.out.println("3. Alocar Solicitações");
        System.out.println("4. Gerar Relatórios");
        System.out.println("5. Inserir Solicitacao");
        System.out.println("6. Visualizar Lista de Pendentes");
        System.out.println("7. Visualizar Lista de Alocados");
        System.out.println("8. Visualizar Lista de Espacos Fisicos");
        System.out.println("0. Sair");
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
    public void gerarMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        Departamento departamento = new Departamento();
        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();

        int opcao;
        do {

            exibirMenu();
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    solicitacoes = departamento.lerSolicitacoes("src/solicitacoesNovas");
                    if (verificarSeEhVazio("src/solicitacoesNovas"))
                        System.out.println("O arquivo com novas solicitacoes esta vazio");
                    else
                        System.out.println("Novas solicitacoes Inseridas na lista de pendentes");
                    departamento.atualizarSolicitacoesPendentes("src/solicitacoesPendentes", solicitacoes);
                    departamento.limparArquivo("src/solicitacoesNovas");
                    break;
                case 2:
                    System.out.print("Informe o tipo de espaço (SALA ou AUDITORIO): ");
                    sc.nextLine();
                    String tipoEspaco = sc.nextLine().toUpperCase();

                    System.out.print("Informe o nome do espaço: ");
                    String nomeEspaco = sc.nextLine();

                    System.out.print("Informe a capacidade do espaço: ");
                    int capacidadeEspaco = sc.nextInt();
                    boolean insercaoBemSucedida = departamento.inserirEspacoFisico(tipoEspaco, nomeEspaco, capacidadeEspaco, "C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\espacosFisicos");
                    if (insercaoBemSucedida){
                        System.out.println("Espaço físico inserido com sucesso!");
                    }
                    else{
                        System.out.println("Falha ao inserir o espaço físico. Verifique se já nao existe um espaço com o mesmo nome.");
                    }
                    break;
                case 3:
                    EspacoFisico espacoFisico = departamento.alocarSolicitacao();
                    if (espacoFisico != null) {
                        System.out.println("Alocacao bem sucedida no local: " + espacoFisico.getNome() + ", com capacidade de " + espacoFisico.getCapacidade());
                    }
                    break;
                case 4:
                    sc.nextLine(); // Limpar o buffer do teclado
                    System.out.println("Escolha o tipo de relatório:");
                    System.out.println("1. Relatório por Curso");
                    System.out.println("2. Relatório por Espaço");
                    System.out.print("Escolha: ");
                    int tipoRelatorio = sc.nextInt();

                    switch (tipoRelatorio) {
                        case 1:
                            System.out.print("Informe o nome do curso para gerar o relatório: ");
                            sc.nextLine(); // Limpar o buffer do teclado
                            String nomeCurso = sc.nextLine().toUpperCase();

                            ArrayList<Solicitacao> relatorioCurso = departamento.relatorioPorCurso(nomeCurso);

                            if (relatorioCurso.isEmpty()) {
                                System.out.println("Não foram encontradas solicitações para o curso informado.");
                            } else {
                                System.out.println("Relatório por Curso:");
                                for (Solicitacao solicitacao : relatorioCurso) {
                                    System.out.println(solicitacao);
                                }
                            }
                            break;
                        case 2:
                            System.out.print("Informe o nome do espaço para gerar o relatório: ");
                            sc.nextLine(); // Limpar o buffer do teclado
                            String nomeEspacoRelatorio = sc.nextLine().toUpperCase();

                            ArrayList<Solicitacao> relatorioEspaco = departamento.relatorioPorEspacoParaFixa(nomeEspacoRelatorio);

                            if (relatorioEspaco.isEmpty()) {
                                System.out.println("Não foram encontradas solicitações para o espaço informado.");
                            } else {
                                System.out.println("Relatório por Espaço para Solicitações:");
                                for (Solicitacao solicitacao : relatorioEspaco) {
                                    System.out.println(solicitacao);
                                }
                            }
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                            break;
                    }
                    break;
                case 5:
                    System.out.print("Informe o tipo de solicitação (FIXA ou EVENTUAL): ");
                    sc.nextLine();
                    String tipoSolicitacao = sc.nextLine().toUpperCase();

                    System.out.print("Informe o ano da solicitação: ");
                    int anoSolicitacao = sc.nextInt();

                    System.out.print("Informe o semestre da solicitação: ");
                    sc.nextLine(); // Consumir a quebra de linha pendente
                    String semestreSolicitacao = sc.nextLine();

                    System.out.print("Informe o curso da solicitação: ");
                    String cursoSolicitacao = sc.nextLine();

                    // Adicionando condição para escolher entre Fixa e Eventual
                    if ("FIXA".equals(tipoSolicitacao)) {
                        System.out.print("Informe a disciplina da solicitação fixa: ");
                        String disciplinaFixa = sc.nextLine();

                        System.out.print("Informe a capacidade da solicitação fixa: ");
                        int capacidadeFixa = sc.nextInt();

                        System.out.print("Informe o horário da solicitação fixa: ");
                        sc.nextLine(); // Consumir a quebra de linha pendente
                        String horarioFixa = sc.nextLine();

                        boolean insercaoFixaBemSucedida = departamento.inserirSolicitacaoPendenteFixa(disciplinaFixa, anoSolicitacao, semestreSolicitacao, cursoSolicitacao, capacidadeFixa, horarioFixa, "C:\\Users\\RodrigoDev\\Documents\\trabalhoLP2\\src\\solicitacoesPendentes");
                        if (insercaoFixaBemSucedida) {
                            departamento.carregarArquivos("src/solicitacoesPendentes");
                            System.out.println("Solicitação pendente (Fixa) inserida com sucesso!");
                        } else {
                            System.out.println("Falha ao inserir a solicitação pendente (Fixa). Verifique se já não existe uma solicitação com os mesmos detalhes.");
                        }
                    } else if ("EVENTUAL".equals(tipoSolicitacao)) {
                        System.out.print("Informe a finalidade da solicitação eventual: ");
                        String finalidadeEventual = sc.nextLine();

                        System.out.print("Informe a capacidade da solicitação eventual: ");
                        int capacidadeEventual = sc.nextInt();

                        System.out.print("Informe o horário da solicitação eventual: ");
                        sc.nextLine(); // Consumir a quebra de linha pendente
                        String horarioEventual = sc.nextLine();

                        System.out.print("Informe a data de início (no formato DD/MM/AAAA): ");
                        String dataInicioString = sc.nextLine();
                        LocalDate dataInicioEventual = LocalDate.parse(dataInicioString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        System.out.print("Informe a data de fim (no formato DD/MM/AAAA): ");
                        String dataFimString = sc.nextLine();
                        LocalDate dataFimEventual = LocalDate.parse(dataFimString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        boolean insercaoEventualBemSucedida = departamento.inserirSolicitacaoPendenteEventual(finalidadeEventual, anoSolicitacao, semestreSolicitacao, cursoSolicitacao, capacidadeEventual, horarioEventual, dataInicioEventual, dataFimEventual, "src/solicitacoesPendentes");
                        if (insercaoEventualBemSucedida) {
                            departamento.carregarArquivos("src/solicitacoesPendentes");
                            System.out.println("Solicitação pendente (Eventual) inserida com sucesso!");
                        } else {
                            System.out.println("Falha ao inserir a solicitação pendente (Eventual). Verifique se já não existe uma solicitação com os mesmos detalhes.");
                        }
                    }
                    break;
                case 6:
                    if (departamento.getSolicitacoesPendentes().isEmpty()){
                        System.out.println("A lista de solicitacoes Pendentes está vazia");
                    }
                    else{
                        for (Solicitacao solicitacao : departamento.getSolicitacoesPendentes()){
                            System.out.println(solicitacao);
                        }
                    }
                    break;
                case 7:
                    if (departamento.getAlocacoes() == null){
                        System.out.println("A lista de alocacoes esta vazia");
                    }
                    else{
                        System.out.println("Conteúdo do HashMap de Alocações:");

                        for (Map.Entry<EspacoFisico, List<Solicitacao>> entry : departamento.getAlocacoes().entrySet()) {
                            EspacoFisico espacoFisicoAtual = entry.getKey();
                            List<Solicitacao> solicitacoesAlocadas = entry.getValue();

                            System.out.println(espacoFisicoAtual);
                            System.out.println("Solicitações:");

                            for (Solicitacao solicitacao : solicitacoesAlocadas) {
                                System.out.println("  - " + solicitacao);
                            }

                            System.out.println();
                        }
                    }
                    break;
                case 8:
                    for (EspacoFisico x : departamento.lerEspacosFisicos("src/espacosFisicos")){
                        System.out.println(x);
                    }
                    break;
                case 0:
                    System.out.println("Saindo do programa. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

        } while (opcao != 0);

        sc.close();
    }


}
