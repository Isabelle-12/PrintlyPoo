package com.example.printlypoo.view.notificacao;

import com.example.printlypoo.controller.notificacao.NotificacaoController;
import com.example.printlypoo.model.notificacao.Notificacao;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NotificacaoVW {

    private NotificacaoController controller = new NotificacaoController();

    public void show(Stage stage) {

        TextField txtId = new TextField();
        txtId.setPromptText("ID da Notificação (ou clique na tabela)");

        TextField txtTipo = new TextField();
        txtTipo.setPromptText("Tipo");

        TextField txtTitulo = new TextField();
        txtTitulo.setPromptText("Título");

        TextField txtMensagem = new TextField();
        txtMensagem.setPromptText("Mensagem");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        TextArea area = new TextArea();
        area.setEditable(false);

        TableView<Notificacao> tabela = new TableView<>();

        TableColumn<Notificacao, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Notificacao, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));

        TableColumn<Notificacao, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipo()));

        TableColumn<Notificacao, String> colMensagem = new TableColumn<>("Mensagem");
        colMensagem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMensagem()));

        TableColumn<Notificacao, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmailDestino()));

        TableColumn<Notificacao, String> colLida = new TableColumn<>("Lida");
        colLida.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isLida() ? "Sim" : "Não"));

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        TableColumn<Notificacao, String> colData = new TableColumn<>("Data de Envio");
        colData.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataEnvio().format(formatoData)));

        tabela.getColumns().addAll(colId, colTitulo, colTipo, colMensagem, colEmail, colLida,colData);
        tabela.setPrefHeight(180);
        tabela.getItems().addAll(controller.listarNotificacoes());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, sel) -> {
            if (sel != null) {
                txtId.setText(String.valueOf(sel.getId()));
                txtTipo.setText(sel.getTipo());
                txtTitulo.setText(sel.getTitulo());
                txtMensagem.setText(sel.getMensagem());
                txtEmail.setText(sel.getEmailDestino());
            }
        });


        Button btnCriar = new Button("Criar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnRemover = new Button("Remover");
        Button btnMarcarLida = new Button("Marcar como Lida");
        Button btnDesmarcarLida = new Button("Marcar como Não Lida");
        Button btnRecarregar = new Button("Recarregar");

        btnCriar.setOnAction(e -> {
            try {
                boolean tipoVazio = txtTipo.getText().isBlank();
                boolean tituloVazio = txtTitulo.getText().isBlank();
                boolean mensagemVazia = txtMensagem.getText().isBlank();
                boolean emailVazio = txtEmail.getText().isBlank();

                if (tipoVazio && tituloVazio && mensagemVazia && emailVazio) {
                    area.setText("Preencha todos os campos.");
                    return;
                }
                if (tipoVazio) { area.setText("Preencha o campo: Tipo."); return; }
                if (tituloVazio) { area.setText("Preencha o campo: Título."); return; }
                if (mensagemVazia) { area.setText("Preencha o campo: Mensagem."); return; }
                if (emailVazio) { area.setText("Preencha o campo: Email."); return; }

                controller.criarNotificacao(
                        txtTipo.getText(), txtTitulo.getText(),
                        txtMensagem.getText(), txtEmail.getText()
                );
                tabela.getItems().setAll(controller.listarNotificacoes());
                txtTipo.clear();
                txtTitulo.clear();
                txtMensagem.clear();
                txtEmail.clear();
                txtId.clear();
                area.setText("Criado com sucesso!");

            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (txtId.getText().isBlank()) {
                    area.setText("Selecione uma notificação na tabela ou informe o ID.");
                    return;
                }

                int id = Integer.parseInt(txtId.getText());
                controller.atualizarNotificacao(id, txtTipo.getText(), txtTitulo.getText(),
                        txtMensagem.getText(), txtEmail.getText());
                tabela.getItems().setAll(controller.listarNotificacoes());
                area.setText("Atualizado com sucesso!");

            } catch (NumberFormatException ex) {
                area.setText("ID deve ser um número inteiro.");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnRemover.setOnAction(e -> {
            try {
                if (txtId.getText().isBlank()) {
                    area.setText("Selecione uma notificação na tabela ou informe o ID.");
                    return;
                }

                int id = Integer.parseInt(txtId.getText());
                controller.removerNotificacao(id);
                tabela.getItems().setAll(controller.listarNotificacoes());
                txtId.clear();
                txtTipo.clear();
                txtTitulo.clear();
                txtMensagem.clear();
                txtEmail.clear();
                area.setText("Removido com sucesso!");

            } catch (NumberFormatException ex) {
                area.setText("ID deve ser um número inteiro.");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnMarcarLida.setOnAction(e -> {
            try {
                if (txtId.getText().isBlank()) {
                    area.setText("Selecione uma notificação na tabela ou informe o ID.");
                    return;
                }
                int id = Integer.parseInt(txtId.getText());
                controller.marcarComoLida(id);
                tabela.getItems().setAll(controller.listarNotificacoes());
                area.setText("Marcado como lida!");

            } catch (NumberFormatException ex) {
                area.setText("ID deve ser um número inteiro.");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnDesmarcarLida.setOnAction(e -> {
            try {
                if (txtId.getText().isBlank()) {
                    area.setText("Selecione uma notificação na tabela ou informe o ID.");
                    return;
                }
                int id = Integer.parseInt(txtId.getText());
                controller.marcarComoNLida(id);
                tabela.getItems().setAll(controller.listarNotificacoes());
                area.setText("Marcado como não lida!");

            } catch (NumberFormatException ex) {
                area.setText("ID deve ser um número inteiro.");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnRecarregar.setOnAction(e -> {

            if (!controller.arquivoExiste()) {

                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Arquivo não encontrado");
                alerta.setHeaderText("O arquivo de dados não existe.");
                alerta.setContentText("Deseja criar um novo arquivo?");

                ButtonType btnSim = new ButtonType("Criar Arquivo");
                ButtonType btnNao = new ButtonType("Cancelar");

                alerta.getButtonTypes().setAll(btnSim, btnNao);

                alerta.showAndWait().ifPresent(resposta -> {

                    if (resposta == btnSim) {

                        controller.criarArquivoVazio();

                        tabela.getItems().clear();

                        area.setText("Novo arquivo criado com sucesso.");
                    }

                });

                return;
            }

            controller.recarregar();
            tabela.getItems().setAll(controller.listarNotificacoes());

            area.setText("Tabela recarregada.");
        });

        VBox root = new VBox(10, tabela, txtId, txtTipo, txtTitulo, txtMensagem, txtEmail, btnCriar, btnAtualizar, btnRemover, btnMarcarLida, btnDesmarcarLida,btnRecarregar, area);

        Scene scene = new Scene(root, 500, 720);
        stage.setTitle("Notificações");
        stage.setScene(scene);
        stage.show();
    }
}