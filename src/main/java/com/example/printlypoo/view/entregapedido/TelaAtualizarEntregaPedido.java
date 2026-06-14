package com.example.printlypoo.view.entregapedido;

import com.example.printlypoo.controller.entregapedido.EntregaPedidoController;
import com.example.printlypoo.model.entregapedido.EntregaPedido;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaAtualizarEntregaPedido {

    public void exibir(EntregaPedido ep, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Editar Entrega");

        TextField txtIdPedido = new TextField();
        TextField txtCaminho = new TextField();
        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Correios", "Transportadora", "Retirada no local", "Entrega própria");
        TextField txtData = new TextField();

        txtIdPedido.setText(ep.getIdPedido());
        txtCaminho.setText(ep.getCaminhoArquivo());
        cbTipo.setValue(ep.getTipoEntrega());
        txtData.setText(ep.getDataEnvio());

        Label lblMsg = new Label("");
        Button btnSalvar = new Button("Salvar alterações");
        Button btnCancelar = new Button("Voltar");
        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            try {
                if (txtIdPedido.getText().trim().isEmpty()) {
                    lblMsg.setText("O ID do pedido é obrigatório!");
                    return;
                }
                if (cbTipo.getValue() == null) {
                    lblMsg.setText("Selecione o tipo de entrega!");
                    return;
                }
                if (!txtData.getText().trim().isEmpty()
                        && !txtData.getText().trim().matches("\\d{2}/\\d{2}/\\d{4}")) {
                    lblMsg.setText("Data inválida! Use o formato DD/MM/AAAA");
                    return;
                }

                EntregaPedido atualizada = new EntregaPedido(
                        txtIdPedido.getText().trim(),
                        txtCaminho.getText().trim(),
                        cbTipo.getValue(),
                        txtData.getText().trim()
                );

                new EntregaPedidoController().atualizar(indice, atualizada);
                lblMsg.setText("Entrega atualizada com sucesso!");

            } catch (Exception ex) {
                lblMsg.setText("Erro ao atualizar: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        grid.add(new Label("ID do pedido:"), 0, 0);               grid.add(txtIdPedido, 1, 0);
        grid.add(new Label("Caminho do arquivo entregue:"), 0, 1); grid.add(txtCaminho, 1, 1);
        grid.add(new Label("Tipo de entrega:"), 0, 2);            grid.add(cbTipo, 1, 2);
        grid.add(new Label("Data de envio:"), 0, 3);              grid.add(txtData, 1, 3);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        VBox layout = new VBox(10, grid, botoes, lblMsg);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout, 470, 340));
        stage.show();
    }
}
