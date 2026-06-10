package com.example.printlypoo.view.fabricante;

import com.example.printlypoo.controller.fabricante.FabricanteDAO;
import com.example.printlypoo.controller.fabricante.ImpressoraDAO;
import com.example.printlypoo.model.fabricante.Fabricante;
import com.example.printlypoo.model.fabricante.Impressora;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class TelaInserirImpressora {

    private final ImpressoraDAO impressoraDAO = new ImpressoraDAO();
    private final FabricanteDAO fabricanteDAO = new FabricanteDAO();

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Impressora");
        stage.setResizable(false);

        // ── ComboBox de fabricantes ──────────────────────────────────────────
        List<Fabricante> fabricantes = fabricanteDAO.listarTodos();
        if (fabricantes.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText(null);
            a.setContentText("Cadastre ao menos um fabricante antes de adicionar impressoras.");
            a.showAndWait();
            return;
        }

        ComboBox<Fabricante> cbFabricante = new ComboBox<>(
                FXCollections.observableArrayList(fabricantes));
        cbFabricante.setPromptText("Selecione o fabricante");
        cbFabricante.setMaxWidth(Double.MAX_VALUE);
        cbFabricante.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(Fabricante f) {
                return f == null ? "" : f.getNome() + " (" + f.getCnpj() + ")";
            }
            @Override public Fabricante fromString(String s) { return null; }
        });

        // ── Campos ──────────────────────────────────────────────────────────
        TextField tfModelo    = new TextField();
        TextField tfVolume    = new TextField();
        TextField tfMateriais = new TextField();
        TextField tfQtd       = new TextField();

        tfModelo.setPromptText("Ex: Bambu Lab X1, Ender 3");
        tfVolume.setPromptText("Ex: 220000.00");
        tfMateriais.setPromptText("Ex: PLA, ABS, PETG");
        tfQtd.setPromptText("Ex: 2");

        // Aceita apenas dígitos e ponto decimal no campo volume
        tfVolume.textProperty().addListener((obs, ant, novo) -> {
            if (!novo.matches("\\d*\\.?\\d*")) tfVolume.setText(ant);
        });
        // Aceita apenas inteiros no campo quantidade
        tfQtd.textProperty().addListener((obs, ant, novo) -> {
            if (!novo.matches("\\d*")) tfQtd.setText(ant);
        });

        // ── Botões ───────────────────────────────────────────────────────────
        Button btnSalvar   = new Button("Cadastrar");
        Button btnCancelar = new Button("Cancelar");
        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setMaxWidth(Double.MAX_VALUE);

        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            if (cbFabricante.getValue() == null) {
                alerta(Alert.AlertType.WARNING, "Selecione o fabricante."); return;
            }
            String modelo    = tfModelo.getText().trim();
            String volStr    = tfVolume.getText().trim();
            String materiais = tfMateriais.getText().trim();
            String qtdStr    = tfQtd.getText().trim();

            if (modelo.isEmpty() || volStr.isEmpty() || materiais.isEmpty() || qtdStr.isEmpty()) {
                alerta(Alert.AlertType.WARNING, "Preencha todos os campos."); return;
            }

            double volume;
            int quantidade;
            try {
                volume = Double.parseDouble(volStr);
                if (volume <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                alerta(Alert.AlertType.ERROR, "Volume máximo deve ser um número positivo.");
                tfVolume.requestFocus(); return;
            }
            try {
                quantidade = Integer.parseInt(qtdStr);
                if (quantidade <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                alerta(Alert.AlertType.ERROR, "Quantidade deve ser um número inteiro positivo.");
                tfQtd.requestFocus(); return;
            }

            try {
                Impressora nova = new Impressora(
                        0, cbFabricante.getValue().getId(),
                        modelo, volume, materiais, quantidade);
                impressoraDAO.inserir(nova);
                alerta(Alert.AlertType.INFORMATION, "Impressora cadastrada com sucesso!");
                stage.close();
            } catch (Exception ex) {
                alerta(Alert.AlertType.ERROR, "Erro ao salvar: " + ex.getMessage());
            }
        });

        // ── Layout ───────────────────────────────────────────────────────────
        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(12);
        form.setPadding(new Insets(20));

        adicionarLinhaCombo(form, 0, "Fabricante *", cbFabricante);
        adicionarLinha(form, 1, "Modelo *",           tfModelo);
        adicionarLinha(form, 2, "Volume Máx. (cm³) *", tfVolume);
        adicionarLinha(form, 3, "Materiais *",         tfMateriais);
        adicionarLinha(form, 4, "Quantidade *",        tfQtd);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setPadding(new Insets(0, 20, 20, 20));

        stage.setScene(new Scene(new VBox(form, botoes), 420, 320));
        stage.show();
    }

    // ── Utilitários ──────────────────────────────────────────────────────────

    private void adicionarLinha(GridPane grid, int linha, String rotulo, TextField campo) {
        campo.setPrefWidth(260);
        grid.add(new Label(rotulo), 0, linha);
        grid.add(campo, 1, linha);
        GridPane.setHgrow(campo, Priority.ALWAYS);
    }

    private void adicionarLinhaCombo(GridPane grid, int linha, String rotulo, ComboBox<?> combo) {
        combo.setPrefWidth(260);
        grid.add(new Label(rotulo), 0, linha);
        grid.add(combo, 1, linha);
        GridPane.setHgrow(combo, Priority.ALWAYS);
    }

    private void alerta(Alert.AlertType tipo, String msg) {
        Alert a = new Alert(tipo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}

