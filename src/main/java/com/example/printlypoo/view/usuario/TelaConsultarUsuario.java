package com.example.printlypoo.view.usuario;

import com.example.printlypoo.controller.usuario.UsuarioController;
import com.example.printlypoo.model.usuario.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class TelaConsultarUsuario {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Consultar Usuários");

        TableView<Usuario> tabela = new TableView<>();

        TableColumn<Usuario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNome()));

        TableColumn<Usuario, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));

        TableColumn<Usuario, String> colCpf = new TableColumn<>("CPF/CNPJ");
        colCpf.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCpfCnpj()));

        TableColumn<Usuario, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefone()));

        TableColumn<Usuario, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoPerfil()));

        tabela.getColumns().addAll(colNome, colEmail, colCpf, colTelefone, colTipo);


        UsuarioController ctrl = new UsuarioController();
        List<Usuario> lista = ctrl.listar();
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
                Usuario u = lista.get(idx);
                new TelaAtualizarUsuario().exibir(u, idx);
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Selecione um usuário na tabela.").showAndWait();
            }
        });

        btnExcluir.setOnAction(e -> {
            int idx = tabela.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Deseja excluir este usuário?",
                        ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(resp -> {
                    if (resp == ButtonType.YES) {
                        ctrl.excluir(idx);
                        tabela.getItems().remove(idx);
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Selecione um usuário na tabela.").showAndWait();
            }
        });

        btnAtualizar.setOnAction(e -> {
            List<Usuario> novaLista = ctrl.listar();
            tabela.setItems(FXCollections.observableArrayList(novaLista));
        });

        HBox botoes = new HBox(10, btnEditar, btnExcluir, btnAtualizar, btnVoltar);
        VBox layout = new VBox(10, tabela, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 700, 400));
        stage.show();
    }
}