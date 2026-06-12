package com.example.printlypoo.view.pedido;


import com.example.printlypoo.controller.pedido.PedidoController;
import com.example.printlypoo.model.pedido.Pedido;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaInserirPedido {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Pedido");

        // Campos
        TextField txtid = new TextField();
        TextField txtfabricante = new TextField();
        TextField txtmaterial = new PasswordField();
        TextField txtquantidade = new TextField();
        TextField txtvalorTotal = new TextField();
        TextField txtstatus = new TextField();
        TextField txtmotivoRecusa = new TextField();
        TextField txtenderecoEntrega = new TextField();
        TextField txtprazoPedido = new TextField();
        TextField txtdataSolicitacao = new TextField();

        Label lblMsg = new Label("");

        // Botões
        Button btnSalvar = new Button("Salvar");
        Button btnLimpar = new Button("Limpar");
        Button btnVoltar = new Button("Voltar");

        btnVoltar.setOnAction(e -> stage.close());


        btnSalvar.setOnAction(e -> {
            try {

                if (txtid.getText().isEmpty() || txtfabricante.getText().isEmpty()) {
                    lblMsg.setText("ID e Fabricante são obrigatórios!");
                    return;
                }

                if (!txtid.getText().isEmpty() && !txtid.getText().matches("\\d+")) {
                    lblMsg.setText("ID deve conter apenas números!");
                    return;
                }

                Pedido u = new Pedido(
                        txtid.getText(),
                        txtfabricante.getText(),
                        txtmaterial.getText(),
                        txtquantidade.getText(),
                        txtvalorTotal.getText(),
                        txtstatus.getText(),
                        txtmotivoRecusa.getText(),
                        txtenderecoEntrega.getText(),
                        txtprazoPedido.getText(),
                        txtdataSolicitacao.getText()

                );

                new PedidoController().inserir(u);
                lblMsg.setText("Pedido cadastrado com sucesso!");


                txtid.clear();
                txtfabricante.clear();
                txtmaterial.clear();
                txtquantidade.clear();
                txtvalorTotal.clear();
                txtstatus.clear();
                txtmotivoRecusa.clear();
                txtenderecoEntrega.clear();
                txtprazoPedido.clear();
                txtdataSolicitacao.clear();


            } catch (Exception ex) {
                lblMsg.setText("Erro ao salvar: " + ex.getMessage());
            }
        });


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));


        grid.add(new Label("ID:"), 0, 0);      grid.add(txtid, 1, 0);
        grid.add(new Label("Fabricante:"), 0, 1);    grid.add(txtfabricante, 1, 1);
        grid.add(new Label("Material:"), 0, 2);     grid.add(txtmaterial, 1, 2);
        grid.add(new Label("Quantidade:"), 0, 3);  grid.add(txtquantidade, 1, 3);
        grid.add(new Label("Valor:"), 0, 4);  grid.add(txtvalorTotal, 1, 4);
        grid.add(new Label("Status:"), 0, 5);       grid.add(txtstatus, 1, 5);
        grid.add(new Label("Motivo Recusa:"), 0, 6);    grid.add(txtmotivoRecusa, 1, 6);
        grid.add(new Label("Endereço:"), 0, 7);    grid.add(txtenderecoEntrega, 1, 7);
        grid.add(new Label("Prazo:"), 0, 8);  grid.add(txtprazoPedido, 1, 8);
        grid.add(new Label("Data:"), 0, 9);  grid.add(txtdataSolicitacao, 1, 9);


        HBox botoes = new HBox(10, btnSalvar, btnLimpar, btnVoltar);
        VBox layout = new VBox(10, grid, botoes, lblMsg);


        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 400, 450));
        stage.show();
    }
}