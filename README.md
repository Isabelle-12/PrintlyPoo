# PrintlyPOO - Sistema de Marketplace de Impressão 3D ☕
![Linguagem](https://img.shields.io/badge/Linguagem-Java-orange)
![Paradigma](https://img.shields.io/badge/Paradigma-Orientação%20a%20Objetos-blue)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)
![License](https://img.shields.io/badge/Licença-MIT-green)

---

## Sobre o Projeto

O **PrintlyPOO** é uma aplicação desktop desenvolvida em **Java com JavaFX**, baseada nos conceitos do marketplace Printly, com foco na aplicação prática de **Programação Orientada a Objetos**.

O sistema permite o gerenciamento completo de usuários, fabricantes, projetos 3D, pedidos, avaliações e notificações por meio de uma interface gráfica, com **persistência de dados em arquivos `.txt`**.

O objetivo é consolidar os conceitos de POO — encapsulamento, herança, polimorfismo e abstração — aplicados a um sistema funcional com múltiplos módulos e entidades relacionadas.

---

## Objetivo Geral

Desenvolver um sistema desktop em Java que simule as funcionalidades de um marketplace de impressão 3D, aplicando os princípios de **Programação Orientada a Objetos** e o padrão de arquitetura **MVC (Model-View-Controller)**.

---

## Objetivos Específicos

- Implementar o padrão **MVC** separando responsabilidades entre Model, View e Controller.
- Criar **CRUDs completos** para todas as entidades do sistema.
- Utilizar **persistência em arquivos `.txt`** para armazenamento dos dados.
- Desenvolver **interfaces gráficas com JavaFX** para interação com o usuário.
- Aplicar **tratamento de exceções** com classe própria de validação (`ValidacaoException`).
- Modelar as entidades do domínio com **encapsulamento e boas práticas de POO**.

---

## Funcionalidades do Sistema

- 👤 **Gestão de Usuários** — cadastro, consulta, atualização e exclusão.
- 🏭 **Gestão de Fabricantes** — cadastro e gerenciamento de makers e suas impressoras.
- 🖨️ **Gestão de Impressoras** — cadastro e consulta das impressoras por fabricante.
- 🧵 **Gestão de Materiais** — controle de materiais disponíveis por fabricante (preço/grama).
- 🗂️ **Gestão de Projetos 3D** — cadastro, atualização e consulta de projetos enviados.
- 📦 **Gestão de Pedidos** — criação, acompanhamento e atualização de pedidos.
- 📋 **Histórico de Status** — registro do ciclo de vida de cada pedido.
- 🚚 **Entrega de Pedidos** — controle de entregas vinculadas aos pedidos.
- ⭐ **Avaliações** — registro e consulta de avaliações de fabricantes.
- 🔔 **Notificações** — envio e visualização de notificações do sistema.
- 📢 **Anúncios Globais** — cadastro e consulta de anúncios da plataforma.
- 🗃️ **Portfólio do Maker** — gerenciamento do portfólio de peças produzidas.

---

## Arquitetura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)**:

### Model
Representa as entidades do domínio com seus atributos e regras de negócio:
`Usuario` · `Fabricante` · `Impressora` · `MaterialMaker` · `PortifolioMaker`
`Projeto3D` · `Pedido` · `HistoricoStatusPedido` · `EntregaPedido`
`Avaliacao` · `Notificacao` · `AnuncioGlobal` · `ValidacaoException`

### View
Telas JavaFX para interação com o usuário:
- Telas de **Inserir**, **Consultar**, **Atualizar** e **Excluir** para cada módulo.

### Controller
Classes responsáveis pela lógica de negócio e comunicação entre View e persistência:
`UsuarioController` · `FabricanteDAO` · `ImpressoraDAO` · `PedidoController`
`Projeto3DController` · `AvaliacaoController` · `NotificacaoController` · entre outros.

---

## Tecnologias Utilizadas

### Linguagem
- **Java**

### Interface Gráfica
- **JavaFX**

### Persistência
- **Arquivos `.txt`** (sem banco de dados)

### Build
- **Maven**

### IDE
- **IntelliJ IDEA**

---

## Estrutura do Projeto

```
PrintlyPOO/
│
├── src/main/java/com/example/printlypoo/
│   ├── model/          # Entidades do domínio (POO)
│   │   ├── usuario/
│   │   ├── fabricante/
│   │   ├── pedido/
│   │   ├── projeto3d/
│   │   ├── avaliacao/
│   │   ├── notificacao/
│   │   ├── historicostatus/
│   │   ├── entregapedido/
│   │   └── anuncio/
│   ├── view/           # Telas JavaFX
│   ├── controller/     # Lógica de negócio e acesso a dados
│   └── main.java       # Ponto de entrada da aplicação
│
├── data/               # Arquivos de persistência (.txt)
│   ├── usuarios.txt
│   ├── fabricantes.dat
│   ├── pedido.txt
│   ├── projetos3d.txt
│   └── ...
│
└── pom.xml             # Configuração Maven
```

---

## Como Executar

**Pré-requisitos:**
- Java 17+
- Maven instalado

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/PrintlyPOO.git

# Acesse a pasta do projeto
cd PrintlyPOO

# Compile e execute com Maven
./mvnw javafx:run
```

---

## Status do Projeto

🚧 **Em desenvolvimento**

Este projeto está sendo desenvolvido como **projeto acadêmico**, podendo receber melhorias e novas funcionalidades ao longo do tempo.

---

## Equipe

| Nome |
|------|
| Bruno Rafael Wacherwski |
| Gabriela Sartor Terniovicz Pereira |
| Isabelle Ribeiro dos Santos |
| Laura Neris de Souza da Silva |
| Paula Carolina Mendes Pontes |
| Pedro Brecher Fracaro Martins |

---

## Informações Acadêmicas

Trabalho apresentado como requisito parcial para a disciplina de **Programação Orientada a Objetos**.

**Instituição:** PUCPR — Pontifícia Universidade Católica do Paraná  
**Curso:** Bacharelado em Engenharia de Software — Turma 3C  
**Ano:** 2026

---

## Licença

Este projeto está licenciado sob a **MIT License © 2026**.

---

✨ Projeto desenvolvido para aplicar os conceitos de **Orientação a Objetos** em um sistema real com múltiplos módulos e interface gráfica.
