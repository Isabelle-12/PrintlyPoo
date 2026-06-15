package com.example.printlypoo.view.usuario;

import com.example.printlypoo.controller.usuario.UsuarioController;
import com.example.printlypoo.model.usuario.Usuario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaAtualizarUsuario {

    public void exibir(Usuario u, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Editar Usuário");


        TextField txtNome = new TextField();
        TextField txtEmail = new TextField();
        TextField txtSenha = new TextField();
        TextField txtCpfCnpj = new TextField();
        TextField txtTelefone = new TextField();
        TextField txtCep = new TextField();
        TextField txtCidade = new TextField();
        TextField txtEstado = new TextField();
        TextField txtEndereco = new TextField();



        txtNome.setText(u.getNome());
        txtEmail.setText(u.getEmail());
        txtSenha.setText(u.getSenha());
        txtCpfCnpj.setText(u.getCpfCnpj());
        txtTelefone.setText(u.getTelefone());
        txtCep.setText(u.getCep());
        txtCidade.setText(u.getCidade());
        txtEstado.setText(u.getEstado());
        txtEndereco.setText(u.getEndereco());


        Label lblMsg = new Label("");
        Button btnSalvar = new Button("Salvar alterações");
        Button btnCancelar = new Button("Voltar");
        


        btnSalvar.setOnAction(e -> {
            try {
                if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
                    lblMsg.setText("Nome e e-mail são obrigatórios!");
                    return;
                }
                
                if (!txtTelefone.getText().matches("\\d+")) {
                    lblMsg.setText("Telefone deve conter apenas números!");
                    return;
                }
                if (!txtCep.getText().matches("\\d{5}-?\\d{3}")) {
                    lblMsg.setText("CEP inválido! Use o formato 00000-000");
                    return;
                }

                Usuario usuarioAtualizado = new Usuario(
                        txtNome.getText().trim(),
                        txtEmail.getText().trim(),
                        txtSenha.getText().trim(),
                        txtCpfCnpj.getText().trim(),
                        txtTelefone.getText().trim(),
                        txtCep.getText().trim(),
                        txtCidade.getText().trim(),
                        txtEstado.getText().trim(),
                        txtEndereco.getText().trim()

                );

                new UsuarioController().atualizar(indice, usuarioAtualizado);
                lblMsg.setText("Usuário atualizado com sucesso!");

            } catch (Exception ex) {
                lblMsg.setText("Erro ao atualizar: " + ex.getMessage());
            }
        });

        btnCancelar.setOnAction(e -> stage.close());


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Nome:"),      0, 0);  grid.add(txtNome,     1, 0);
        grid.add(new Label("E-mail:"),    0, 1);  grid.add(txtEmail,    1, 1);
        grid.add(new Label("Senha:"),     0, 2);  grid.add(txtSenha,    1, 2);
        grid.add(new Label("CPF/CNPJ:"), 0, 3);  grid.add(txtCpfCnpj,  1, 3);
        grid.add(new Label("Telefone:"), 0, 4);  grid.add(txtTelefone, 1, 4);
        grid.add(new Label("CEP:"),       0, 5);  grid.add(txtCep,      1, 5);
        grid.add(new Label("Cidade:"),    0, 6);  grid.add(txtCidade,   1, 6);
        grid.add(new Label("Estado:"),    0, 7);  grid.add(txtEstado,   1, 7);
        grid.add(new Label("Endereço:"), 0, 8);  grid.add(txtEndereco, 1, 8);
        

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        VBox layout = new VBox(10, grid, botoes, lblMsg);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 420, 480));
        stage.show();
    }
}