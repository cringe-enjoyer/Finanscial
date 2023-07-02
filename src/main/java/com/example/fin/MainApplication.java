package com.example.fin;

import com.example.fin.utils.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        if (checkFile()) {
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

    private boolean checkFile() {
        File file = new File(FileUtils.PATH);
        return !file.exists() || FileUtils.isEmpty(file);
    }

    public static void main(String[] args) {
        launch();
    }
}