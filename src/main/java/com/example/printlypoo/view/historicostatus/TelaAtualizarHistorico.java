package com.example.printlypoo.view.historicostatus;

import com.example.printlypoo.controller.historicostatus.HistoricoStatusPedidoController;
import com.example.printlypoo.controller.pedido.PedidoController;
import com.example.printlypoo.model.historicostatus.HistoricoStatusPedido;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaAtualizarHistorico {

    public void exibir(HistoricoStatusPedido historico, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Atualizar Histórico - ID: " + historico.getid());

        Label lblIdPedido    = new Label("ID do Pedido:");
        TextField txtIdPedido = new TextField(historico.getidPedido());
        txtIdPedido.setEditable(false);
        txtIdPedido.setStyle("-fx-background-color: #eeeeee;");

        Label lblStatusAnterior    = new Label("Status Anterior:");
        TextField txtStatusAnterior = new TextField(historico.getstatusAnterior());

        Label lblNovoStatus    = new Label("Novo Status:");
        ComboBox<String> cmbNovoStatus = new ComboBox<>();
        cmbNovoStatus.getItems().addAll("Pendente", "Em análise", "Aprovado", "Em produção",
                "Aguardando envio", "Enviado", "Entregue", "Recusado");
        cmbNovoStatus.setValue(historico.getnovoStatus());

        Label lblObservacao    = new Label("Observação:");
        TextField txtObservacao = new TextField(historico.getobservacao());

        Label lblDataHora    = new Label("Data e Hora (DD/MM/AAAA HH:mm:ss):");
        TextField txtDataHora = new TextField(historico.getdataHoraMudanca());

        Button btnSalvar   = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            try {
                // Validação: campos obrigatórios
                if (txtStatusAnterior.getText().trim().isEmpty()
                        || cmbNovoStatus.getValue() == null
                        || txtDataHora.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING,
                            "Preencha todos os campos obrigatórios.").showAndWait();
                    return;
                }

                String novoStatusAtualizado = cmbNovoStatus.getValue();

                HistoricoStatusPedido atualizado = new HistoricoStatusPedido(
                        historico.getid(),
                        historico.getidPedido(),
                        txtStatusAnterior.getText().trim(),
                        novoStatusAtualizado,
                        txtObservacao.getText().trim(),
                        txtDataHora.getText().trim()
                );

                new HistoricoStatusPedidoController().atualizar(indice, atualizado);

                // Atualiza o status atual do pedido para refletir a mudança no histórico
                new PedidoController().atualizarStatus(
                        historico.getidPedido(), novoStatusAtualizado);

                new Alert(Alert.AlertType.INFORMATION,
                        "Histórico atualizado e status do pedido sincronizado!")
                        .showAndWait();
                stage.close();

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR,
                        "Erro ao atualizar: " + ex.getMessage()).showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));

        grid.add(lblIdPedido,       0, 0);  grid.add(txtIdPedido,       1, 0);
        grid.add(lblStatusAnterior, 0, 1);  grid.add(txtStatusAnterior, 1, 1);
        grid.add(lblNovoStatus,     0, 2);  grid.add(cmbNovoStatus,     1, 2);
        grid.add(lblObservacao,     0, 3);  grid.add(txtObservacao,     1, 3);
        grid.add(lblDataHora,       0, 4);  grid.add(txtDataHora,       1, 4);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        VBox layout = new VBox(10, grid, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 480, 300));
        stage.show();
    }
}
