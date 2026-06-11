module com.example.printlypoo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens com.example.printlypoo to javafx.fxml;
    exports com.example.printlypoo;
    exports com.example.printlypoo.view.fabricante;
    opens com.example.printlypoo.view.fabricante to javafx.graphics;
    opens com.example.printlypoo.model.fabricante to javafx.base, javafx.graphics;
}