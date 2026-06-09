package com.example.printlypoo.view.usuario;


import com.example.printlypoo.controller.usuario.UsuarioController;
import com.example.printlypoo.model.usuario.Usuario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaInserirUsuario {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Usuário");

        // Campos
        TextField txtNome = new TextField();
        TextField txtEmail = new TextField();
        PasswordField txtSenha = new PasswordField();
        TextField txtCpfCnpj = new TextField();
        TextField txtTelefone = new TextField();
        TextField txtCep = new TextField();
        TextField txtCidade = new TextField();
        TextField txtEstado = new TextField();
        TextField txtEndereco = new TextField();
        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("CLIENTE", "FABRICANTE");
        cbTipo.setValue("CLIENTE");

        Label lblMsg = new Label("");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnVoltar = new Button("Voltar");

        btnVoltar.setOnAction(e -> stage.close());


        btnSalvar.setOnAction(e -> {
            try {

                if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty()) {
                    lblMsg.setText("Nome e e-mail são obrigatórios!");
                    return;
                }

                if (!txtTelefone.getText().isEmpty() && !txtTelefone.getText().matches("\\d+")) {
                    lblMsg.setText("Telefone deve conter apenas números!");
                    return;
                }

                if (!txtCep.getText().isEmpty() && !txtCep.getText().matches("\\d{5}-?\\d{3}")) {
                    lblMsg.setText("CEP inválido! Use o formato 00000-000");
                    return;
                }

                Usuario u = new Usuario(
                        txtNome.getText(),
                        txtEmail.getText(),
                        txtSenha.getText(),
                        txtCpfCnpj.getText(),
                        txtTelefone.getText(),
                        txtCep.getText(),
                        txtCidade.getText(),
                        txtEstado.getText(),
                        txtEndereco.getText(),
                        cbTipo.getValue()
                );

                new UsuarioController().inserir(u);
                lblMsg.setText("Usuário cadastrado com sucesso!");


                txtNome.clear();
                txtEmail.clear();
                txtSenha.clear();
                txtCpfCnpj.clear();
                txtTelefone.clear();
                txtCep.clear();
                txtCidade.clear();
                txtEstado.clear();
                txtEndereco.clear();
                cbTipo.setValue("CLIENTE");

            } catch (Exception ex) {
                lblMsg.setText("Erro ao salvar: " + ex.getMessage());
            }
        });


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));


        grid.add(new Label("Nome:"), 0, 0);      grid.add(txtNome, 1, 0);
        grid.add(new Label("E-mail:"), 0, 1);    grid.add(txtEmail, 1, 1);
        grid.add(new Label("Senha:"), 0, 2);     grid.add(txtSenha, 1, 2);
        grid.add(new Label("CPF/CNPJ:"), 0, 3);  grid.add(txtCpfCnpj, 1, 3);
        grid.add(new Label("Telefone:"), 0, 4);  grid.add(txtTelefone, 1, 4);
        grid.add(new Label("CEP:"), 0, 5);       grid.add(txtCep, 1, 5);
        grid.add(new Label("Cidade:"), 0, 6);    grid.add(txtCidade, 1, 6);
        grid.add(new Label("Estado:"), 0, 7);    grid.add(txtEstado, 1, 7);
        grid.add(new Label("Endereço:"), 0, 8);  grid.add(txtEndereco, 1, 8);
        grid.add(new Label("Tipo:"), 0, 9);      grid.add(cbTipo, 1, 9);

        HBox botoes = new HBox(10, btnSalvar, btnLimpar, btnVoltar);
        VBox layout = new VBox(10, grid, botoes, lblMsg);


        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 400, 450));
        stage.show();
    }
}