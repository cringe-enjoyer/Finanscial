package com.example.fin.database;

import com.example.fin.model.Pillow;
import com.example.fin.utils.DateUtils;

import java.sql.*;

public class Sqlite {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:src/main/resources/finanscial.sqlite");
    }

    /**
     * Save user's pillow in database
     *
     * @param pillow user's financial pillow
     * @return true if pillow added to the database otherwise false
     */
    public static boolean addPillow(Pillow pillow) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            statement.executeUpdate("INSERT INTO user_data (salary, sum, percent, update_date, min_value) VALUES" +
                    "(" + pillow.getSalary() + ", " + pillow.getSum() + ", " + pillow.getPercent() + ", '"
                    + DateUtils.dateToString(pillow.getUpdateDay()) + "', " + pillow.getMinVal() + ");");
        } catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
        return true;
    }

    public static Pillow getPillow() {
        Pillow pillow = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            ResultSet result = statement.executeQuery("SELECT * from user_data");
            if (result.next()) {
                pillow = new Pillow(result.getDouble("sum"), result.getString("update_date"),
                        result.getDouble("percent"), result.getInt("min_value"),
                        result.getDouble("salary"));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
            return null;
        }
        return pillow;
    }

    public static void updatePillow(Pillow pillow) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            statement.executeUpdate("UPDATE user_data SET salary = " + pillow.getSalary() + ", sum = "
                    + pillow.getSum() + ", percent = " + pillow.getPercent() + ", update_date = '" +
                    DateUtils.dateToString(pillow.getUpdateDay()) + "', min_value = " + pillow.getMinVal() + ";");
        } catch (Exception exception) {

        }
    }

    public static void checkDB() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT tbl_name FROM sqlite_master WHERE tbl_name LIKE 'user_data';");
            if (resultSet.next()) {
                if (resultSet.getString("tbl_name").equals("user_data"))
                    return;
            }
            createDB();
        } catch (Exception ex) {

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
