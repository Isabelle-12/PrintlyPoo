package com.example.printlypoo.view.anuncio;

import com.example.printlypoo.controller.anuncio.AnuncioGlobalController;
import com.example.printlypoo.model.anuncio.AnuncioGlobal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaAtualizarAnuncio {

    public void exibir(AnuncioGlobal a, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Editar Anúncio");

        TextField txtTitulo = new TextField();
        TextArea txtMensagem = new TextArea();
        txtMensagem.setPrefRowCount(3);
        TextField txtDataInicio = new TextField();
        txtDataInicio.setPromptText("DD/MM/AAAA");
        TextField txtDataFim = new TextField();
        txtDataFim.setPromptText("DD/MM/AAAA");
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("ATIVO", "INATIVO");


        txtTitulo.setText(a.getTitulo());
        txtMensagem.setText(a.getMensagem());
        txtDataInicio.setText(a.getDataInicio());
        txtDataFim.setText(a.getDataFim());
        cbStatus.setValue(a.getStatus());

        Label lblMsg = new Label("");
        Button btnSalvar = new Button("Salvar alterações");
        Button btnVoltar = new Button("Voltar");

        btnVoltar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            try {
                if (txtTitulo.getText().trim().isEmpty()) {
                    lblMsg.setText("Título é obrigatório!");
                    return;
                }
                if (txtMensagem.getText().trim().isEmpty()) {
                    lblMsg.setText("Mensagem é obrigatória!");
                    return;
                }
                if (!txtDataInicio.getText().isEmpty() &&
                        !txtDataInicio.getText().matches("\\d{2}/\\d{2}/\\d{4}")) {
                    lblMsg.setText("Data início inválida! Use DD/MM/AAAA");
                    return;
                }
                if (!txtDataFim.getText().isEmpty() &&
                        !txtDataFim.getText().matches("\\d{2}/\\d{2}/\\d{4}")) {
                    lblMsg.setText("Data fim inválida! Use DD/MM/AAAA");
                    return;
                }

                AnuncioGlobal atualizado = new AnuncioGlobal(
                        txtTitulo.getText().trim(),
                        txtMensagem.getText().trim(),
                        txtDataInicio.getText().trim(),
                        txtDataFim.getText().trim(),
                        cbStatus.getValue()
                );

                new AnuncioGlobalController().atualizar(indice, atualizado);
                lblMsg.setText("Anúncio atualizado com sucesso!");

            } catch (Exception ex) {
                lblMsg.setText("Erro ao atualizar: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Título:"),      0, 0); grid.add(txtTitulo,     1, 0);
        grid.add(new Label("Mensagem:"),    0, 1); grid.add(txtMensagem,   1, 1);
        grid.add(new Label("Data Início:"), 0, 2); grid.add(txtDataInicio, 1, 2);
        grid.add(new Label("Data Fim:"),    0, 3); grid.add(txtDataFim,    1, 3);
        grid.add(new Label("Status:"),      0, 4); grid.add(cbStatus,      1, 4);

        HBox botoes = new HBox(10, btnSalvar, btnVoltar);
        VBox layout = new VBox(10, grid, botoes, lblMsg);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 400, 350));
        stage.show();
    }
}