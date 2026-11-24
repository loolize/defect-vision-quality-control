import java.util.List;
import java.util.Scanner;

public class AutomaticInspection extends BaseInspection implements InspectionMethod{
    private final InspectionImage image;
    private final List<TemplateCell> cells;
    private final CellAnalyzer analyzer;

    public AutomaticInspection(InspectionImage image, List<TemplateCell> cells){
        this.image = image;
        this.cells = List.copyOf(cells);
        this.analyzer = new CellAnalyzer();
    }



    @Override
    public String inspect(Inspection inspection, Scanner scanner){

        List<CellResult> results = analyzer.analyzeCells(
                image.getImage(), cells, inspection.getInspectionId()
        );

        System.out.println("\nРезультаты проверки:");

        
        for (CellResult result : results) {
            inspection.addResult(result);

            System.out.println("Ячейка " + result.getCellId() + " - " + result.getAutomaticState());
        }

        String status = inspection.makePreliminaryDecision();


        System.out.println("\nПустые ячейки: " + inspection.getEmptyCells());

        System.out.println("Неопределенные ячейки: " + inspection.getUncertainCells());

        System.out.println("\nПредварительное заключение: " + status);

        System.out.print("Подтвердить результат? (да/нет): ");


        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("нет")){

            printOptions();

            System.out.print("Выберите правильный результат: ");

            String choice = scanner.nextLine();
            String selected = getStatusByChoice(choice);

            if (selected != null) { status = selected;}


        }

        return status;
        
    }


}
