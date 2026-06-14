package com.example.printlypoo.view.historicostatus;

import com.example.printlypoo.controller.historicostatus.HistoricoStatusPedidoController;
import com.example.printlypoo.controller.pedido.PedidoController;
import com.example.printlypoo.model.historicostatus.HistoricoStatusPedido;
import com.example.printlypoo.model.pedido.Pedido;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class TelaInserirHistorico {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Registrar Mudança de Status");

        // Carrega os pedidos para que o usuário escolha por ID
        PedidoController pedidoCtrl = new PedidoController();
        List<Pedido> pedidos = pedidoCtrl.listar();

        Label lblIdPedido    = new Label("Pedido:");
        ComboBox<String> cmbIdPedido = new ComboBox<>();
        for (Pedido p : pedidos) {
            cmbIdPedido.getItems().add(p.getid() + " - " + p.getfabricante()
                    + " (" + p.getstatus() + ")");
        }
        cmbIdPedido.setPromptText("Selecione o pedido");

        Label lblStatusAnterior    = new Label("Status Anterior:");
        TextField txtStatusAnterior = new TextField();
        txtStatusAnterior.setEditable(false);
        txtStatusAnterior.setStyle("-fx-background-color: #eeeeee;");

        // Ao selecionar o pedido, preenche automaticamente o status anterior
        cmbIdPedido.setOnAction(e -> {
            int idx = cmbIdPedido.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                txtStatusAnterior.setText(pedidos.get(idx).getstatus());
            }
        });

        Label lblNovoStatus    = new Label("Novo Status:");
        ComboBox<String> cmbNovoStatus = new ComboBox<>();
        cmbNovoStatus.getItems().addAll("Pendente", "Em análise", "Aprovado", "Em produção",
                "Aguardando envio", "Enviado", "Entregue", "Recusado");
        cmbNovoStatus.setPromptText("Selecione o novo status");

        Label lblObservacao    = new Label("Observação:");
        TextField txtObservacao = new TextField();
        txtObservacao.setPromptText("Motivo ou comentário sobre a mudança");

        Label lblDataHora    = new Label("Data e Hora da Mudança:");
        TextField txtDataHora = new TextField();
        // Preenche automaticamente com a data e hora atuais
        txtDataHora.setText(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        Button btnSalvar   = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            try {
                // Validação: pedido selecionado
                int idxPedido = cmbIdPedido.getSelectionModel().getSelectedIndex();
                if (idxPedido < 0) {
                    new Alert(Alert.AlertType.WARNING,
                            "Selecione um pedido.").showAndWait();
                    return;
                }

                // Validação: novo status selecionado
                if (cmbNovoStatus.getValue() == null) {
                    new Alert(Alert.AlertType.WARNING,
                            "Selecione o novo status.").showAndWait();
                    return;
                }

                // Validação: novo status diferente do anterior
                if (cmbNovoStatus.getValue().equals(txtStatusAnterior.getText())) {
                    new Alert(Alert.AlertType.WARNING,
                            "O novo status deve ser diferente do status atual.").showAndWait();
                    return;
                }

                // Validação: campos obrigatórios
                if (txtDataHora.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING,
                            "Informe a data e hora da mudança.").showAndWait();
                    return;
                }

                Pedido pedidoSelecionado = pedidos.get(idxPedido);
                String idHistorico = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

                HistoricoStatusPedido historico = new HistoricoStatusPedido(
                        idHistorico,
                        pedidoSelecionado.getid(),
                        txtStatusAnterior.getText().trim(),
                        cmbNovoStatus.getValue(),
                        txtObservacao.getText().trim(),
                        txtDataHora.getText().trim()
                );

                new HistoricoStatusPedidoController().inserir(historico);

                // Atualiza o status do pedido original para refletir a mudança
                pedidoCtrl.atualizarStatus(pedidoSelecionado.getid(), cmbNovoStatus.getValue());

                new Alert(Alert.AlertType.INFORMATION,
                        "Histórico registrado e status do pedido atualizado!")
                        .showAndWait();
                stage.close();

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR,
                        "Erro ao salvar: " + ex.getMessage()).showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));

        grid.add(lblIdPedido,       0, 0);  grid.add(cmbIdPedido,       1, 0);
        grid.add(lblStatusAnterior, 0, 1);  grid.add(txtStatusAnterior, 1, 1);
        grid.add(lblNovoStatus,     0, 2);  grid.add(cmbNovoStatus,     1, 2);
        grid.add(lblObservacao,     0, 3);  grid.add(txtObservacao,     1, 3);
        grid.add(lblDataHora,       0, 4);  grid.add(txtDataHora,       1, 4);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        VBox layout = new VBox(10, grid, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 500, 300));
        stage.show();
    }
}
