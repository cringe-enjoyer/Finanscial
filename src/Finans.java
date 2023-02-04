import java.io.*;
import java.util.Calendar;
import java.util.Scanner;

public class Finans {
    private static double sum;
    private static boolean isUpdate = false;
    private static Calendar currentDate = Calendar.getInstance();
    private static byte updateDay = 25;
    private static String path = "F:\\Документы\\Finans\\fin.txt";
    //private static int salary = 5000;

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
                rewrite();
                return;
            }
            var tmp = str.toString().split(" ");
            sum = Double.parseDouble(tmp[0]);
            isUpdate = tmp[1].equals("1");
            if (currentDate.get(Calendar.DAY_OF_MONTH) >= updateDay && !isUpdate) {
                add();
                isUpdate = true;
                rewrite();
            }
            if (!isUpdate) {
                add();
                isUpdate = true;
                rewrite();
            }
            System.out.println("Остаток: " + sum);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
            throw new RuntimeException(e);
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
            var tmp = str.toString().split(" ");
            sum = Double.parseDouble(tmp[0]);
            isUpdate = tmp[1].equals("1");
            if (isUpdate)
                System.out.println("Остаток: " + sum);
            else {
                try (Scanner scanner = new Scanner(System.in)) {
                    System.out.println("Остаток не обновлялся в этом месяце, обновить?\nY/N");
                    switch (scanner.next().trim()) {
                        case "Y" -> update();
                        case "N" -> System.out.println("Остаток: " + sum);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void rewrite() {
        clear();
        try (FileWriter writer = new FileWriter(path, false)) {
            // Записать новую sum в файл и через пробел добавить 0 (если сумма не обновлялась в этом месяце)
            // или 1 (если сумма обновлялась)
            writer.write(Double.toString(sum));
            writer.write(' ');
            writer.write(isUpdate ? '1' : '0');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void add() {
        try (Scanner sc = new Scanner(System.in)){
            System.out.println("Введите зп за месяц");
            double salary = Double.parseDouble(sc.nextLine().trim());
            sum += salary * 0.1 >= 1000 ? salary * 0.1 : 1000;
            isUpdate = true;
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
