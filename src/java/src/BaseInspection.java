import java.util.Scanner;

public abstract class BaseInspection implements InspectionMethod{

    private final StatusMenu menu = new StatusMenu();

    protected void printOptions(){
        menu.printOptions();
    }

    protected InspectionStatus getStatusByChoice(String choice) {
        return menu.getStatusByChoice(choice);


    }

    @Override
    public abstract InspectionStatus inspect(Inspection inspection, Scanner scanner);


}


