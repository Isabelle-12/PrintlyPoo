package com.example.printlypoo.view.anuncio;

import com.example.printlypoo.controller.anuncio.AnuncioGlobalController;
import com.example.printlypoo.model.anuncio.AnuncioGlobal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaExcluirAnuncio {

    public void exibir(AnuncioGlobal a, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Excluir Anúncio");

        Label lblAviso = new Label("Tem certeza que deseja excluir o anúncio abaixo?");
        Label lblTitulo = new Label("Título: " + a.getTitulo());
        Label lblMensagem = new Label("Mensagem: " + a.getMensagem());
        Label lblStatus = new Label("Status: " + a.getStatus());
        Label lblMsg = new Label("");

        Button btnConfirmar = new Button("Confirmar Exclusão");
        Button btnVoltar = new Button("Voltar");

        btnVoltar.setOnAction(e -> stage.close());

        btnConfirmar.setOnAction(e -> {
            try {
                new AnuncioGlobalController().excluir(indice);
                lblMsg.setText("Anúncio excluído com sucesso!");
                btnConfirmar.setDisable(true);
            } catch (Exception ex) {
                lblMsg.setText("Erro ao excluir: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(12,
                lblAviso,
                new Separator(),
                lblTitulo,
                lblMensagem,
                lblStatus,
                new Separator(),
                new HBox(10, btnConfirmar, btnVoltar),
                lblMsg
        );
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 380, 260));
        stage.setResizable(false);
        stage.show();
    }
}