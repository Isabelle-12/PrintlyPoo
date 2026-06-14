package com.example.printlypoo.view.projeto3d;

import com.example.printlypoo.controller.projeto3d.Projeto3DController;
import com.example.printlypoo.model.projeto3d.Projeto3D;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class TelaConsultarProjeto3D {

    private final Projeto3DController ctrl = new Projeto3DController();

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Consultar Projetos 3D");

        TableView<Projeto3D> tabela = new TableView<>();

        TableColumn<Projeto3D, String> colUsuario = new TableColumn<>("Usuário");
        colUsuario.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsuario()));

        TableColumn<Projeto3D, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Projeto3D, String> colFormato = new TableColumn<>("Formato");
        colFormato.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFormato()));

        TableColumn<Projeto3D, String> colVolume = new TableColumn<>("Volume (cm³)");
        colVolume.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getVolume())));

        TableColumn<Projeto3D, String> colPeso = new TableColumn<>("Peso (g)");
        colPeso.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getPeso())));

        TableColumn<Projeto3D, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

        tabela.getColumns().addAll(colUsuario, colNome, colFormato, colVolume, colPeso, colStatus);

        tabela.setItems(FXCollections.observableArrayList(ctrl.listar()));

        // Filtro por usuário (requisito: "consulta lista os projetos por usuário")
        TextField txtFiltro = new TextField();
        txtFiltro.setPromptText("e-mail do usuário");
        Button btnFiltrar = new Button("Filtrar por usuário");
        Button btnTodos = new Button("Mostrar todos");

        btnFiltrar.setOnAction(e -> {
            String u = txtFiltro.getText().trim();
            if (u.isEmpty()) {
                tabela.setItems(FXCollections.observableArrayList(ctrl.listar()));
            } else {
                tabela.setItems(FXCollections.observableArrayList(ctrl.listarPorUsuario(u)));
            }
        });

        btnTodos.setOnAction(e -> tabela.setItems(FXCollections.observableArrayList(ctrl.listar())));

        Button btnEditar = new Button("Editar selecionado");
        Button btnExcluir = new Button("Excluir selecionado");
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> stage.close());

        btnEditar.setOnAction(e -> {
            Projeto3D p = tabela.getSelectionModel().getSelectedItem();
            if (p == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione um projeto na tabela.").showAndWait();
                return;
            }
            if ("Em produção".equals(p.getStatus())) {
                new Alert(Alert.AlertType.WARNING,
                        "Projetos em produção não podem ser editados.").showAndWait();
                return;
            }
            int realIdx = indiceReal(p);
            if (realIdx >= 0) {
                new TelaAtualizarProjeto3D().exibir(p, realIdx);
                stage.close();
            }
        });

        btnExcluir.setOnAction(e -> {
            Projeto3D p = tabela.getSelectionModel().getSelectedItem();
            if (p == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione um projeto na tabela.").showAndWait();
                return;
            }
            if ("Em produção".equals(p.getStatus())) {
                new Alert(Alert.AlertType.WARNING,
                        "Projetos em produção não podem ser excluídos.").showAndWait();
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Deseja excluir este projeto?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(resp -> {
                if (resp == ButtonType.YES) {
                    int realIdx = indiceReal(p);
                    if (realIdx >= 0) {
                        ctrl.excluir(realIdx);
                        tabela.getItems().remove(p);
                    }
                }
            });
        });

        HBox filtro = new HBox(10, txtFiltro, btnFiltrar, btnTodos);
        HBox botoes = new HBox(10, btnEditar, btnExcluir, btnVoltar);
        VBox layout = new VBox(10, filtro, tabela, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 720, 430));
        stage.show();
    }

    // Acha a posição real do projeto na lista completa do arquivo,
    // mesmo quando a tabela está filtrada por usuário.
    private int indiceReal(Projeto3D p) {
        List<Projeto3D> todos = ctrl.listar();
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).paraTexto().equals(p.paraTexto())) {
                return i;
            }
        }
        return -1;
    }
}
