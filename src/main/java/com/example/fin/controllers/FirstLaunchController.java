package com.example.fin.controllers;

import com.example.fin.model.Finans;
import com.example.fin.MainApplication;
import com.example.fin.model.Pillow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.Optional;

public class FirstLaunchController {
    private static final String regexNum = "\\d+[.|,]*\\d*";
    //private static final String regexDate = "^([12][0-9]|3[0-1]|0?[1-9])$";
    @FXML
    private TextField salaryText;
    @FXML
    private TextField percentText;
    @FXML
    private TextField dateText;
    @FXML
    private TextField minSumText;
    @FXML
    private TextField startSumText;
    @FXML
    private Button okBtn;

    @FXML
    private void onOkClick(ActionEvent actionEvent) {
        String message;
        if (!checkFields()) {
            message = "Поля неверно заполнены";
            showEx(message);
            return;
        }
        String salary = salaryText.getText().replaceAll(" ", "");
        String percent = percentText.getText().replaceAll(" ", "");
        String date = dateText.getText().replaceAll(" ", "");
        String minVal = minSumText.getText().replaceAll(" ", "");
        String startSum = startSumText.getText().replaceAll(" ", "");
        if (salary.matches(regexNum) && percent.matches(regexNum) && date.matches(regexNum)) {
            Pillow pillow = new Pillow(Double.parseDouble(startSum), Integer.parseInt(date), Double.parseDouble(percent),
                    Integer.parseInt(minVal), Double.parseDouble(salary));
            pillow.setUpdateDay(validateDate(Integer.parseInt(date)));
            if (!Finans.savePillow(pillow)) {
                showEx("Ошибка сохранения");
                return;
            }

            //Switch to another screen
            try {
                Stage stage = (Stage) okBtn.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (Exception ex) {
                message = "Ошибка открытия приложения";
                showEx(message);
            }

        } else {
            message = "Поля неверно заполнены";
            showEx(message);
        }
    }

    private Calendar validateDate(int date) {
        Calendar today = Calendar.getInstance();
        Calendar payDay = Calendar.getInstance();
        payDay.set(Calendar.DAY_OF_MONTH, date);
        if (today.after(payDay)) {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType yes = new ButtonType("Да");
            ButtonType no = new ButtonType("Нет");
            dialog.getButtonTypes().setAll(yes, no);
            dialog.setTitle("Обновить подушку");
            dialog.setHeaderText("Обновить подушку за этот месяц");
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == yes)
                return payDay;
            else {
                int newMonth = payDay.get(Calendar.MONTH) + 1;
                payDay.set(Calendar.MONTH, newMonth > 11 ? 0 : newMonth);
                return payDay;
            }
        }
        payDay.add(Calendar.MONTH, 1);
        return payDay;
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
