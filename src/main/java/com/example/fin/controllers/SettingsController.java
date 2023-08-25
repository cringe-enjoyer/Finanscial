package com.example.fin.controllers;

import com.example.fin.MainApplication;
import com.example.fin.database.Sqlite;
import com.example.fin.model.Cushion;
import com.example.fin.model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private TextField salaryText;
    @FXML
    private TextField percentText;
    @FXML
    private TextField dateText;
    @FXML
    private TextField minSumText;
    @FXML
    private TextField cushionSumText;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    public void setSalaryText(String salaryText) {
        this.salaryText.setText(salaryText);
    }

    public void setPercentText(String percentText) {
        this.percentText.setText(percentText);
    }

    public void setDateText(String dateText) {
        this.dateText.setText(dateText);
    }

    public void setMinSumText(String minSumText) {
        this.minSumText.setText(minSumText);
    }

    public void setCushionSumText(String cushionSumText) {
        this.cushionSumText.setText(cushionSumText);
    }

    @FXML
    protected void onSaveClick(ActionEvent actionEvent) {
        Cushion cushion = new Cushion(Double.parseDouble(cushionSumText.getText()), Integer.parseInt(dateText.getText()));
        UserData userData = new UserData(cushion, Double.parseDouble(percentText.getText()),
                Integer.parseInt(minSumText.getText()), Double.parseDouble(salaryText.getText()));
        Sqlite.updateUserData(userData);
        try {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @FXML
    protected void onCancelClick(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
