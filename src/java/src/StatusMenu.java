public class StatusMenu {
    public void printOptions(){
        System.out.println("1. " + InspectionStatus.FULL);
        System.out.println("2. " + InspectionStatus.EMPTY_CELL);
        System.out.println("3. " + InspectionStatus.RECHECK);
    }

    public InspectionStatus getStatusByChoice(String choice) {
        switch (choice) {
            case "1":
                return InspectionStatus.FULL;

            case "2":
                return InspectionStatus.EMPTY_CELL;

            case "3":
                return InspectionStatus.RECHECK;

            default:
                return null;
        }
    }



}