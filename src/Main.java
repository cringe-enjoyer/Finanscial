import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Выберите операцию\n1 - Показать остаток\n2 - Обновить остаток\n0 - Выход");
            byte option = Byte.parseByte(scanner.next().trim());
            switch (option) {
                case 1 -> Finans.show();
                case 2 -> {
                    Finans.update();
                }
                default -> {
                    scanner.close();
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
