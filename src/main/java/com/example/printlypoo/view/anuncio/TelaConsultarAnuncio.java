package com.example.printlypoo.view.anuncio;

import com.example.printlypoo.controller.anuncio.AnuncioGlobalController;
import com.example.printlypoo.model.anuncio.AnuncioGlobal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class TelaConsultarAnuncio {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Consultar Anúncios");

        TableView<AnuncioGlobal> tabela = new TableView<>();

        TableColumn<AnuncioGlobal, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitulo()));

        TableColumn<AnuncioGlobal, String> colMensagem = new TableColumn<>("Mensagem");
        colMensagem.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMensagem()));

        TableColumn<AnuncioGlobal, String> colInicio = new TableColumn<>("Data Início");
        colInicio.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataInicio()));

        TableColumn<AnuncioGlobal, String> colFim = new TableColumn<>("Data Fim");
        colFim.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataFim()));

        TableColumn<AnuncioGlobal, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));

        tabela.getColumns().addAll(colTitulo, colMensagem, colInicio, colFim, colStatus);

        AnuncioGlobalController ctrl = new AnuncioGlobalController();
        List<AnuncioGlobal> lista = ctrl.listar();
        tabela.setItems(FXCollections.observableArrayList(lista));

        Button btnEditar = new Button("Editar selecionado");
        Button btnExcluir = new Button("Excluir selecionado");
        Button btnAtualizar = new Button("Atualizar lista");
        Button btnVoltar = new Button("Voltar");

        btnVoltar.setOnAction(e -> stage.close());

        btnEditar.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                AnuncioGlobal a = lista.get(idx);
                new TelaAtualizarAnuncio().exibir(a, idx);
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Selecione um anúncio na tabela.").showAndWait();
            }
        });

        btnExcluir.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Deseja excluir este anúncio?",
                        ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(resp -> {
                    if (resp == ButtonType.YES) {
                        ctrl.excluir(idx);
                        tabela.getItems().remove(idx);
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Selecione um anúncio na tabela.").showAndWait();
            }
        });

        btnAtualizar.setOnAction(e -> {
            List<AnuncioGlobal> novaLista = ctrl.listar();
            tabela.setItems(FXCollections.observableArrayList(novaLista));
        });

        HBox botoes = new HBox(10, btnEditar, btnExcluir, btnAtualizar, btnVoltar);
        VBox layout = new VBox(10, tabela, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 700, 400));
        stage.show();
    }
}