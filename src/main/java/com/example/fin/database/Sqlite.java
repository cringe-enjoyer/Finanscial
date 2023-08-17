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
            ResultSet resultSet = statement.executeQuery("SELECT tbl_name FROM sqlite_master WHERE tbl_name " +
                    "LIKE 'user_data' OR tbl_name LIKE 'old_cushions';");
            StringBuilder checkResult = new StringBuilder();
            while (resultSet.next()) {
                checkResult.append(resultSet.getString("tbl_name")).append(" ");
/*                if (resultSet.getString("tbl_name").equals("user_data"))
                    return;*/
            }
            if (checkResult.toString().trim().equals("user_data old_cushions"))
                return;
            createDB();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static boolean addOldCushion(Cushion cushion) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            statement.executeUpdate("INSERT INTO old_cushions (sum, date) VALUES" +
                    "(" + cushion.getSum() + ", '" + DateUtils.dateToString(cushion.getUpdateDate()) + "' );");
        } catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
        return true;
    }

    public static List<Cushion> getOldCushions() {
        List<Cushion> cushions = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM old_cushions");
            while (result.next()) {
                cushions.add(new Cushion(result.getDouble("sum"), DateUtils.stringToDate(result.getString("date"))));
            }
            return cushions;
        } catch (SQLException e) {
            return null;
            //throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Double> getOldCushionsAsMap() {
        Map<String, Double> oldPillows = new HashMap<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM old_cushions");
            while (result.next()) {
                oldPillows.put(result.getString("date"), result.getDouble("sum"));
            }
            return oldPillows;
        } catch (SQLException e) {
            return null;
            //throw new RuntimeException(e);
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
                    "    min_value   INTEGER);" +
                    "create table old_cushions ( " +
                    "    id   integer        not null" +
                    "        constraint old_cushions_pk" +
                    "            primary key autoincrement," +
                    "    sum  REAL default 0 not null," +
                    "    date TEXT" +
                    ");");
        } catch (Exception exception) {

        }
    }
}
