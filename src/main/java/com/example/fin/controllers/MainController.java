package com.example.fin.controllers;

import com.example.fin.model.Cushion;
import com.example.fin.model.Finans;
import com.example.fin.MainApplication;
import com.example.fin.database.Sqlite;
import com.example.fin.model.UserData;
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
    private Label cushionResultText;

    @FXML
    protected void onSettingsBtnClick() {
        //Switch to another screen
        try {
            Stage stage = (Stage) settingsBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("settings-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            SettingsController controller = fxmlLoader.getController();
            UserData userData = Sqlite.getUserData();
            Cushion cushion = userData.getCushion();
            controller.setPercentText(String.valueOf(userData.getPercent() * 100));
            controller.setMinSumText(String.valueOf(userData.getMinVal()));
            controller.setSalaryText(String.valueOf(userData.getSalary()));
            controller.setPillowSumText(String.valueOf(cushion.getSum()));
            controller.setDateText(String.valueOf(cushion.getUpdateDate().get(Calendar.DAY_OF_MONTH)));
        } catch (Exception ex) {
                /*message = "Ошибка открытия приложения";
                showEx(message);*/
        }
    }

    @FXML
    protected void onReloadButtonClick() {
        if (Finans.checkUpdate())
            Finans.update();

        Cushion cushion = Finans.getUserData().getCushion();
        cushionResultText.setText(String.valueOf(cushion.getSum()));
    }
}