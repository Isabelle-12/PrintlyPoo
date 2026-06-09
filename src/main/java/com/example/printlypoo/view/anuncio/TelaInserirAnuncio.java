package com.example.printlypoo.view.anuncio;

import com.example.printlypoo.controller.anuncio.AnuncioGlobalController;
import com.example.printlypoo.model.anuncio.AnuncioGlobal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaInserirAnuncio {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Anúncio");

        TextField txtTitulo = new TextField();
        TextArea txtMensagem = new TextArea();
        txtMensagem.setPrefRowCount(3);
        TextField txtDataInicio = new TextField();
        txtDataInicio.setPromptText("DD/MM/AAAA");
        TextField txtDataFim = new TextField();
        txtDataFim.setPromptText("DD/MM/AAAA");
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("ATIVO", "INATIVO");
        cbStatus.setValue("ATIVO");

        Label lblMsg = new Label("");

        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnVoltar = new Button("Voltar");

        btnVoltar.setOnAction(e -> stage.close());

        btnLimpar.setOnAction(e -> {
            txtTitulo.clear();
            txtMensagem.clear();
            txtDataInicio.clear();
            txtDataFim.clear();
            cbStatus.setValue("ATIVO");
            lblMsg.setText("");
        });

        btnSalvar.setOnAction(e -> {
            try {
                // Validações obrigatórias
                if (txtTitulo.getText().trim().isEmpty()) {
                    lblMsg.setText("Título é obrigatório!");
                    return;
                }
                if (txtMensagem.getText().trim().isEmpty()) {
                    lblMsg.setText("Mensagem é obrigatória!");
                    return;
                }

                // Validação de data
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

                AnuncioGlobal a = new AnuncioGlobal(
                        txtTitulo.getText().trim(),
                        txtMensagem.getText().trim(),
                        txtDataInicio.getText().trim(),
                        txtDataFim.getText().trim(),
                        cbStatus.getValue()
                );

                new AnuncioGlobalController().inserir(a);
                lblMsg.setText("Anúncio cadastrado com sucesso!");

                txtTitulo.clear();
                txtMensagem.clear();
                txtDataInicio.clear();
                txtDataFim.clear();
                cbStatus.setValue("ATIVO");

            } catch (Exception ex) {
                lblMsg.setText("Erro ao salvar: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Título:"),       0, 0); grid.add(txtTitulo,     1, 0);
        grid.add(new Label("Mensagem:"),     0, 1); grid.add(txtMensagem,   1, 1);
        grid.add(new Label("Data Início:"),  0, 2); grid.add(txtDataInicio, 1, 2);
        grid.add(new Label("Data Fim:"),     0, 3); grid.add(txtDataFim,    1, 3);
        grid.add(new Label("Status:"),       0, 4); grid.add(cbStatus,      1, 4);

        HBox botoes = new HBox(10, btnSalvar, btnLimpar, btnVoltar);
        VBox layout = new VBox(10, grid, botoes, lblMsg);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 400, 350));
        stage.show();
    }
}