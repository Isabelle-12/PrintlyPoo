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

        // config das coluna
        TableColumn<MaterialMaker, String> colId = new TableColumn<>("ID Fabricante");
        colId.setCellValueFactory(new PropertyValueFactory<>("idFabricante"));

        TableColumn<MaterialMaker, String> colTipo = new TableColumn<>("Tipo Material");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoMaterial"));

        TableColumn<MaterialMaker, Double> colPreco = new TableColumn<>("Preço/g");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoPorGrama"));

        // tabelas
        tabela.getColumns().addAll(colId, colTipo, colPreco);


        atualizarTabela();

        // faz o botao salvar
        btnSalvar.setOnAction(e -> {
            try {
                double preco = Double.parseDouble(txtPreco.getText());
                controller.salvarMaterial(txtId.getText(), txtTipo.getText(), preco);


                atualizarTabela();

                // Limpa tudo
                txtId.clear();
                txtTipo.clear();
                txtPreco.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Material salvo com sucesso!");
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, digite um número válido no preço!");
                alert.showAndWait();
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

    // busca os materiais
    private void atualizarTabela() {
        tabela.setItems(FXCollections.observableArrayList(controller.listarMateriais()));
    }
}