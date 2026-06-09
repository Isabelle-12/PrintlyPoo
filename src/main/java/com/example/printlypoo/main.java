package com.example.printlypoo;


import com.example.printlypoo.view.usuario.TelaConsultarUsuario;
import com.example.printlypoo.view.usuario.TelaInserirUsuario;
import com.example.printlypoo.view.anuncio.TelaConsultarAnuncio;
import com.example.printlypoo.view.anuncio.TelaInserirAnuncio;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Printly - Sistema de Gestão");

        Label lblTitulo = new Label("Printly");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        //---------------------------------------------------------------------------------------//
        //Isabelle - Usuarios
        Label lblUsuarios = new Label("Gerenciamento de Usuários");
        lblUsuarios.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button btnInserir = new Button("Cadastrar Usuário");
        Button btnConsultar = new Button("Consultar Usuários");

        btnInserir.setMaxWidth(200);
        btnConsultar.setMaxWidth(200);

        btnInserir.setOnAction(e -> new TelaInserirUsuario().exibir());
        btnConsultar.setOnAction(e -> new TelaConsultarUsuario().exibir());

       //---------------------------------------------------------------------------------------//
        // Isabelle - Anuncios
        Label lblAnuncios = new Label("Gerenciamento de Anúncios");
        lblAnuncios.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button btnInserirAnuncio = new Button("Cadastrar Anúncio");
        Button btnConsultarAnuncio = new Button("Consultar Anúncios");

        btnInserirAnuncio.setMaxWidth(200);
        btnConsultarAnuncio.setMaxWidth(200);

        btnInserirAnuncio.setOnAction(e -> new TelaInserirAnuncio().exibir());
        btnConsultarAnuncio.setOnAction(e -> new TelaConsultarAnuncio().exibir());

        //---------------------------------------------------------------------------------------//


        VBox layout = new VBox(15,
                lblTitulo,
                new Separator(),
                lblUsuarios,
                btnInserir,
                btnConsultar,
                new Separator(),
                lblAnuncios,
                btnInserirAnuncio,
                btnConsultarAnuncio
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));

        stage.setScene(new Scene(layout, 350, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}