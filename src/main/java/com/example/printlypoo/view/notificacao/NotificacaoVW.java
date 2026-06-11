package com.example.printlypoo.view;

import com.example.printlypoo.controller.NotificacaoController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NotificacaoVW {

    private NotificacaoController controller = new NotificacaoController();

    public void show(Stage stage) {

        TextField txtTipo = new TextField();
        txtTipo.setPromptText("Tipo");

        TextField txtTitulo = new TextField();
        txtTitulo.setPromptText("Título");

        TextField txtMensagem = new TextField();
        txtMensagem.setPromptText("Mensagem");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        TextArea area = new TextArea();
        area.setEditable(false);

        Button btnCriar = new Button("Criar");
        Button btnListar = new Button("Listar");

        btnCriar.setOnAction(e -> {
            try {
                controller.criarNotificacao(
                        txtTipo.getText(),
                        txtTitulo.getText(),
                        txtMensagem.getText(),
                        txtEmail.getText()
                );

                area.setText("Criado!");

            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnListar.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();

            controller.listarNotificacoes().forEach(n ->
                    sb.append("ID: ")
                            .append(n.getId())
                            .append(" | ")
                            .append(n.getTitulo())
                            .append(" | Lida: ")
                            .append(n.isLida())
                            .append("\n")
            );

            area.setText(sb.toString());
        });
        TextField txtId = new TextField();
        txtId.setPromptText("ID da Notificação");

        Button btnAtualizar = new Button("Atualizar");
        Button btnRemover = new Button("Remover");
        Button btnMarcarLida = new Button("Marcar como Lida");

        btnAtualizar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                controller.atualizarNotificacao(id, txtTipo.getText(), txtTitulo.getText(),
                        txtMensagem.getText(), txtEmail.getText());
                area.setText("Atualizado com sucesso!");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnRemover.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                controller.removerNotificacao(id);
                area.setText("Removido com sucesso!");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        btnMarcarLida.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                controller.marcarComoLida(id);
                area.setText("Marcado como lida!");
            } catch (Exception ex) {
                area.setText("Erro: " + ex.getMessage());
            }
        });

        VBox root = new VBox(10, txtId, txtTipo, txtTitulo, txtMensagem, txtEmail, btnCriar, btnListar, btnAtualizar, btnRemover, btnMarcarLida, area);

        Scene scene = new Scene(root, 450, 550);

        stage.setTitle("Notificações");
        stage.setScene(scene);
        stage.show();
    }
}