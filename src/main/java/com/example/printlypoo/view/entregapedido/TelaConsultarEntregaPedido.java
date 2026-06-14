package com.example.printlypoo.view.entregapedido;

import com.example.printlypoo.controller.entregapedido.EntregaPedidoController;
import com.example.printlypoo.model.entregapedido.EntregaPedido;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class TelaConsultarEntregaPedido {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Consultar Entregas");

        EntregaPedidoController ctrl = new EntregaPedidoController();

        TableView<EntregaPedido> tabela = new TableView<>();

        TableColumn<EntregaPedido, String> colId = new TableColumn<>("ID Pedido");
        colId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdPedido()));

        TableColumn<EntregaPedido, String> colCaminho = new TableColumn<>("Arquivo entregue");
        colCaminho.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCaminhoArquivo()));

        TableColumn<EntregaPedido, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipoEntrega()));

        TableColumn<EntregaPedido, String> colData = new TableColumn<>("Data de envio");
        colData.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDataEnvio()));

        tabela.getColumns().addAll(colId, colCaminho, colTipo, colData);

        tabela.setItems(FXCollections.observableArrayList(ctrl.listar()));

        Button btnEditar = new Button("Editar selecionado");
        Button btnExcluir = new Button("Excluir selecionado");
        Button btnAtualizar = new Button("Atualizar lista");
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> stage.close());

        btnEditar.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                EntregaPedido ep = tabela.getItems().get(idx);
                new TelaAtualizarEntregaPedido().exibir(ep, idx);
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione uma entrega na tabela.").showAndWait();
            }
        });

        btnExcluir.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Deseja excluir esta entrega?", ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(resp -> {
                    if (resp == ButtonType.YES) {
                        ctrl.excluir(idx);
                        tabela.getItems().remove(idx);
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione uma entrega na tabela.").showAndWait();
            }
        });

        btnAtualizar.setOnAction(e ->
                tabela.setItems(FXCollections.observableArrayList(ctrl.listar())));

        HBox botoes = new HBox(10, btnEditar, btnExcluir, btnAtualizar, btnVoltar);
        VBox layout = new VBox(10, tabela, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 680, 400));
        stage.show();
    }
}
