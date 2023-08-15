package com.example.fin.database;

import com.example.fin.model.Cushion;
import com.example.fin.model.UserData;
import com.example.fin.utils.DateUtils;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sqlite {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:src/main/resources/finanscial.sqlite");
    }

    /**
     * Save user data in database
     *
     * @param userData user data
     * @return true if pillow added to the database otherwise false
     */
    public static boolean addUserData(UserData userData) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            statement.executeUpdate("INSERT INTO user_data (salary, sum, percent, update_date, min_value) VALUES" +
                    "(" + userData.getSalary() + ", " + userData.getCushion().getSum() + ", " + userData.getPercent() + ", '"
                    + DateUtils.dateToString(userData.getCushion().getUpdateDate()) + "', " + userData.getMinVal() + ");");
        } catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
        return true;
    }

    public static UserData getUserData() {
        UserData userData = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            ResultSet result = statement.executeQuery("SELECT * from user_data");
            if (result.next()) {
                Cushion cushion = new Cushion(result.getDouble("sum"),
                        DateUtils.stringToDate(result.getString("update_date")));
                userData = new UserData(cushion, result.getDouble("percent"),
                        result.getInt("min_value"), result.getDouble("salary"));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
            return null;
        }
        return userData;
    }

    public static void updateUserData(UserData userData) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            statement.executeUpdate("UPDATE user_data SET salary = " + userData.getSalary() + ", sum = "
                    + userData.getCushion().getSum() + ", percent = " + userData.getPercent() + ", update_date = '" +
                    DateUtils.dateToString(userData.getCushion().getUpdateDate()) + "', min_value = " + userData.getMinVal() + ";");
        } catch (Exception exception) {
            //throw new Exception();
        }
    }

    public static void checkDB() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT tbl_name FROM sqlite_master WHERE tbl_name " + "LIKE 'user_data'");
            StringBuilder checkResult = new StringBuilder();
            while (resultSet.next()) {
                checkResult.append(resultSet.getString("tbl_name")).append(" ");
/*                if (resultSet.getString("tbl_name").equals("user_data"))
                    return;*/
            }
            createDB();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void createDB() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeQuery("create table user_data ( " +
                    "    id          INTEGER" +
                    "        constraint user_data_pk" +
                    "            primary key autoincrement," +
                    "    salary      REAL," +
                    "    sum         REAL," +
                    "    percent     REAL," +
                    "    update_date TEXT," +
                    "    min_value   INTEGER);");
        } catch (Exception exception) {

        }
    }
}
