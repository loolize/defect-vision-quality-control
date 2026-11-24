import java.util.Scanner;

public class ManualInspection extends BaseInspection implements InspectionMethod{
    @Override
    public InspectionStatus inspect(Inspection inspection, Scanner scanner) {

        inspection.setAutomaticStatus(InspectionStatus.UNSUITABLE_IMAGE);

        System.out.println("\nАвтоматическая проверка не выполнена");

        System.out.println("Осмотрите блистер вручную");

        printOptions();

        while (true){

            System.out.print("Введите результат проверки: ");

            String choice = scanner.nextLine();
            InspectionStatus status = getStatusByChoice(choice);

            if (status != null) {
                return status;
            }


            System.out.println("Введите число от 1 до 3");
        }

        
    }




}
