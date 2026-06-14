package com.example.printlypoo.view.historicostatus;

import com.example.printlypoo.controller.historicostatus.HistoricoStatusPedidoController;
import com.example.printlypoo.model.historicostatus.HistoricoStatusPedido;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaExcluirHistorico {

    public void exibir(HistoricoStatusPedido historico, int indice) {
        Stage stage = new Stage();
        stage.setTitle("Excluir Histórico - ID: " + historico.getid());

        Label lblConfirmacao = new Label("Tem certeza que deseja excluir o registro abaixo?");

        // Exibe os dados do histórico para o usuário confirmar
        Label lblDados = new Label(
                "ID Histórico:    " + historico.getid()              + "\n" +
                        "ID Pedido:       " + historico.getidPedido()        + "\n" +
                        "Status Anterior: " + historico.getstatusAnterior()  + "\n" +
                        "Novo Status:     " + historico.getnovoStatus()      + "\n" +
                        "Observação:      " + historico.getobservacao()      + "\n" +
                        "Data/Hora:       " + historico.getdataHoraMudanca()
        );
        lblDados.setStyle("-fx-font-family: monospace;");

        Button btnExcluir  = new Button("Confirmar Exclusão");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        btnExcluir.setOnAction(e -> {
            new HistoricoStatusPedidoController().excluir(indice);
            new Alert(Alert.AlertType.INFORMATION,
                    "Registro de histórico excluído com sucesso!").showAndWait();
            stage.close();
        });

        HBox botoes = new HBox(10, btnExcluir, btnCancelar);
        VBox layout = new VBox(12, lblConfirmacao, lblDados, botoes);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 420, 280));
        stage.show();
    }
}
