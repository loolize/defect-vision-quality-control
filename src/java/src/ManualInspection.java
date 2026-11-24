import java.util.Scanner;

public class ManualInspection extends BaseInspection implements InspectionMethod{
    @Override
    public String inspect(Inspection inspection, Scanner scanner) {

        inspection.setAutomaticStatus("Изображение непригодно");

        System.out.println("\nАвтоматическая проверка не выполнена");

        System.out.println("Осмотрите блистер вручную");

        printOptions();

        while (true){

            System.out.print("Введите результат проверки: ");

            String choice = scanner.nextLine();
            String status = getStatusByChoice(choice);

            if (status != null) {
                return status;
            }


            System.out.println("Введите число от 1 до 3");
        }

        
    }




}
