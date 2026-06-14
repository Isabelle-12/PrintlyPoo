package com.example.printlypoo.view.avaliacao;

import com.example.printlypoo.controller.avaliacao.AvaliacaoController;
import com.example.printlypoo.model.avaliacao.Avaliacao;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AvaliacaoVW {

    private AvaliacaoController controller = new AvaliacaoController();

    public void show(Stage stage) {

        TextField txtId = new TextField();
        txtId.setPromptText("ID da Avaliação (ou clique na tabela)");

        TextField txtNota = new TextField();
        txtNota.setPromptText("Nota (1-5)");

        TextField txtComentario = new TextField();
        txtComentario.setPromptText("Comentário");

        TextField txtResposta = new TextField();
        txtResposta.setPromptText("Resposta do Fabricante");

        TextArea area = new TextArea();
        area.setEditable(false);

        TableView<Avaliacao> tabela = new TableView<>();

        TableColumn<Avaliacao, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Avaliacao, String> colNota = new TableColumn<>("Nota");
        colNota.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNota())));

        TableColumn<Avaliacao, String> colComentario = new TableColumn<>("Comentário");
        colComentario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getComentarioCliente()));

        TableColumn<Avaliacao, String> colResposta = new TableColumn<>("Resposta");
        colResposta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRespostaFabricante() == null ? "" : data.getValue().getRespostaFabricante()));

        tabela.getColumns().addAll(colId, colNota, colComentario,colResposta);
        tabela.setPrefHeight(180);
        tabela.getItems().addAll(controller.listarAvaliacoes());

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, sel) -> {
            if (sel != null) {
                txtId.setText(String.valueOf(sel.getId()));
                txtNota.setText(String.valueOf(sel.getNota()));
                txtComentario.setText(sel.getComentarioCliente());
                txtResposta.setText(sel.getRespostaFabricante() == null ? "" : sel.getRespostaFabricante());
            }
        });

        Button btnCriar = new Button("Criar Avaliação");
        Button btnAtualizar = new Button("Atualizar");
        Button btnRemover = new Button("Remover");
        Button btnRecarregar = new Button("Recarregar");
        Button btnResponder = new Button("Responder");

        btnCriar.setOnAction(e -> {
            try {
                boolean notaVazia = txtNota.getText().isBlank();
                boolean comentarioVazio = txtComentario.getText().isBlank();

                if (notaVazia && comentarioVazio) {
                    area.setText("Preencha todos os campos.");
                    return;
                }
                if (notaVazia) {
                    area.setText("Preencha o campo: Nota.");
                    return;
                }
                if (comentarioVazio) {
                    area.setText("Preencha o campo: Comentário.");
                    return;
                }

                int nota = Integer.parseInt(txtNota.getText());

                if (nota < 1 || nota > 5) {
                    area.setText("Nota inválida: digite um valor entre 1 e 5.");
                    return;
                }

                controller.criarAvaliacao(nota, txtComentario.getText());
                tabela.getItems().setAll(controller.listarAvaliacoes());
                txtNota.clear();
                txtComentario.clear();
                txtId.clear();
                area.setText("Criado com sucesso!");

            } catch (NumberFormatException ex) {
                area.setText("Nota inválida: digite apenas números no campo Nota.");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                if (txtId.getText().isBlank()) {
                    area.setText("Selecione uma avaliação na tabela ou informe o ID.");
                    return;
                }

                int id = Integer.parseInt(txtId.getText());
                int nota = Integer.parseInt(txtNota.getText());

                if (nota < 1 || nota > 5) {
                    area.setText("Nota inválida: digite um valor entre 1 e 5.");
                    return;
                }

                controller.atualizarAvaliacao(id, nota, txtComentario.getText());
                tabela.getItems().setAll(controller.listarAvaliacoes());
                area.setText("Atualizado com sucesso!");

            } catch (NumberFormatException ex) {
                area.setText("ID e Nota devem ser números inteiros.");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnRemover.setOnAction(e -> {
            try {
                if (txtId.getText().isBlank()) {
                    area.setText("Selecione uma avaliação na tabela ou informe o ID.");
                    return;
                }

                int id = Integer.parseInt(txtId.getText());
                controller.removerAvaliacao(id);
                tabela.getItems().setAll(controller.listarAvaliacoes());
                txtId.clear();
                txtNota.clear();
                txtComentario.clear();
                area.setText("Removido com sucesso!");

            } catch (NumberFormatException ex) {
                area.setText("ID deve ser um número inteiro.");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });
        btnResponder.setOnAction(e -> {
            try {
                if (txtId.getText().isBlank()) {
                    area.setText("Selecione uma avaliação na tabela ou informe o ID.");
                    return;
                }
                if (txtResposta.getText().isBlank()) {
                    area.setText("Preencha o campo: Resposta.");
                    return;
                }

                int id = Integer.parseInt(txtId.getText());
                controller.responderAvaliacao(id, txtResposta.getText());
                tabela.getItems().setAll(controller.listarAvaliacoes());
                area.setText("Resposta registrada com sucesso!");

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
                alerta.setHeaderText("O arquivo de avaliações não existe.");
                alerta.setContentText("Deseja criar um novo arquivo?");

                ButtonType btnSim = new ButtonType("Criar Arquivo");
                ButtonType btnNao = new ButtonType("Cancelar");

                alerta.getButtonTypes().setAll(btnSim, btnNao);

                alerta.showAndWait().ifPresent(resposta -> {
                    if (resposta == btnSim) {
                        controller.criarArquivoVazio();
                        tabela.getItems().clear();
                        area.setText("Arquivo criado com sucesso.");
                    }
                });

                return;
            }

            controller.recarregar();
            tabela.getItems().setAll(controller.listarAvaliacoes());

            area.setText("Tabela recarregada.");
        });

        VBox root = new VBox(10, tabela, txtId, txtNota, txtComentario,txtResposta, btnCriar, btnAtualizar, btnRemover,btnResponder, btnRecarregar, area);

        Scene scene = new Scene(root, 500, 540);
        stage.setTitle("Avaliações");
        stage.setScene(scene);
        stage.show();
    }
}