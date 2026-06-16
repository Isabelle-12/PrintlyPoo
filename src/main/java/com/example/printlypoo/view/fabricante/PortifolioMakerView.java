package com.example.printlypoo.view.fabricante;

import com.example.printlypoo.controller.fabricante.PortifolioMakerController;
import com.example.printlypoo.model.fabricante.PortifolioMaker;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PortifolioMakerView extends Application {
    private PortifolioMakerController controller = new PortifolioMakerController();
    private TableView<PortifolioMaker> tabela = new TableView<>();

    public static void main(String[] args) { launch(args); }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        stage.setTitle("CRUD - Portifólio [print.ly]");

        Label lblTitulo = new Label("Título do Projeto:");
        TextField txtTitulo = new TextField();
        Label lblDesc = new Label("Descrição Técnica:");
        TextField txtDesc = new TextField();
        Label lblImg = new Label("Caminho da Imagem:");
        TextField txtImg = new TextField();

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        TableColumn<PortifolioMaker, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<PortifolioMaker, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricaoTecnica"));

        TableColumn<PortifolioMaker, String> colImg = new TableColumn<>("Caminho Imagem");
        colImg.setCellValueFactory(new PropertyValueFactory<>("caminhoImagem"));

        tabela.getColumns().addAll(colTitulo, colDesc, colImg);
        atualizarTabela();

        // Escutador de clique na tabela
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                txtTitulo.setText(selecionado.getTitulo());
                txtTitulo.setEditable(false); // Bloqueia o título para usar como ID
                txtDesc.setText(selecionado.getDescricaoTecnica());
                txtImg.setText(selecionado.getCaminhoImagem());
            }
        });

        btnSalvar.setOnAction(e -> {
            controller.salvarPortifolio(txtTitulo.getText(), txtDesc.getText(), txtImg.getText());
            limparCampos(txtTitulo, txtDesc, txtImg);
            atualizarTabela();
            new Alert(Alert.AlertType.INFORMATION, "Item de portifólio salvo com sucesso!").showAndWait();
        });

        btnAtualizar.setOnAction(e -> {
            controller.atualizarPortifolio(txtTitulo.getText(), txtDesc.getText(), txtImg.getText());
            limparCampos(txtTitulo, txtDesc, txtImg);
            atualizarTabela();
            new Alert(Alert.AlertType.INFORMATION, "Portifólio atualizado com sucesso!").showAndWait();
        });

        btnExcluir.setOnAction(e -> {
            PortifolioMaker selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                controller.excluirPortifolio(selecionado.getTitulo());
                limparCampos(txtTitulo, txtDesc, txtImg);
                atualizarTabela();
                new Alert(Alert.AlertType.INFORMATION, "Portifólio excluído com sucesso!").showAndWait();
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione um item na tabela para excluir!").showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10); grid.setVgap(10);
        grid.add(lblTitulo, 0, 0); grid.add(txtTitulo, 1, 0);
        grid.add(lblDesc, 0, 1); grid.add(txtDesc, 1, 1);
        grid.add(lblImg, 0, 2); grid.add(txtImg, 1, 2);

        HBox botoes = new HBox(10, btnSalvar, btnAtualizar, btnExcluir);
        VBox layout = new VBox(10, grid, botoes, tabela);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 500, 450));
        stage.show();
    }

    private void atualizarTabela() {
        tabela.setItems(FXCollections.observableArrayList(controller.listarPortifolios()));
    }

    private void limparCampos(TextField titulo, TextField desc, TextField img) {
        titulo.clear();
        titulo.setEditable(true);
        desc.clear();
        img.clear();
    }
}