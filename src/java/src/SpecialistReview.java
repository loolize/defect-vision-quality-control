import java.util.Scanner;

public class SpecialistReview {

    private final StatusMenu menu = new StatusMenu();

    public String checkReview(String status, Scanner scanner) {
        System.out.print("\nТребуется разбор специалистом? (да/нет): ");

        String answer = scanner.nextLine().trim();

        if (answer.equalsIgnoreCase("да")) {
            return review(status, scanner);
        }

        return status;

    }

    private String review(String status, Scanner scanner) {

        System.out.println("\nРазбор спорного случая");
        System.out.println("Текущий результат: " + status);
        System.out.print("Введите имя специалиста: ");
        scanner.nextLine();

        menu.printOptions();

        while (true){

            System.out.print("Решение специалиста: ");

            String choice = scanner.nextLine().trim();
            String result = menu.getStatusByChoice(choice);

            if (result != null) {
                return result;
            }

            System.out.println("Введите число от 1 до 3");
        }
    }





}
