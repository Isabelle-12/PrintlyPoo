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

        btnSalvar.setOnAction(e -> {
            controller.salvarPortifolio(txtTitulo.getText(), txtDesc.getText(), txtImg.getText());
            atualizarTabela();

            txtTitulo.clear();
            txtDesc.clear();
            txtImg.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item de portifólio salvo com sucesso!");
            alert.showAndWait();
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
}