import java.util.Scanner;

public abstract class BaseInspection implements InspectionMethod{

    private final StatusMenu menu = new StatusMenu();

    protected void printOptions(){
        menu.printOptions();
    }

    protected String getStatusByChoice(String choice) {
        return menu.getStatusByChoice(choice);


    }

    @Override
    public abstract String inspect(Inspection inspection, Scanner scanner);


}


