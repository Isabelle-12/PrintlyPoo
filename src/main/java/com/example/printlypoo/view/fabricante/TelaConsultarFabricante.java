package com.example.printlypoo.view.fabricante;
import com.example.printlypoo.controller.fabricante.FabricanteDAO;
import com.example.printlypoo.controller.fabricante.ImpressoraDAO;
import com.example.printlypoo.model.fabricante.Fabricante;
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
import java.util.Optional;

public class TelaConsultarFabricante {

    private final FabricanteDAO fabricanteDAO = new FabricanteDAO();
    private final ImpressoraDAO impressoraDAO = new ImpressoraDAO();

    private TableView<Fabricante> tabela;
    private ObservableList<Fabricante> dados;

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Consultar Fabricantes");

        // ── Tabela ───────────────────────────────────────────────────────────
        tabela = construirTabela();

        // ── Botões ───────────────────────────────────────────────────────────
        Button btnEditar  = new Button("Editar");
        Button btnExcluir = new Button("Excluir");
        Button btnFechar  = new Button("Fechar");

        btnEditar.setMaxWidth(120);
        btnExcluir.setMaxWidth(120);
        btnFechar.setMaxWidth(120);

        btnEditar.setOnAction(e -> {
            Fabricante sel = tabela.getSelectionModel().getSelectedItem();
            if (sel == null) { alerta(Alert.AlertType.WARNING, "Selecione um fabricante."); return; }
            abrirEdicao(sel, stage);
        });

        btnExcluir.setOnAction(e -> excluir(stage));
        btnFechar.setOnAction(e -> stage.close());

        // Duplo clique abre edição
        tabela.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && tabela.getSelectionModel().getSelectedItem() != null)
                abrirEdicao(tabela.getSelectionModel().getSelectedItem(), stage);
        });

        HBox botoes = new HBox(10, btnEditar, btnExcluir, btnFechar);
        botoes.setAlignment(Pos.CENTER_RIGHT);
        botoes.setPadding(new Insets(10, 10, 10, 10));

        VBox root = new VBox(tabela, botoes);
        VBox.setVgrow(tabela, Priority.ALWAYS);

        stage.setScene(new Scene(root, 700, 420));
        stage.show();
        carregarTabela();
    }

    // ── Tabela ───────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private TableView<Fabricante> construirTabela() {
        TableView<Fabricante> tv = new TableView<>();
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.setPlaceholder(new Label("Nenhum fabricante cadastrado."));

        TableColumn<Fabricante, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()).asObject());
        colId.setMaxWidth(50);

        TableColumn<Fabricante, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Fabricante, String> colCnpj = new TableColumn<>("CNPJ");
        colCnpj.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCnpj()));

        TableColumn<Fabricante, String> colTel = new TableColumn<>("Telefone");
        colTel.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefone()));

        TableColumn<Fabricante, String> colEnd = new TableColumn<>("Endereço");
        colEnd.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEndereco()));

        tv.getColumns().addAll(colId, colNome, colCnpj, colTel, colEnd);

        dados = FXCollections.observableArrayList();
        tv.setItems(dados);
        return tv;
    }

    private void carregarTabela() {
        dados.setAll(fabricanteDAO.listarTodos());
    }

    // ── Edição (modal inline) ─────────────────────────────────────────────────

    private void abrirEdicao(Fabricante fab, Stage owner) {
        Stage modal = new Stage();
        modal.initModality(Modality.WINDOW_MODAL);
        modal.initOwner(owner);
        modal.setTitle("Editar Fabricante");
        modal.setResizable(false);

        TextField tfNome     = new TextField(fab.getNome());
        TextField tfCnpj     = new TextField(fab.getCnpj());
        TextField tfTelefone = new TextField(fab.getTelefone());
        TextField tfEndereco = new TextField(fab.getEndereco());

        Button btnSalvar   = new Button("Salvar Alterações");
        Button btnCancelar = new Button("Cancelar");
        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setMaxWidth(Double.MAX_VALUE);

        btnCancelar.setOnAction(e -> modal.close());
        btnSalvar.setOnAction(e -> {
            String nome     = tfNome.getText().trim();
            String cnpj     = tfCnpj.getText().trim();
            String telefone = tfTelefone.getText().trim();
            String endereco = tfEndereco.getText().trim();

            if (nome.isEmpty() || cnpj.isEmpty() || telefone.isEmpty() || endereco.isEmpty()) {
                alerta(Alert.AlertType.WARNING, "Preencha todos os campos.");
                return;
            }
            try {
                fab.setNome(nome);
                fab.setCnpj(cnpj);
                fab.setTelefone(telefone);
                fab.setEndereco(endereco);
                fabricanteDAO.atualizar(fab);
                alerta(Alert.AlertType.INFORMATION, "Fabricante atualizado com sucesso!");
                carregarTabela();
                modal.close();
            } catch (IOException ex) {
                alerta(Alert.AlertType.ERROR, "Erro ao salvar: " + ex.getMessage());
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(12);
        form.setPadding(new Insets(20));

        adicionarLinha(form, 0, "Nome *",     tfNome);
        adicionarLinha(form, 1, "CNPJ *",     tfCnpj);
        adicionarLinha(form, 2, "Telefone *", tfTelefone);
        adicionarLinha(form, 3, "Endereço *", tfEndereco);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setPadding(new Insets(0, 20, 20, 20));

        modal.setScene(new Scene(new VBox(form, botoes), 400, 260));
        modal.showAndWait();
    }

    // ── Exclusão ──────────────────────────────────────────────────────────────

    private void excluir(Stage owner) {
        Fabricante sel = tabela.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta(Alert.AlertType.WARNING, "Selecione um fabricante."); return; }

        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.initOwner(owner);
        conf.setTitle("Confirmar Exclusão");
        conf.setHeaderText("Excluir \"" + sel.getNome() + "\"?");
        conf.setContentText("Todas as impressoras vinculadas também serão removidas. Esta ação não pode ser desfeita.");

        Optional<ButtonType> resp = conf.showAndWait();
        if (resp.isPresent() && resp.get() == ButtonType.OK) {
            try {
                impressoraDAO.excluirPorFabricante(sel.getId());
                fabricanteDAO.excluir(sel.getId());
                carregarTabela();
                alerta(Alert.AlertType.INFORMATION, "Fabricante excluído com sucesso.");
            } catch (IOException ex) {
                alerta(Alert.AlertType.ERROR, "Erro ao excluir: " + ex.getMessage());
            }
        }
    }

    // ── Utilitário ────────────────────────────────────────────────────────────

    private void adicionarLinha(GridPane grid, int linha, String rotulo, TextField campo) {
        campo.setPrefWidth(260);
        grid.add(new Label(rotulo), 0, linha);
        grid.add(campo, 1, linha);
        GridPane.setHgrow(campo, Priority.ALWAYS);
    }

    private void alerta(Alert.AlertType tipo, String msg) {
        Alert a = new Alert(tipo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}

