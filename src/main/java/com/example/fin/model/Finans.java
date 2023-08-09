package com.example.fin.model;

import com.example.fin.database.Sqlite;

import java.util.Calendar;
import java.util.Scanner;

public class Finans {
    public static double getSum() {
        Pillow pillow = Sqlite.getPillow();
        return pillow == null ? 0 : pillow.getSum();
    }

    /**
     * Update user's sum
     */
    public static boolean update() {
        Pillow pillow = Sqlite.getPillow();
        if (pillow == null)
            return false;

        if (checkUpdate()) {
            add(pillow);
            Sqlite.updatePillow(pillow);
        }
        return true;
    }

    public static boolean checkUpdate() {
        boolean check = false;
        Pillow pillow = Sqlite.getPillow();
        if (pillow == null)
            return false;
        Calendar currentDate = Calendar.getInstance();
        if (currentDate.after(pillow.getUpdateDay()) ||
                currentDate.get(Calendar.DAY_OF_MONTH) == pillow.getUpdateDay().get(Calendar.DAY_OF_MONTH))
            check = true;
        return check;
    }

    /**
     * Save user's pillow in database
     *
     * @param pillow user's financial pillow
     * @return true if user's pillow added to the database otherwise false
     */
    public static boolean savePillow(Pillow pillow) {
        return Sqlite.addPillow(pillow);
    }

    /**
     * Ask user about update. If "yes" call {@link #update}
     */
    private static void askUpdate() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Остаток не обновлялся в этом месяце, обновить?\nY/N");
            switch (scanner.next().trim()) {
                //case "Y" -> update();
                case "N" -> System.out.println("Остаток: ");
            }
        }
    }

    /**
     * Add user's percent of salary to the sum. If percentage of salary is less than user's
     * minimum value to add then add user's minimum value
     */
    private static void add(Pillow pillow) {
        double sum = pillow.getSum() +
                (pillow.getSalary() * pillow.getPercent() >= pillow.getMinVal() ?
                        pillow.getSalary() * pillow.getPercent() : pillow.getMinVal());
        pillow.setSum(sum);
        Calendar updateDay = pillow.getUpdateDay();
        updateDay.add(Calendar.MONTH, 1);
        updateDay.set(Calendar.DAY_OF_MONTH, pillow.getUpdateDay().get(Calendar.DAY_OF_MONTH));
/*        if (updateDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            updateDay.add(Calendar.DAY_OF_MONTH, -1);
        else if (updateDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            updateDay.add(Calendar.DAY_OF_MONTH, -2);*/
        pillow.setUpdateDay(updateDay);
    }
}
