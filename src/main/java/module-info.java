module com.example.printlypoo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens com.example.printlypoo to javafx.fxml;
    exports com.example.printlypoo;
}