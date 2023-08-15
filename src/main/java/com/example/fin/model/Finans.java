package com.example.fin.model;

import com.example.fin.database.Sqlite;

import java.util.Calendar;

public class Finans {
    public static UserData getUserData() {
        UserData userData = Sqlite.getUserData();
        return userData;
    }

    /**
     * Update user data
     */
    public static boolean update() {
        UserData userData = Sqlite.getUserData();
        if (userData == null)
            return false;

        //Increase sum for all needed months
        while (checkUpdate()) {
            add(userData);
            Sqlite.updateUserData(userData);
        }
        return true;
    }

    public static boolean checkUpdate() {
        boolean check = false;
        UserData userData = Sqlite.getUserData();
        if (userData == null)
            return false;
        Cushion cushion = userData.getCushion();
        Calendar currentDate = Calendar.getInstance();
        if (currentDate.after(cushion.getUpdateDate()) ||
                currentDate.get(Calendar.DAY_OF_MONTH) == cushion.getUpdateDate().get(Calendar.DAY_OF_MONTH))
            check = true;
        return check;
    }

    /**
     * Save user data in database
     *
     * @param userData user data
     * @return true if user data added to the database otherwise false
     */
    public static boolean saveUserData(UserData userData) {
        return Sqlite.addUserData(userData);
    }

    /**
     * Add user's percent of salary to the sum. If percentage of salary is less than user's
     * minimum value to add then add user's minimum value
     */
    private static void add(UserData userData) {
        Cushion cushion = userData.getCushion();
        double sum = cushion.getSum() +
                (userData.getSalary() * userData.getPercent() >= userData.getMinVal() ?
                        userData.getSalary() * userData.getPercent() : userData.getMinVal());
        cushion.setSum(sum);
        Calendar updateDay = cushion.getUpdateDate();
        updateDay.add(Calendar.MONTH, 1);
        updateDay.set(Calendar.DAY_OF_MONTH, cushion.getUpdateDate().get(Calendar.DAY_OF_MONTH));
/*        if (updateDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            updateDay.add(Calendar.DAY_OF_MONTH, -1);
        else if (updateDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            updateDay.add(Calendar.DAY_OF_MONTH, -2);*/
        cushion.setUpdateDate(updateDay);
        userData.setCushion(cushion);
    }
}