import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Finans {
    private static double sum;
    private static Calendar currentDate = Calendar.getInstance();
    private static Calendar updateDay;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private static String path = "F:\\Документы\\Finans\\fin.txt";

    static void update() {
        try (FileReader reader = new FileReader(path)) {
            int i;
            StringBuilder str = new StringBuilder();
            while ((i = reader.read()) != -1)
                str.append((char) i);
            if (str.isEmpty()) {
                Scanner sr = new Scanner(System.in);
                System.out.println("Введите остаток");
                sum = Double.parseDouble(sr.nextLine());
                sr.close();
                updateDay = currentDate;
                updateDay.add(Calendar.DAY_OF_MONTH, -1);
                rewrite();
                System.out.println("Остаток: " + sum);
                return;
            }
            String[] tmp = str.toString().split(" ");
            sum = Double.parseDouble(tmp[0]);
            updateDay = parseDate(tmp[1].trim());
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

    static void show() {
        try (FileReader reader = new FileReader(path)) {
            int i;
            StringBuilder str = new StringBuilder();
            while ((i = reader.read()) != -1)
                str.append((char) i);
            if (str.isEmpty())
                askUpdate();
            else {
                var tmp = str.toString().split(" ");
                sum = Double.parseDouble(tmp[0]);
                updateDay = Calendar.getInstance();
                updateDay.setTime(sdf.parse(tmp[1]));
                if (currentDate.before(updateDay))
                    System.out.println("Остаток: " + sum);
                else
                    askUpdate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void askUpdate() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Остаток не обновлялся в этом месяце, обновить?\nY/N");
            switch (scanner.next().trim()) {
                case "Y" -> update();
                case "N" -> System.out.println("Остаток: " + sum);
            }
        }
    }

    private static Calendar parseDate(String strDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(strDate));
        return calendar;
    }

    private static void rewrite() {
        clear();
        try (FileWriter writer = new FileWriter(path, false)) {
            // Записать новую sum в файл и через пробел добавить 0 (если сумма не обновлялась в этом месяце)
            // или 1 (если сумма обновлялась) и дату следующего обновления
            writer.write(Double.toString(sum));
            writer.write(' ');
            writer.write(sdf.format(updateDay.getTime()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void add() {
        try (Scanner sc = new Scanner(System.in)){
            System.out.println("Введите зп за месяц");
            double salary = Double.parseDouble(sc.nextLine().trim());
            sum += salary * 0.1 >= 1000 ? salary * 0.1 : 1000;
            updateDay.add(Calendar.MONTH, 1);
            updateDay.set(Calendar.DAY_OF_MONTH, 25);
            if (updateDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                updateDay.add(Calendar.DAY_OF_MONTH, -1);
            else if (updateDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                updateDay.add(Calendar.DAY_OF_MONTH, -2);
        }
        catch (NumberFormatException e) {
            System.out.println(e + "\nВведено неверное значение");
        }
    }

    private static void clear() {
        try {
            new FileWriter(path, false).close(); //Очищает файл
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
    }
}
