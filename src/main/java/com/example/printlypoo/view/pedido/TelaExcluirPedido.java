package com.example.printlypoo.view.pedido;

import com.example.printlypoo.controller.pedido.PedidoController;
import com.example.printlypoo.model.pedido.Pedido;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaExcluirPedido {

    public void exibir(Pedido pedido, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Excluir Pedido - ID: " + pedido.getid());

        Label lblConfirmacao = new Label("Tem certeza que deseja excluir o pedido abaixo?");

        // Exibe os dados do pedido para o usuário confirmar
        Label lblDados = new Label(
                "ID: "          + pedido.getid()              + "\n" +
                        "Fabricante: "  + pedido.getfabricante()      + "\n" +
                        "Material: "    + pedido.getmaterial()        + "\n" +
                        "Quantidade: "  + pedido.getquantidade()      + "\n" +
                        "Valor Total: " + pedido.getvalorTotal()      + "\n" +
                        "Status: "      + pedido.getstatus()          + "\n" +
                        "Endereço: "    + pedido.getenderecoEntrega() + "\n" +
                        "Prazo: "       + pedido.getprazoPedido()     + "\n" +
                        "Data Solic.: " + pedido.getdataSolicitacao()
        );
        lblDados.setStyle("-fx-font-family: monospace;");

        Button btnExcluir  = new Button("Confirmar Exclusão");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        btnExcluir.setOnAction(e -> {
            new PedidoController().excluir(indice);
            new Alert(Alert.AlertType.INFORMATION, "Pedido excluído com sucesso!")
                    .showAndWait();
            stage.close();
        });

        HBox botoes = new HBox(10, btnExcluir, btnCancelar);
        VBox layout = new VBox(12, lblConfirmacao, lblDados, botoes);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 320));
        stage.show();
    }
}
