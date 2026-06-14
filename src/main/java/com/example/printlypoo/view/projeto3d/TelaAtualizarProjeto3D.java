package com.example.printlypoo.view.projeto3d;

import com.example.printlypoo.controller.projeto3d.Projeto3DController;
import com.example.printlypoo.model.projeto3d.Projeto3D;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaAtualizarProjeto3D {

    public void exibir(Projeto3D p, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Editar Projeto 3D");

        TextField txtUsuario = new TextField();
        TextField txtNome = new TextField();
        TextField txtDescricao = new TextField();
        TextField txtCaminho = new TextField();
        ComboBox<String> cbFormato = new ComboBox<>();
        cbFormato.getItems().addAll("STL", "OBJ");
        TextField txtVolume = new TextField();
        TextField txtPeso = new TextField();
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("Aguardando", "Em produção", "Concluído", "Cancelado");

        txtUsuario.setText(p.getUsuario());
        txtNome.setText(p.getNome());
        txtDescricao.setText(p.getDescricao());
        txtCaminho.setText(p.getCaminhoArquivo());
        cbFormato.setValue(p.getFormato());
        txtVolume.setText(String.valueOf(p.getVolume()));
        txtPeso.setText(String.valueOf(p.getPeso()));
        cbStatus.setValue(p.getStatus());

        Label lblMsg = new Label("");
        Button btnSalvar = new Button("Salvar alterações");
        Button btnCancelar = new Button("Voltar");
        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            try {
                if (txtUsuario.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty()) {
                    lblMsg.setText("Usuário e nome são obrigatórios!");
                    return;
                }
                if (cbFormato.getValue() == null) {
                    lblMsg.setText("Selecione o formato (STL ou OBJ)!");
                    return;
                }
                if (cbStatus.getValue() == null) {
                    lblMsg.setText("Selecione o status!");
                    return;
                }

                double volume;
                double peso;
                try {
                    volume = Double.parseDouble(txtVolume.getText().trim().replace(",", "."));
                    peso = Double.parseDouble(txtPeso.getText().trim().replace(",", "."));
                } catch (NumberFormatException nfe) {
                    lblMsg.setText("Volume e peso devem ser números (ex: 120.5)");
                    return;
                }

                Projeto3D atualizado = new Projeto3D(
                        txtUsuario.getText().trim(),
                        txtNome.getText().trim(),
                        txtDescricao.getText().trim(),
                        txtCaminho.getText().trim(),
                        cbFormato.getValue(),
                        volume,
                        peso,
                        cbStatus.getValue()
                );

                new Projeto3DController().atualizar(indice, atualizado);
                lblMsg.setText("Projeto atualizado com sucesso!");

            } catch (Exception ex) {
                lblMsg.setText("Erro ao atualizar: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Usuário (e-mail):"), 0, 0);      grid.add(txtUsuario, 1, 0);
        grid.add(new Label("Nome:"), 0, 1);                  grid.add(txtNome, 1, 1);
        grid.add(new Label("Descrição:"), 0, 2);             grid.add(txtDescricao, 1, 2);
        grid.add(new Label("Caminho do arquivo 3D:"), 0, 3); grid.add(txtCaminho, 1, 3);
        grid.add(new Label("Formato:"), 0, 4);               grid.add(cbFormato, 1, 4);
        grid.add(new Label("Volume (cm³):"), 0, 5);          grid.add(txtVolume, 1, 5);
        grid.add(new Label("Peso (g):"), 0, 6);              grid.add(txtPeso, 1, 6);
        grid.add(new Label("Status:"), 0, 7);                grid.add(cbStatus, 1, 7);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        VBox layout = new VBox(10, grid, botoes, lblMsg);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 450, 470));
        stage.show();
    }
}
