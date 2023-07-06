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
            Finans.update();
/*            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Введите зарплату");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(salary -> Finans.updateSalary(Double.parseDouble(salary)));*/
        }
        pillowResultText.setText(String.valueOf(Finans.getSum()));
    }
}