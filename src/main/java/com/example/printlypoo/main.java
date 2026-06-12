    package com.example.printlypoo;


    import com.example.printlypoo.view.avaliacao.AvaliacaoVW;
    import com.example.printlypoo.view.notificacao.NotificacaoVW;
    import com.example.printlypoo.view.usuario.TelaConsultarUsuario;
    import com.example.printlypoo.view.usuario.TelaInserirUsuario;
    import com.example.printlypoo.view.anuncio.TelaConsultarAnuncio;
    import com.example.printlypoo.view.anuncio.TelaInserirAnuncio;
    import com.example.printlypoo.view.fabricante.TelaConsultarFabricante;
    import com.example.printlypoo.view.fabricante.TelaInserirFabricante;
    import com.example.printlypoo.view.fabricante.TelaConsultarImpressora;
    import com.example.printlypoo.view.fabricante.TelaInserirImpressora;
    import com.example.printlypoo.view.pedido.TelaConsultarPedido;
    import com.example.printlypoo.view.pedido.TelaInserirPedido;
    import javafx.application.Application;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.Separator;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;
    import javafx.scene.layout.*;

    public class main extends Application {

        @Override
        public void start(Stage stage) {
            stage.setTitle("Printly - Sistema de Gestão");

            Label lblTitulo = new Label("Printly");
            lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            //---------------------------------------------------------------------------------------//
            // Isabelle - Usuarios
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
            // Gabi - Fabricantes
            Label lblFabricantes = new Label("Gerenciamento de Fabricantes");
            lblFabricantes.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Button btnInserirFabricante = new Button("Cadastrar Fabricante");
            Button btnConsultarFabricante = new Button("Consultar Fabricantes");

            btnInserirFabricante.setMaxWidth(200);
            btnConsultarFabricante.setMaxWidth(200);

            btnInserirFabricante.setOnAction(e -> new TelaInserirFabricante().exibir());
            btnConsultarFabricante.setOnAction(e -> new TelaConsultarFabricante().exibir());

            //---------------------------------------------------------------------------------------//
            // Gabi - Impressoras
            Label lblImpressoras = new Label("Gerenciamento de Impressoras");
            lblImpressoras.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Button btnInserirImpressora = new Button("Cadastrar Impressora");
            Button btnConsultarImpressora = new Button("Consultar Impressoras");

            btnInserirImpressora.setMaxWidth(200);
            btnConsultarImpressora.setMaxWidth(200);

            btnInserirImpressora.setOnAction(e -> new TelaInserirImpressora().exibir());
            btnConsultarImpressora.setOnAction(e -> new TelaConsultarImpressora().exibir());

            //---------------------------------------------------------------------------------------//
            // Paula - Avaliacoes/Notificacoes
//            Label lblNotifAval = new Label("Gerenciamento de Avaliações e Notificações do sistema");
//            lblNotifAval.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
//
//            Button btnConsultarNotif = new Button("Consultar Notificacoes");
//            Button btnConsultarAvaliacoes = new Button("Consultar Avaliacoes");
//
//            btnConsultarNotif.setMaxWidth(200);
//            btnConsultarAvaliacoes.setMaxWidth(200);
//
//            btnConsultarNotif.setOnAction(e -> new NotificacaoVW().show(new Stage()));
//            btnConsultarAvaliacoes.setOnAction(e -> new AvaliacaoVW().show(new Stage()));

            //---------------------------------------------------------------------------------------//
            // Pedro - Pedidos
            Label lblPedidos = new Label("Gerenciamento de Pedidos");
            lblPedidos.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Button btnInserirPedido = new Button("Cadastrar Pedido");
            Button btnConsultarPedido = new Button("Consultar Pedidos");

            btnInserirPedido.setMaxWidth(200);
            btnConsultarPedido.setMaxWidth(200);

            btnInserirPedido.setOnAction(e -> new TelaInserirPedido().exibir());
            btnConsultarPedido.setOnAction(e -> new TelaConsultarPedido().exibir());
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
                    btnConsultarAnuncio,
                    new Separator(),
                    lblFabricantes,
                    btnInserirFabricante,
                    btnConsultarFabricante,
                    new Separator(),
                    lblImpressoras,
                    btnInserirImpressora,
                    btnConsultarImpressora,
                    new Separator(),
                    lblPedidos,
                    btnInserirPedido,
                    btnConsultarPedido
//                    new Separator(),
//                    lblNotifAval,
//                    btnConsultarNotif,
//                    btnConsultarAvaliacoes
            );
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(40));

            stage.setScene(new Scene(layout, 350, 600));
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }