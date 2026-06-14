package com.example.printlypoo.view.pedido;

import com.example.printlypoo.controller.fabricante.FabricanteDAO;
import com.example.printlypoo.controller.pedido.PedidoController;
import com.example.printlypoo.model.fabricante.Fabricante;
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

public class TelaInserirPedido {

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Inserir Pedido");

        Label lblFabricante        = new Label("Fabricante:");
        ComboBox<Fabricante> cmbFabricante = new ComboBox<>();

        // Carrega os fabricantes cadastrados no arquivo
        List<Fabricante> fabricantes = new FabricanteDAO().listarTodos();
        cmbFabricante.getItems().addAll(fabricantes);
        cmbFabricante.setPromptText("Selecione o fabricante");
        // O toString() do Fabricante já exibe "Nome (CNPJ)", então o ComboBox
        // mostra exatamente isso sem precisar de cellFactory extra

        if (fabricantes.isEmpty()) {
            cmbFabricante.setPromptText("Nenhum fabricante cadastrado");
            cmbFabricante.setDisable(true);
        }

        Label lblMaterial        = new Label("Material:");
        TextField txtMaterial    = new TextField();

        Label lblQuantidade      = new Label("Quantidade:");
        TextField txtQuantidade  = new TextField();

        Label lblValorTotal      = new Label("Valor Total (R$):");
        TextField txtValorTotal  = new TextField();

        Label lblStatus          = new Label("Status Inicial:");
        ComboBox<String> cmbStatus = new ComboBox<>();
        cmbStatus.getItems().addAll("Pendente", "Em análise", "Aprovado", "Em produção",
                "Aguardando envio", "Enviado", "Entregue", "Recusado");
        cmbStatus.setValue("Pendente");

        Label lblMotivoRecusa     = new Label("Motivo de Recusa:");
        TextField txtMotivoRecusa = new TextField();
        txtMotivoRecusa.setPromptText("Preencher apenas se recusado");

        Label lblEndereco        = new Label("Endereço de Entrega:");
        TextField txtEndereco    = new TextField();

        Label lblPrazo           = new Label("Prazo do Pedido (DD/MM/AAAA):");
        TextField txtPrazo       = new TextField();
        txtPrazo.setPromptText("Ex: 30/12/2025");

        Label lblData            = new Label("Data de Solicitação (DD/MM/AAAA):");
        TextField txtData        = new TextField();
        txtData.setText(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Button btnSalvar   = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            try {
                // Validação: fabricante selecionado
                if (cmbFabricante.getValue() == null) {
                    new Alert(Alert.AlertType.WARNING,
                            "Selecione um fabricante.").showAndWait();
                    return;
                }

                // Validação: campos obrigatórios
                if (txtMaterial.getText().trim().isEmpty()
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

                String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

                // Salva o nome do fabricante no pedido para exibição na consulta
                String nomeFabricante = cmbFabricante.getValue().getNome();

                Pedido pedido = new Pedido(
                        id,
                        nomeFabricante,
                        txtMaterial.getText().trim(),
                        String.valueOf(quantidade),
                        String.format("%.2f", valorTotal),
                        cmbStatus.getValue(),
                        txtMotivoRecusa.getText().trim(),
                        txtEndereco.getText().trim(),
                        txtPrazo.getText().trim(),
                        txtData.getText().trim()
                );

                new PedidoController().inserir(pedido);
                new Alert(Alert.AlertType.INFORMATION,
                        "Pedido inserido com sucesso! ID: " + id).showAndWait();
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

        grid.add(lblFabricante,   0, 0);  grid.add(cmbFabricante,   1, 0);
        grid.add(lblMaterial,     0, 1);  grid.add(txtMaterial,     1, 1);
        grid.add(lblQuantidade,   0, 2);  grid.add(txtQuantidade,   1, 2);
        grid.add(lblValorTotal,   0, 3);  grid.add(txtValorTotal,   1, 3);
        grid.add(lblStatus,       0, 4);  grid.add(cmbStatus,       1, 4);
        grid.add(lblMotivoRecusa, 0, 5);  grid.add(txtMotivoRecusa, 1, 5);
        grid.add(lblEndereco,     0, 6);  grid.add(txtEndereco,     1, 6);
        grid.add(lblPrazo,        0, 7);  grid.add(txtPrazo,        1, 7);
        grid.add(lblData,         0, 8);  grid.add(txtData,         1, 8);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        VBox layout = new VBox(10, grid, botoes);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 500, 420));
        stage.show();
    }
}