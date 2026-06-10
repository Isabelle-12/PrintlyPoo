package com.example.printlypoo.view.fabricante;
import com.example.printlypoo.controller.fabricante.FabricanteDAO;
import com.example.printlypoo.model.fabricante.Fabricante;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaInserirFabricante {

    private final FabricanteDAO dao = new FabricanteDAO();

    public void exibir() {
        Stage stage = new Stage();
        stage.setTitle("Cadastrar Fabricante");
        stage.setResizable(false);

        // ── Campos ──────────────────────────────────────────────────────────
        TextField tfNome     = new TextField();
        TextField tfCnpj     = new TextField();
        TextField tfTelefone = new TextField();
        TextField tfEndereco = new TextField();

        tfNome.setPromptText("Nome / Razão Social");
        tfCnpj.setPromptText("XX.XXX.XXX/XXXX-XX");
        tfTelefone.setPromptText("(XX) XXXXX-XXXX");
        tfEndereco.setPromptText("Endereço completo");

        // Formata CNPJ ao sair do campo
        tfCnpj.focusedProperty().addListener((obs, antes, agora) -> {
            if (!agora) tfCnpj.setText(formatarCnpj(tfCnpj.getText()));
        });

        // Formata telefone ao sair do campo
        tfTelefone.focusedProperty().addListener((obs, antes, agora) -> {
            if (!agora) tfTelefone.setText(formatarTelefone(tfTelefone.getText()));
        });

        // ── Botão Salvar ─────────────────────────────────────────────────────
        Button btnSalvar   = new Button("Cadastrar");
        Button btnCancelar = new Button("Cancelar");

        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setMaxWidth(Double.MAX_VALUE);

        btnCancelar.setOnAction(e -> stage.close());

        btnSalvar.setOnAction(e -> {
            String nome     = tfNome.getText().trim();
            String cnpj     = tfCnpj.getText().trim();
            String telefone = tfTelefone.getText().trim();
            String endereco = tfEndereco.getText().trim();

            if (nome.isEmpty() || cnpj.isEmpty() || telefone.isEmpty() || endereco.isEmpty()) {
                alerta(Alert.AlertType.WARNING, "Preencha todos os campos.");
                return;
            }
            if (!validarCnpj(cnpj)) {
                alerta(Alert.AlertType.ERROR, "CNPJ inválido. Use o formato XX.XXX.XXX/XXXX-XX.");
                tfCnpj.requestFocus();
                return;
            }
            if (dao.buscarPorCnpj(cnpj) != null) {
                alerta(Alert.AlertType.ERROR, "Já existe um fabricante com este CNPJ.");
                return;
            }
            try {
                dao.inserir(new Fabricante(0, cnpj, nome, telefone, endereco));
                alerta(Alert.AlertType.INFORMATION, "Fabricante cadastrado com sucesso!");
                stage.close();
            } catch (Exception ex) {
                alerta(Alert.AlertType.ERROR, "Erro ao salvar: " + ex.getMessage());
            }
        });

        // ── Layout ───────────────────────────────────────────────────────────
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.setPadding(new Insets(20));

        adicionarLinha(form, 0, "Nome *",      tfNome);
        adicionarLinha(form, 1, "CNPJ *",      tfCnpj);
        adicionarLinha(form, 2, "Telefone *",  tfTelefone);
        adicionarLinha(form, 3, "Endereço *",  tfEndereco);

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setPadding(new Insets(0, 20, 20, 20));

        VBox root = new VBox(form, botoes);

        stage.setScene(new Scene(root, 400, 260));
        stage.show();
    }

    // ── Utilitários ──────────────────────────────────────────────────────────

    private void adicionarLinha(GridPane grid, int linha, String rotulo, TextField campo) {
        campo.setPrefWidth(260);
        grid.add(new Label(rotulo), 0, linha);
        grid.add(campo, 1, linha);
        GridPane.setHgrow(campo, Priority.ALWAYS);
    }

    private String formatarCnpj(String valor) {
        String d = valor.replaceAll("\\D", "");
        if (d.length() != 14) return valor;
        return String.format("%s.%s.%s/%s-%s",
                d.substring(0,2), d.substring(2,5), d.substring(5,8),
                d.substring(8,12), d.substring(12,14));
    }

    private boolean validarCnpj(String cnpj) {
        return cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");
    }

    private String formatarTelefone(String valor) {
        String d = valor.replaceAll("\\D", "");
        if (d.length() == 11)
            return String.format("(%s) %s-%s", d.substring(0,2), d.substring(2,7), d.substring(7));
        if (d.length() == 10)
            return String.format("(%s) %s-%s", d.substring(0,2), d.substring(2,6), d.substring(6));
        return valor;
    }

    private void alerta(Alert.AlertType tipo, String msg) {
        Alert a = new Alert(tipo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
