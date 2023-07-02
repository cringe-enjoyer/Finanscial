package com.example.fin.controllers;

import com.example.fin.MainApplication;
import com.example.fin.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FirstLaunchController {
    private static final String regexNum = "\\d+[.|,]*\\d*";
    private static final String regexDate = "[1-31]{1,2}";
    @FXML
    private TextField salaryText;
    @FXML
    private TextField percentText;
    @FXML
    private TextField dateText;
    @FXML
    private TextField minSumText;
    @FXML
    TextField startSumText;
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

        if (salary.matches(regexNum) && percent.matches(regexNum) && date.matches(regexDate)) {
            saveData(salary, percent, date, minVal, startSum);
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

    //TODO: добавить сохранение зарплаты
    /**
     * Save user's data in txt file
     *
     * @param salary user's salary
     * @param percent percentage of the user's salary
     * @param date user's pay day
     * @param minVal minimum number for a pillow
     */
    private void saveData(String salary, String percent, String date, String minVal, String startSum) {
        try (FileOutputStream writer = new FileOutputStream(new File(FileUtils.PATH))) {
            if (!startSum.isEmpty())
                writer.write((startSum + " ").getBytes());
            else
                writer.write("0 ".getBytes());
            date = validateDate(date);
            writer.write((date + " ").getBytes());
            writer.write((percent + " ").getBytes());
            writer.write(minVal.getBytes());
            //writer.write((salary + " ").getBytes());
        } catch (Exception ex) {
            System.out.println("saveData " + ex);
            new ExceptionDialog(ex);
        }
    }

    private String validateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar today = Calendar.getInstance();
        Calendar payDay = Calendar.getInstance();
        payDay.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date));
        if (today.before(payDay))
            return sdf.format(payDay.getTime());
        // TODO: Может убрать это и оставить только для текущего месяца
        payDay.add(Calendar.MONTH, 1);
        return sdf.format(payDay);
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
