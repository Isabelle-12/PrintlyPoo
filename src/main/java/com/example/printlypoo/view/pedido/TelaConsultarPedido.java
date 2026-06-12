package com.example.printlypoo.view.pedido;

import com.example.printlypoo.controller.pedido.PedidoController;
import com.example.printlypoo.model.pedido.Pedido;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class TelaConsultarPedido {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Consultar Pedidos");

        TableView<Pedido> tabela = new TableView<>();

        TableColumn<Pedido, String> colid = new TableColumn<>("ID");
        colid.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getid()));

        TableColumn<Pedido, String> colfabricante = new TableColumn<>("Fabricante");
        colfabricante.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getfabricante()));

        TableColumn<Pedido, String> colmaterial = new TableColumn<>("Material");
        colmaterial.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getmaterial()));

        TableColumn<Pedido, String> colquantidade = new TableColumn<>("Quantidade");
        colquantidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getquantidade()));

        TableColumn<Pedido, String> colvalorTotal = new TableColumn<>("Valor");
        colvalorTotal.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getvalorTotal()));

        TableColumn<Pedido, String> colstatus = new TableColumn<>("Status");
        colstatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getstatus()));

        TableColumn<Pedido, String> colmotivoRecusa = new TableColumn<>("Motivo Recusa");
        colmotivoRecusa.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getmotivoRecusa()));

        TableColumn<Pedido, String> colenderecoEntrega = new TableColumn<>("Endereço");
        colenderecoEntrega.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getenderecoEntrega()));

        TableColumn<Pedido, String> colprazoPedido = new TableColumn<>("Prazo");
        colprazoPedido.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getprazoPedido()));

        TableColumn<Pedido, String> coldataSolicitacao = new TableColumn<>("Data");
        coldataSolicitacao.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getdataSolicitacao()));

        tabela.getColumns().addAll(colid, colfabricante, colmaterial, colquantidade, colvalorTotal, colstatus, colmotivoRecusa, colenderecoEntrega, colprazoPedido, coldataSolicitacao);


        PedidoController ctrl = new PedidoController();
        List<Pedido> lista = ctrl.listar();
        tabela.setItems(FXCollections.observableArrayList(lista));

        // Botões
        Button btnEditar = new Button("Editar selecionado");
        Button btnExcluir = new Button("Excluir selecionado");
        Button btnAtualizar = new Button("Atualizar lista");
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> stage.close());

        btnEditar.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Pedido u = lista.get(idx);
                new TelaAtualizarPedido().exibir(u, idx);
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Selecione um pedido na tabela.").showAndWait();
            }
        });

        btnExcluir.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Deseja excluir este pedido?",
                        ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(resp -> {
                    if (resp == ButtonType.YES) {
                        ctrl.excluir(idx);
                        tabela.getItems().remove(idx);
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Selecione um pedido na tabela.").showAndWait();
            }
        });

        btnAtualizar.setOnAction(e -> {
            List<Pedido> novaLista = ctrl.listar();
            tabela.setItems(FXCollections.observableArrayList(novaLista));
        });

        HBox botoes = new HBox(10, btnEditar, btnExcluir, btnAtualizar, btnVoltar);
        VBox layout = new VBox(10, tabela, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 700, 400));
        stage.show();
    }
}