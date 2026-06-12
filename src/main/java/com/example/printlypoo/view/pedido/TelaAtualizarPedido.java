package com.example.printlypoo.view.pedido;

import com.example.printlypoo.controller.pedido.PedidoController;
import com.example.printlypoo.model.pedido.Pedido;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaAtualizarPedido {

    public void exibir(Pedido u, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Editar Pedido");


        TextField txtid = new TextField();
        TextField txtfabricante = new TextField();
        TextField txtmaterial = new TextField();
        TextField txtquantidade = new TextField();
        TextField txtvalorTotal = new TextField();
        TextField txtstatus = new TextField();
        TextField txtmotivoRecusa = new TextField();
        TextField txtenderecoEntrega = new TextField();
        TextField txtprazoPedido = new TextField();
        TextField txtdataSolicitacao = new TextField();

        txtid.setText(u.getid());
        txtfabricante.setText(u.getfabricante());
        txtmaterial.setText(u.getmaterial());
        txtquantidade.setText(u.getquantidade());
        txtvalorTotal.setText(u.getvalorTotal());
        txtstatus.setText(u.getstatus());
        txtmotivoRecusa.setText(u.getmotivoRecusa());
        txtenderecoEntrega.setText(u.getenderecoEntrega());
        txtprazoPedido.setText(u.getprazoPedido());
        txtdataSolicitacao.setText(u.getdataSolicitacao());


        Label lblMsg = new Label("");
        Button btnSalvar = new Button("Salvar alterações");
        Button btnCancelar = new Button("Voltar");
        btnCancelar.setOnAction(e -> stage.close());


        btnSalvar.setOnAction(e -> {
            try {
                if (txtid.getText().trim().isEmpty() || txtfabricante.getText().trim().isEmpty()) {
                    lblMsg.setText("ID e Fabricante são obrigatórios!");
                    return;
                }

                if (!txtid.getText().matches("\\d+")) {
                    lblMsg.setText("ID deve conter apenas números!");
                    return;
                }

                Pedido pedidoAtualizado = new Pedido(
                        txtid.getText().trim(),
                        txtfabricante.getText().trim(),
                        txtmaterial.getText().trim(),
                        txtquantidade.getText().trim(),
                        txtvalorTotal.getText().trim(),
                        txtstatus.getText().trim(),
                        txtmotivoRecusa.getText().trim(),
                        txtenderecoEntrega.getText().trim(),
                        txtprazoPedido.getText().trim(),
                        txtdataSolicitacao.getText().trim()

                );

                new PedidoController().atualizar(indice, pedidoAtualizado);
                lblMsg.setText("Pedido atualizado com sucesso!");

            } catch (Exception ex) {
                lblMsg.setText("Erro ao atualizar: " + ex.getMessage());
            }
        });

        btnCancelar.setOnAction(e -> stage.close());


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Id:"),      0, 0);  grid.add(txtid,     1, 0);
        grid.add(new Label("Fabricante:"),    0, 1);  grid.add(txtfabricante,    1, 1);
        grid.add(new Label("Material:"),     0, 2);  grid.add(txtmaterial,    1, 2);
        grid.add(new Label("Quantidade:"), 0, 3);  grid.add(txtquantidade,  1, 3);
        grid.add(new Label("Valor:"), 0, 4);  grid.add(txtvalorTotal, 1, 4);
        grid.add(new Label("Status:"),       0, 5);  grid.add(txtstatus,      1, 5);
        grid.add(new Label("Motivo Recusa:"),    0, 6);  grid.add(txtmotivoRecusa,   1, 6);
        grid.add(new Label("Endereço:"),    0, 7);  grid.add(txtenderecoEntrega,   1, 7);
        grid.add(new Label("Prazo:"), 0, 8);  grid.add(txtprazoPedido, 1, 8);
        grid.add(new Label("Data da Solicitação:"), 0, 9);  grid.add(txtdataSolicitacao, 1, 9);


        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        VBox layout = new VBox(10, grid, botoes, lblMsg);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 420, 480));
        stage.show();
    }
}