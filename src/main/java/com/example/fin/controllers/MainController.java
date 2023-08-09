package com.example.fin.controllers;

import com.example.fin.model.Finans;
import com.example.fin.MainApplication;
import com.example.fin.model.Pillow;
import com.example.fin.database.Sqlite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Calendar;

public class MainController {
    @FXML
    private Button settingsBtn;
    @FXML
    private Button reloadBtn;
    @FXML
    private Label pillowResultText;

    //TODO: добавить возможность изменить настройки
    @FXML
    protected void onSettingsBtnClick() {
        //Switch to another screen
        try {
            Stage stage = (Stage) settingsBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("settings-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            SettingsController controller = fxmlLoader.getController();
            Pillow pillow = Sqlite.getPillow();
            controller.setPercentText(String.valueOf(pillow.getPercent() * 100));
            controller.setMinSumText(String.valueOf(pillow.getMinVal()));
            controller.setSalaryText(String.valueOf(pillow.getSalary()));
            controller.setPillowSumText(String.valueOf(pillow.getSum()));
            controller.setDateText(String.valueOf(pillow.getUpdateDay().get(Calendar.DAY_OF_MONTH)));
        } catch (Exception ex) {
                /*message = "Ошибка открытия приложения";
                showEx(message);*/
        }
    }

    @FXML
    protected void onReloadButtonClick() {
        if (Finans.checkUpdate())
            Finans.update();

        pillowResultText.setText(String.valueOf(Finans.getSum()));
    }
}