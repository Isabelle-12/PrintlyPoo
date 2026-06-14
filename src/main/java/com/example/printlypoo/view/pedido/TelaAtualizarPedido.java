package com.example.printlypoo.view.pedido;

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
import java.util.UUID;

public class TelaAtualizarPedido {

    public void exibir(Pedido pedido, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Atualizar Pedido - ID: " + pedido.getid());

        Label lblFabricante      = new Label("Fabricante:");
        TextField txtFabricante  = new TextField(pedido.getfabricante());

        Label lblMaterial        = new Label("Material:");
        TextField txtMaterial    = new TextField(pedido.getmaterial());

        Label lblQuantidade      = new Label("Quantidade:");
        TextField txtQuantidade  = new TextField(pedido.getquantidade());

        Label lblValorTotal      = new Label("Valor Total (R$):");
        TextField txtValorTotal  = new TextField(pedido.getvalorTotal());

        Label lblStatus          = new Label("Status:");
        ComboBox<String> cmbStatus = new ComboBox<>();
        cmbStatus.getItems().addAll("Pendente", "Em análise", "Aprovado", "Em produção",
                "Aguardando envio", "Enviado", "Entregue", "Recusado");
        cmbStatus.setValue(pedido.getstatus());

        Label lblMotivoRecusa    = new Label("Motivo de Recusa:");
        TextField txtMotivoRecusa = new TextField(pedido.getmotivoRecusa());

        Label lblObservacaoStatus = new Label("Observação da mudança de status:");
        TextField txtObservacao  = new TextField();
        txtObservacao.setPromptText("Descreva o motivo da mudança de status");

        Label lblEndereco        = new Label("Endereço de Entrega:");
        TextField txtEndereco    = new TextField(pedido.getenderecoEntrega());

        Label lblPrazo           = new Label("Prazo do Pedido (DD/MM/AAAA):");
        TextField txtPrazo       = new TextField(pedido.getprazoPedido());

        Label lblData            = new Label("Data de Solicitação (DD/MM/AAAA):");
        TextField txtData        = new TextField(pedido.getdataSolicitacao());

        Button btnSalvar   = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            try {
                // Validação: campos obrigatórios
                if (txtFabricante.getText().trim().isEmpty()
                        || txtMaterial.getText().trim().isEmpty()
                        || txtQuantidade.getText().trim().isEmpty()
                        || txtValorTotal.getText().trim().isEmpty()
                        || txtEndereco.getText().trim().isEmpty()
                        || txtPrazo.getText().trim().isEmpty()
                        || txtData.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING,
                            "Preencha todos os campos obrigatórios.").showAndWait();
                    return;
                }

                // Validação: quantidade deve ser numérica
                int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
                if (quantidade <= 0) {
                    new Alert(Alert.AlertType.WARNING,
                            "A quantidade deve ser um número inteiro positivo.").showAndWait();
                    return;
                }

                // Validação: valor total deve ser numérico
                double valorTotal = Double.parseDouble(
                        txtValorTotal.getText().trim().replace(",", "."));
                if (valorTotal < 0) {
                    new Alert(Alert.AlertType.WARNING,
                            "O valor total não pode ser negativo.").showAndWait();
                    return;
                }

                // Validação: formato de data DD/MM/AAAA
                String regexData = "\\d{2}/\\d{2}/\\d{4}";
                if (!txtPrazo.getText().trim().matches(regexData)
                        || !txtData.getText().trim().matches(regexData)) {
                    new Alert(Alert.AlertType.WARNING,
                            "Datas devem estar no formato DD/MM/AAAA.").showAndWait();
                    return;
                }

                String statusAnterior = pedido.getstatus();
                String novoStatus     = cmbStatus.getValue();

                Pedido atualizado = new Pedido(
                        pedido.getid(),
                        txtFabricante.getText().trim(),
                        txtMaterial.getText().trim(),
                        String.valueOf(quantidade),
                        String.format("%.2f", valorTotal),
                        novoStatus,
                        txtMotivoRecusa.getText().trim(),
                        txtEndereco.getText().trim(),
                        txtPrazo.getText().trim(),
                        txtData.getText().trim()
                );

                new PedidoController().atualizar(indice, atualizado);

                // Se o status foi alterado, registra automaticamente no histórico
                if (!statusAnterior.equals(novoStatus)) {
                    String dataHora = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    String idHistorico = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                    String observacao  = txtObservacao.getText().trim().isEmpty()
                            ? "Status alterado" : txtObservacao.getText().trim();

                    HistoricoStatusPedido historico = new HistoricoStatusPedido(
                            idHistorico,
                            pedido.getid(),
                            statusAnterior,
                            novoStatus,
                            observacao,
                            dataHora
                    );
                    new HistoricoStatusPedidoController().inserir(historico);
                }

                new Alert(Alert.AlertType.INFORMATION, "Pedido atualizado com sucesso!")
                        .showAndWait();
                stage.close();

            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR,
                        "Quantidade e Valor Total devem ser números válidos.").showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));

        grid.add(lblFabricante,       0, 0);  grid.add(txtFabricante,       1, 0);
        grid.add(lblMaterial,         0, 1);  grid.add(txtMaterial,         1, 1);
        grid.add(lblQuantidade,       0, 2);  grid.add(txtQuantidade,       1, 2);
        grid.add(lblValorTotal,       0, 3);  grid.add(txtValorTotal,       1, 3);
        grid.add(lblStatus,           0, 4);  grid.add(cmbStatus,           1, 4);
        grid.add(lblMotivoRecusa,     0, 5);  grid.add(txtMotivoRecusa,     1, 5);
        grid.add(lblObservacaoStatus, 0, 6);  grid.add(txtObservacao,       1, 6);
        grid.add(lblEndereco,         0, 7);  grid.add(txtEndereco,         1, 7);
        grid.add(lblPrazo,            0, 8);  grid.add(txtPrazo,            1, 8);
        grid.add(lblData,             0, 9);  grid.add(txtData,             1, 9);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        VBox layout = new VBox(10, grid, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 520, 460));
        stage.show();
    }
}
