package com.example.fin.controllers;

import com.example.fin.Finans;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class MainController {
    @FXML
    private Button reloadBtn;
    @FXML
    private Label pillowResultText;

    //TODO: добавить возможность изменить настройки
    @FXML
    protected void onReloadButtonClick() {
        if (Finans.checkUpdate()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Введите зарплату");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(salary -> Finans.update(Double.parseDouble(salary)));
        }
        Finans.read();
        pillowResultText.setText(String.valueOf(Finans.getSum()));
    }
}