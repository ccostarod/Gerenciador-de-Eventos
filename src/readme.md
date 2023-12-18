# README - Sistema de Alocação de Espaços Físicos e Solicitações
Projeto desenvolvido por Rodrigo Otávio Cantanhede Costa e Claudiomir Silva Nogueira Junior.
<br>
<br>
Este projeto consiste em um sistema de alocação de espaços físicos para solicitações de eventos ou aulas, desenvolvido em Java. O sistema conta com funcionalidades para gerenciar solicitações pendentes, inserir novos espaços físicos, alocar solicitações em espaços disponíveis, gerar relatórios por curso ou espaço, inserir novas solicitações e visualizar listas de pendentes, alocados e espaços físicos.

## Estrutura do Projeto

O projeto está organizado em três pacotes principais:

1. **controller:** Contém a classe `Departamento`, responsável por controlar as operações relacionadas à alocação de espaços e gerenciamento de solicitações.

2. **model:** Contém as classes `EspacoFisico` e `Solicitacao`, que representam, respectivamente, os espaços físicos disponíveis e as solicitações de eventos ou aulas.

3. **view:** Contém a classe `Menu`, responsável pela interação com o usuário através de um menu de opções, e a classe Main responsavel por rodar o programa.

## Funcionalidades

### 1. Ler Solicitações Novas (Opção 1)

- As novas solicitações são lidas de um arquivo e inseridas na lista de pendentes.
- O arquivo é então limpo para evitar processamento duplicado.

### 2. Inserir Espaços (Opção 2)

- Permite a inserção de novos espaços físicos com informações como tipo, nome e capacidade.
- Verifica se já existe um espaço com o mesmo nome antes de inserir.

### 3. Alocar Solicitações (Opção 3)

- Aloca uma solicitação em um espaço físico disponível.
- Retorna informações sobre o sucesso da alocação.

### 4. Gerar Relatórios (Opção 4)

- Permite a geração de relatórios por curso ou espaço.
- O usuário escolhe o tipo de relatório desejado e fornece informações adicionais, como nome do curso ou espaço.
- Exibe as solicitações correspondentes ou uma mensagem se não houverem.

### 5. Inserir Solicitação (Opção 5)

- Permite a inserção de novas solicitações, fixas ou eventuais, com informações específicas para cada tipo.

### 6. Visualizar Lista de Pendentes (Opção 6)

- Exibe a lista de solicitações pendentes.

### 7. Visualizar Lista de Alocados (Opção 7)

- Exibe a lista de alocações, mostrando os espaços físicos e as solicitações alocadas em cada um.

### 8. Visualizar Lista de Espaços Físicos (Opção 8)

- Exibe a lista de espaços físicos disponíveis.




O menu será exibido, e o usuário poderá escolher entre as opções disponíveis.

## Observações

- Certifique-se de fornecer caminhos de arquivos e diretórios corretos para as operações de leitura e escrita.

Este projeto foi desenvolvido como parte de um trabalho prático para a disciplina de Linguagem de Programação II. Caso haja alguma dúvida ou sugestão de melhoria, sinta-se à vontade para entrar em contato com os desenvolvedores.

Agradecemos por utilizar este sistema de alocação de espaços e esperamos que seja útil para suas necessidades de gerenciamento de solicitações e espaços físicos.