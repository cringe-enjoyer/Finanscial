package com.example.fin.controllers;

import com.example.fin.MainApplication;
import com.example.fin.Pillow;
import com.example.fin.database.Sqlite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        if (salary.matches(regexNum) && percent.matches(regexNum) && date.matches(regexNum)) {
            if (!saveData(salary, percent, Integer.parseInt(date), minVal, startSum)) {
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

    /**
     * Save user's data in database
     *
     * @param salary   user's salary
     * @param percent  percentage of the user's salary
     * @param date     user's pay day
     * @param minVal   minimum number for a pillow
     * @param startSum start pillow's sum
     * @return true if user's data saved in database otherwise false
     */
    private boolean saveData(String salary, String percent, int date, String minVal, String startSum) {
        Pillow pillow = new Pillow(Double.parseDouble(startSum), date, Double.parseDouble(percent) / 100,
                Integer.parseInt(minVal), Double.parseDouble(salary));
        return Sqlite.addPillow(pillow);
        /*try (FileOutputStream writer = new FileOutputStream(new File(FileUtils.PATH))) {
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
        }*/
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
