module com.example.fin {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.fin to javafx.fxml;
    exports com.example.fin;
    exports com.example.fin.controllers;
    opens com.example.fin.controllers to javafx.fxml;
}