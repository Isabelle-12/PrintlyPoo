package com.example.printlypoo.view.usuario;

import com.example.printlypoo.controller.usuario.UsuarioController;
import com.example.printlypoo.model.usuario.Usuario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaExcluirUsuario {

    public void exibir(Usuario u, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Excluir Usuário");


        Label lblAviso = new Label("Tem certeza que deseja excluir o usuário abaixo?");
        Label lblNome = new Label("Nome: " + u.getNome());
        Label lblEmail = new Label("E-mail: " + u.getEmail());
        Label lblCpf = new Label("CPF/CNPJ: " + u.getCpfCnpj());

        Label lblMsg = new Label("");

        Button btnConfirmar = new Button("Confirmar Exclusão");
        Button btnCancelar = new Button("Voltar");
        btnCancelar.setOnAction(e -> stage.close());

        btnConfirmar.setOnAction(e -> {
            try {
                new UsuarioController().excluir(indice);
                lblMsg.setText("Usuário excluído com sucesso!");
                btnConfirmar.setDisable(true); // evita clicar duas vezes
            } catch (Exception ex) {
                lblMsg.setText("Erro ao excluir: " + ex.getMessage());
            }
        });

        btnCancelar.setOnAction(e -> stage.close());

        VBox layout = new VBox(12,
                lblAviso,
                new Separator(),
                lblNome,
                lblEmail,
                lblCpf,
                new Separator(),
                new HBox(10, btnConfirmar, btnCancelar),
                lblMsg
        );
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 350, 280));
        stage.setResizable(false);
        stage.show();
    }
}