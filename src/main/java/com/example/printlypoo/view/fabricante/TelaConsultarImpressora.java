package com.example.printlypoo.view.fabricante;
import com.example.printlypoo.controller.fabricante.FabricanteDAO;
import com.example.printlypoo.controller.fabricante.ImpressoraDAO;
import com.example.printlypoo.model.fabricante.Fabricante;
import com.example.printlypoo.model.fabricante.Impressora;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TelaConsultarImpressora {

    private final ImpressoraDAO impressoraDAO = new ImpressoraDAO();
    private final FabricanteDAO fabricanteDAO = new FabricanteDAO();

    private TableView<Impressora> tabela;
    private ObservableList<Impressora> dados;

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Consultar Impressoras");

        tabela = construirTabela();

        Button btnEditar  = new Button("Editar");
        Button btnExcluir = new Button("Excluir");
        Button btnFechar  = new Button("Fechar");

        btnEditar.setMaxWidth(120);
        btnExcluir.setMaxWidth(120);
        btnFechar.setMaxWidth(120);

        btnEditar.setOnAction(e -> {
            Impressora sel = tabela.getSelectionModel().getSelectedItem();
            if (sel == null) { alerta(Alert.AlertType.WARNING, "Selecione uma impressora."); return; }
            abrirEdicao(sel, stage);
        });

        btnExcluir.setOnAction(e -> excluir(stage));
        btnFechar.setOnAction(e -> stage.close());

        tabela.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && tabela.getSelectionModel().getSelectedItem() != null)
                abrirEdicao(tabela.getSelectionModel().getSelectedItem(), stage);
        });

        HBox botoes = new HBox(10, btnEditar, btnExcluir, btnFechar);
        botoes.setAlignment(Pos.CENTER_RIGHT);
        botoes.setPadding(new Insets(10));

        VBox root = new VBox(tabela, botoes);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        stage.setScene(new Scene(root, 750, 420));
        stage.show();
        carregarTabela();
    }

    // ── Tabela ───────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private TableView<Impressora> construirTabela() {
        TableView<Impressora> tv = new TableView<>();
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.setPlaceholder(new Label("Nenhuma impressora cadastrada."));

        TableColumn<Impressora, String> colFab = new TableColumn<>("Fabricante");
        colFab.setCellValueFactory(c -> {
            Fabricante f = fabricanteDAO.buscarPorId(c.getValue().getIdFabricante());
            return new SimpleStringProperty(f != null ? f.getNome() : "—");
        });

        TableColumn<Impressora, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getModelo()));

        TableColumn<Impressora, Double> colVol = new TableColumn<>("Volume Máx. (cm³)");
        colVol.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getVolumeMaximo()).asObject());

        TableColumn<Impressora, String> colMat = new TableColumn<>("Materiais");
        colMat.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMateriais()));

        TableColumn<Impressora, Integer> colQtd = new TableColumn<>("Qtd.");
        colQtd.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getQuantidade()).asObject());
        colQtd.setMaxWidth(60);

        tv.getColumns().addAll(colFab, colModelo, colVol, colMat, colQtd);

        dados = FXCollections.observableArrayList();
        tv.setItems(dados);
        return tv;
    }

    private void carregarTabela() {
        dados.setAll(impressoraDAO.listarTodos());
    }

    // ── Edição ────────────────────────────────────────────────────────────────

    private void abrirEdicao(Impressora imp, Stage owner) {
        Stage modal = new Stage();
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initOwner(owner);
        modal.setTitle("Editar Impressora");
        modal.setResizable(false);

        // ComboBox de fabricantes
        List<Fabricante> fabricantes = fabricanteDAO.listarTodos();
        ComboBox<Fabricante> cbFab = new ComboBox<>(
                FXCollections.observableArrayList(fabricantes));
        cbFab.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(Fabricante f) {
                return f == null ? "" : f.getNome() + " (" + f.getCnpj() + ")";
            }
            @Override public Fabricante fromString(String s) { return null; }
        });
        cbFab.setMaxWidth(Double.MAX_VALUE);
        // Pré-selecionar fabricante atual
        fabricantes.stream()
                .filter(f -> f.getId() == imp.getIdFabricante())
                .findFirst().ifPresent(cbFab::setValue);

        TextField tfModelo    = new TextField(imp.getModelo());
        TextField tfVolume    = new TextField(String.valueOf(imp.getVolumeMaximo()));
        TextField tfMateriais = new TextField(imp.getMateriais());
        TextField tfQtd       = new TextField(String.valueOf(imp.getQuantidade()));

        tfVolume.textProperty().addListener((obs, ant, novo) -> {
            if (!novo.matches("\\d*\\.?\\d*")) tfVolume.setText(ant);
        });
        tfQtd.textProperty().addListener((obs, ant, novo) -> {
            if (!novo.matches("\\d*")) tfQtd.setText(ant);
        });

        Button btnSalvar   = new Button("Salvar Alterações");
        Button btnCancelar = new Button("Cancelar");
        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setMaxWidth(Double.MAX_VALUE);

        btnCancelar.setOnAction(e -> modal.close());
        btnSalvar.setOnAction(e -> {
            if (cbFab.getValue() == null) {
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
                alerta(Alert.AlertType.ERROR, "Volume deve ser um número positivo.");
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
                imp.setIdFabricante(cbFab.getValue().getId());
                imp.setModelo(modelo);
                imp.setVolumeMaximo(volume);
                imp.setMateriais(materiais);
                imp.setQuantidade(quantidade);
                impressoraDAO.atualizar(imp);
                alerta(Alert.AlertType.INFORMATION, "Impressora atualizada com sucesso!");
                carregarTabela();
                modal.close();
            } catch (IOException ex) {
                alerta(Alert.AlertType.ERROR, "Erro ao salvar: " + ex.getMessage());
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(12);
        form.setPadding(new Insets(20));

        adicionarLinhaCombo(form, 0, "Fabricante *",        cbFab);
        adicionarLinha(form,      1, "Modelo *",            tfModelo);
        adicionarLinha(form,      2, "Volume Máx. (cm³) *", tfVolume);
        adicionarLinha(form,      3, "Materiais *",         tfMateriais);
        adicionarLinha(form,      4, "Quantidade *",        tfQtd);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setPadding(new Insets(0, 20, 20, 20));

        modal.setScene(new Scene(new VBox(form, botoes), 430, 320));
        modal.showAndWait();
    }

    // ── Exclusão ──────────────────────────────────────────────────────────────

    private void excluir(Stage owner) {
        Impressora sel = tabela.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta(Alert.AlertType.WARNING, "Selecione uma impressora."); return; }

        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.initOwner(owner);
        conf.setTitle("Confirmar Exclusão");
        conf.setHeaderText("Excluir impressora \"" + sel.getModelo() + "\"?");
        conf.setContentText("Esta ação não pode ser desfeita.");

        Optional<ButtonType> resp = conf.showAndWait();
        if (resp.isPresent() && resp.get() == ButtonType.OK) {
            try {
                impressoraDAO.excluir(sel.getId());
                carregarTabela();
                alerta(Alert.AlertType.INFORMATION, "Impressora removida com sucesso.");
            } catch (IOException ex) {
                alerta(Alert.AlertType.ERROR, "Erro ao excluir: " + ex.getMessage());
            }
        }
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


