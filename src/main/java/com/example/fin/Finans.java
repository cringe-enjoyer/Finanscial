package com.example.fin;

import com.example.fin.utils.FileUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Finans {
    private static double sum;
    private static Calendar currentDate = Calendar.getInstance();
    /**
     * Date of next salary
     */
    private static Calendar updateDay;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    //private static final String path = "/data.txt"; //"F:\\Документы\\Finans\\fin.txt";
    // TODO: 27.02.2023 Пересмотреть вывод информации, может что-то изменить или улучшить?

    private static double percent;
    private static int minVal;
    private static double salary;

    public static double getSum() {
        return sum;
    }

    /**
     * Ask user about update his sum and update it
     */
    public static void update(double userSalary) {
        try (FileReader reader = new FileReader(FileUtils.PATH)) {
            int i;
            StringBuilder str = new StringBuilder();
            while ((i = reader.read()) != -1)
                str.append((char) i);
            String[] tmp = str.toString().split(" ");
            sum = Double.parseDouble(tmp[0]);
            updateDay = parseDate(tmp[1].trim());
            percent = Double.parseDouble(tmp[2]);
            minVal = Integer.parseInt(tmp[3]);
            salary = userSalary;
            //salary = Double.parseDouble(tmp[4]);
            if (currentDate.after(updateDay)) {
                add();
                rewrite();
            }
            System.out.println("Остаток: " + sum);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
            throw new RuntimeException(e);
        } catch (ParseException parseException) {
            System.out.println("Date parse fail " + parseException);
            throw new RuntimeException(parseException);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            System.out.println("Введено неверное значение");
            throw new RuntimeException(e);
        }
    }

    public static boolean checkUpdate() {
        boolean check = false;
        try (FileReader reader = new FileReader(FileUtils.PATH)) {
            int i;
            StringBuilder str = new StringBuilder();
            while ((i = reader.read()) != -1)
                str.append((char) i);
            updateDay = parseDate(str.toString().split(" ")[1]);
            if (currentDate.after(updateDay))
                check = true;
        } catch (Exception ex) {
            System.out.println("checkUpdate " + ex.getMessage());
        }
        return check;
    }

    /**
     * Read sum from file
     */
    public static void read() {
        try (FileReader reader = new FileReader(FileUtils.PATH)) {
            int i;
            StringBuilder str = new StringBuilder();
            while ((i = reader.read()) != -1)
                str.append((char) i);
            String[] tmp = str.toString().split(" ");
            sum = Double.parseDouble(tmp[0]);
            updateDay = Calendar.getInstance();
            updateDay.setTime(sdf.parse(tmp[1]));
        } catch (Exception e) {
            System.out.println("read " + e);
        }
    }

    /**
     * Ask user about update. If "yes" call {@link #update}
     */
    private static void askUpdate() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Остаток не обновлялся в этом месяце, обновить?\nY/N");
            switch (scanner.next().trim()) {
                //case "Y" -> update();
                case "N" -> System.out.println("Остаток: " + sum);
            }
        }
    }

    private static Calendar parseDate(String strDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(strDate));
        return calendar;
    }

    /**
     * Clear file then write new sum, percent and minimum value to add in file and date of new update separated by space. Date format: dd.MM.yyyy
     */
    private static void rewrite() {
        clear();
        try (FileWriter writer = new FileWriter(FileUtils.PATH, false)) {
            writer.write(Double.toString(sum));
            writer.write(' ');
            writer.write(sdf.format(updateDay.getTime()));
            writer.write(' ');
            writer.write(Double.toString(percent));
            writer.write(' ');
            writer.write(String.valueOf(minVal));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: Протестировать добавление
    /**
     * Add user's {@link #percent} of salary to the sum. If percentage of salary is less than user's
     * {@link #minVal minimum value} to add then add user's minimum value
     */
    private static void add() {
        //try (Scanner sc = new Scanner(System.in)){
            //System.out.println("Введите зп за месяц");
            //double salary = Double.parseDouble(sc.nextLine().trim());
            sum += salary * percent >= minVal ? salary * percent : minVal;
            updateDay.add(Calendar.MONTH, 1);
            updateDay.set(Calendar.DAY_OF_MONTH, 25);
            if (updateDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                updateDay.add(Calendar.DAY_OF_MONTH, -1);
            else if (updateDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                updateDay.add(Calendar.DAY_OF_MONTH, -2);
        //}
/*        catch (NumberFormatException e) {
            System.out.println(e + "\nВведено неверное значение");
        }*/
    }

    /**
     * Clear save file
     */
    private static void clear() {
        try {
            new FileWriter(FileUtils.PATH, false).close(); //Очищает файл
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
    }
}
