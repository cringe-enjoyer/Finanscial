package com.example.fin.controllers;

import com.example.fin.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.io.File;
import java.io.FileOutputStream;

public class FirstLaunch {
    private static final String regexNum = "\\d+[.|,]*\\d*";
    private static final String regexDate = "\\d{1,2}";
    private static final String PATH = "/data.txt";
    @FXML
    private TextField salaryText;
    @FXML
    private TextField percentText;
    @FXML
    private TextField dateText;
    @FXML
    private TextField minSumText;
    @FXML
    private Button okBtn;

    @FXML
    private void onOkClick(MouseEvent mouseEvent) {
        String message;
        if (!checkFields()) {
            message = "Поля неверно заполнены";
            showEx(message);
            return;
        }
        String salary = salaryText.getText().replaceAll("\s", "");
        String percent = percentText.getText().replaceAll("\s", "");
        String date = dateText.getText().replaceAll("\s", "");
        String minVal = minSumText.getText().replaceAll("\s", "");

        if (salary.matches(regexNum) && percent.matches(regexNum) && date.matches(regexDate)) {
            saveData(salary, percent, date, minVal);
            //Switch to another screen
            try {
                Stage stage = (Stage) okBtn.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (Exception ex) {
                message = "Ошибка открытия приложения";
                showEx(message);
            }
        }
        else {
            message = "Поля неверно заполнены";
            showEx(message);
        }
    }

    /**
     * Save user's data in txt file
     *
     * @param salary user's salary
     * @param percent percentage of the user's salary
     * @param date user's pay day
     * @param minVal minimum number for a pillow
     */
    private void saveData(String salary, String percent, String date, String minVal) {
        try (FileOutputStream writer = new FileOutputStream(new File(PATH))) {
            writer.write((salary + " ").getBytes());
            writer.write((percent + " ").getBytes());
            writer.write((date + " ").getBytes());
            writer.write(minVal.getBytes());
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    /**
     * Fields validation
     *
     * @return true if field values are correct
     */
    private boolean checkFields() {
        if (salaryText.getText().isEmpty())
            return false;
        if (percentText.getText().isEmpty())
            return false;
        if (dateText.getText().isEmpty())
            return false;
        if (minSumText.getText().isEmpty())
            return false;
        return true;
    }

    /**
     * Show dialog with exception
     */
    private void showEx(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Ошибка");
        dialog.setHeaderText(message);

        dialog.showAndWait();
    }
}
