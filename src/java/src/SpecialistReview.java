import java.util.Scanner;

public class SpecialistReview extends BaseInspection {

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

        printOptions();

        while (true){

            System.out.print("Решение специалиста: ");

            String choice = scanner.nextLine().trim();
            String result = getStatusByChoice(choice);

            if (result != null) {
                return result;
            }

            System.out.println("Введите число от 1 до 3");
        }
    }




    @Override
    public String inspect(Inspection inspection, Scanner scanner){
        return checkReview(inspection.getFinalStatus(), scanner);
    }



}
