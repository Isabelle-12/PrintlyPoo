package com.example.printlypoo.view.fabricante;

import com.example.printlypoo.controller.fabricante.MaterialMakerController;
import com.example.printlypoo.model.fabricante.MaterialMaker;
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

public class MaterialMakerView extends Application {
    private MaterialMakerController controller = new MaterialMakerController();
    private TableView<MaterialMaker> tabela = new TableView<>();

    public static void main(String[] args) { launch(args); }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        stage.setTitle("CRUD - Materiais [print.ly]");

        Label lblId = new Label("ID Fabricante:");
        TextField txtId = new TextField();
        Label lblTipo = new Label("Tipo Material:");
        TextField txtTipo = new TextField();
        Label lblPreco = new Label("Preço por Grama:");
        TextField txtPreco = new TextField();

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        TableColumn<MaterialMaker, String> colId = new TableColumn<>("ID Fabricante");
        colId.setCellValueFactory(new PropertyValueFactory<>("idFabricante"));

        TableColumn<MaterialMaker, String> colTipo = new TableColumn<>("Tipo Material");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoMaterial"));

        TableColumn<MaterialMaker, Double> colPreco = new TableColumn<>("Preço/g");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoPorGrama"));

        tabela.getColumns().addAll(colId, colTipo, colPreco);
        atualizarTabela();

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                txtId.setText(selecionado.getIdFabricante());
                txtId.setEditable(false); // ID vira chave primária, não edita
                txtTipo.setText(selecionado.getTipoMaterial());
                txtPreco.setText(String.valueOf(selecionado.getPrecoPorGrama()));
            }
        });

        btnSalvar.setOnAction(e -> {
            try {
                double preco = Double.parseDouble(txtPreco.getText());
                controller.salvarMaterial(txtId.getText(), txtTipo.getText(), preco);
                limparCampos(txtId, txtTipo, txtPreco);
                atualizarTabela();
                new Alert(Alert.AlertType.INFORMATION, "Material salvo com sucesso!").showAndWait();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Por favor, digite um preço válido!").showAndWait();
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                double preco = Double.parseDouble(txtPreco.getText());
                controller.atualizarMaterial(txtId.getText(), txtTipo.getText(), preco);
                limparCampos(txtId, txtTipo, txtPreco);
                atualizarTabela();
                new Alert(Alert.AlertType.INFORMATION, "Material atualizado com sucesso!").showAndWait();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Por favor, digite um preço válido!").showAndWait();
            }
        });

        btnExcluir.setOnAction(e -> {
            MaterialMaker selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                controller.excluirMaterial(selecionado.getIdFabricante());
                limparCampos(txtId, txtTipo, txtPreco);
                atualizarTabela();
                new Alert(Alert.AlertType.INFORMATION, "Material excluído com sucesso!").showAndWait();
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione um material na tabela para excluir!").showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10); grid.setVgap(10);
        grid.add(lblId, 0, 0); grid.add(txtId, 1, 0);
        grid.add(lblTipo, 0, 1); grid.add(txtTipo, 1, 1);
        grid.add(lblPreco, 0, 2); grid.add(txtPreco, 1, 2);

        HBox botoes = new HBox(10, btnSalvar, btnAtualizar, btnExcluir);
        VBox layout = new VBox(10, grid, botoes, tabela);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 500, 450));
        stage.show();
    }

    private void atualizarTabela() {
        tabela.setItems(FXCollections.observableArrayList(controller.listarMateriais()));
    }

    private void limparCampos(TextField id, TextField tipo, TextField preco) {
        id.clear();
        id.setEditable(true);
        tipo.clear();
        preco.clear();
    }
}