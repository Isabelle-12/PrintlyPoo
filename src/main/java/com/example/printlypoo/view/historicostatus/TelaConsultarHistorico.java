package com.example.printlypoo.view.historicostatus;

import com.example.printlypoo.controller.historicostatus.HistoricoStatusPedidoController;
import com.example.printlypoo.model.historicostatus.HistoricoStatusPedido;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class TelaConsultarHistorico {

    // Exibe todos os registros de histórico
    public void exibir() {
        exibirInterno(null);
    }

    // Exibe somente os registros do pedido informado
    public void exibirPorPedido(String idPedido) {
        exibirInterno(idPedido);
    }

    private void exibirInterno(String idPedidoFiltro) {
        Stage stage = new Stage();
        stage.setTitle(idPedidoFiltro == null
                ? "Histórico de Status - Todos os Pedidos"
                : "Histórico de Status - Pedido: " + idPedidoFiltro);

        TableView<HistoricoStatusPedido> tabela = new TableView<>();

        TableColumn<HistoricoStatusPedido, String> colid = new TableColumn<>("ID Hist.");
        colid.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getid()));

        TableColumn<HistoricoStatusPedido, String> colidPedido = new TableColumn<>("ID Pedido");
        colidPedido.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getidPedido()));

        TableColumn<HistoricoStatusPedido, String> colAnterior = new TableColumn<>("Status Anterior");
        colAnterior.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getstatusAnterior()));

        TableColumn<HistoricoStatusPedido, String> colNovo = new TableColumn<>("Novo Status");
        colNovo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getnovoStatus()));

        TableColumn<HistoricoStatusPedido, String> colObs = new TableColumn<>("Observação");
        colObs.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getobservacao()));

        TableColumn<HistoricoStatusPedido, String> colData = new TableColumn<>("Data/Hora");
        colData.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getdataHoraMudanca()));

        tabela.getColumns().addAll(colid, colidPedido, colAnterior, colNovo, colObs, colData);

        HistoricoStatusPedidoController ctrl = new HistoricoStatusPedidoController();
        List<HistoricoStatusPedido> lista = (idPedidoFiltro == null)
                ? ctrl.listar()
                : ctrl.listarPorPedido(idPedidoFiltro);

        tabela.setItems(FXCollections.observableArrayList(lista));

        Button btnEditar    = new Button("Editar selecionado");
        Button btnExcluir   = new Button("Excluir selecionado");
        Button btnAtualizar = new Button("Atualizar lista");
        Button btnVoltar    = new Button("Voltar");
        btnVoltar.setOnAction(e -> stage.close());

        btnEditar.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                HistoricoStatusPedido h = lista.get(idx);
                // Calcula o índice real no arquivo quando há filtro por pedido
                int idxReal = idPedidoFiltro == null ? idx : calcularIndiceReal(ctrl, h);
                new TelaAtualizarHistorico().exibir(h, idxReal);
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Selecione um registro na tabela.").showAndWait();
            }
        });

        btnExcluir.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Deseja excluir este registro de histórico?",
                        ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(resp -> {
                    if (resp == ButtonType.YES) {
                        HistoricoStatusPedido h = lista.get(idx);
                        int idxReal = idPedidoFiltro == null ? idx : calcularIndiceReal(ctrl, h);
                        ctrl.excluir(idxReal);
                        tabela.getItems().remove(idx);
                        lista.remove(idx);
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Selecione um registro na tabela.").showAndWait();
            }
        });

        btnAtualizar.setOnAction(e -> {
            List<HistoricoStatusPedido> novaLista = (idPedidoFiltro == null)
                    ? ctrl.listar()
                    : ctrl.listarPorPedido(idPedidoFiltro);
            lista.clear();
            lista.addAll(novaLista);
            tabela.setItems(FXCollections.observableArrayList(lista));
        });

        HBox botoes = new HBox(10, btnEditar, btnExcluir, btnAtualizar, btnVoltar);
        VBox layout = new VBox(10, tabela, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 800, 380));
        stage.show();
    }

    // Encontra o índice real no arquivo completo a partir do ID do histórico
    private int calcularIndiceReal(HistoricoStatusPedidoController ctrl, HistoricoStatusPedido h) {
        List<HistoricoStatusPedido> todos = ctrl.listar();
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getid().equals(h.getid())) {
                return i;
            }
        }
        return -1;
    }
}
