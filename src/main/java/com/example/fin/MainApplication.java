package com.example.fin;

import com.example.fin.database.Sqlite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        if (checkPillow()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("firstLaunch-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Financial pillow");
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Financial pillow");
            stage.setScene(scene);
            stage.show();
        }
    }

    private boolean checkPillow() {
        Sqlite.checkDB();
        return Sqlite.getPillow() == null;
    }

    public static void main(String[] args) {
        launch();
    }
}