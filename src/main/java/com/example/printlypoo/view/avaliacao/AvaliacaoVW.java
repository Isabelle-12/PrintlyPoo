package com.example.printlypoo.view;

import com.example.printlypoo.controller.AvaliacaoController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AvaliacaoVW {

    private AvaliacaoController controller = new AvaliacaoController();

    public void show(Stage stage) {


        TextField txtNota = new TextField();

        txtNota.setPromptText("Nota (1-5)");


        TextField txtComentario = new TextField();

        txtComentario.setPromptText("Comentário");


        TextArea area = new TextArea();

        area.setEditable(false);


        Button btnCriar = new Button("Criar Avaliação");

        Button btnListar = new Button("Listar");


        btnCriar.setOnAction(e -> {

            try {

                int nota = Integer.parseInt(txtNota.getText());

                String comentario = txtComentario.getText();


                controller.criarAvaliacao(nota, comentario);


                txtNota.clear();

                txtComentario.clear();


                area.setText("Criado com sucesso!");


            } catch (Exception ex) {

                area.setText("Erro: " + ex.getMessage());

            }

        });


        btnListar.setOnAction(e -> {

            StringBuilder sb = new StringBuilder();


            controller.listarAvaliacoes().forEach(a ->

                    sb.append("ID: ")

                            .append(a.getId())

                            .append(" | Nota: ")

                            .append(a.getNota())

                            .append(" | ")

                            .append(a.getComentarioCliente())

                            .append("\n")

            );


            area.setText(sb.toString());

        });

        TextField txtId = new TextField();
        txtId.setPromptText("ID da Avaliação");

        Button btnAtualizar = new Button("Atualizar");
        Button btnRemover = new Button("Remover");

        btnAtualizar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                int nota = Integer.parseInt(txtNota.getText());
                controller.atualizarAvaliacao(id, nota, txtComentario.getText());
                area.setText("Atualizado com sucesso!");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnRemover.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                controller.removerAvaliacao(id);
                area.setText("Removido com sucesso!");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });


        VBox root = new VBox(10, txtId, txtNota, txtComentario, btnCriar, btnListar, btnAtualizar, btnRemover, area);


        Scene scene = new Scene(root, 400, 300);


        stage.setTitle("Avaliações");

        stage.setScene(scene);

        stage.show();

    }

}