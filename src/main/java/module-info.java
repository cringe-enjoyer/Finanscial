module com.example.fin {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.fin to javafx.fxml;
    exports com.example.fin;
}