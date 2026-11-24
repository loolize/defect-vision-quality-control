public class StatusMenu {
    public void printOptions(){
        System.out.println("1. Полный");
        System.out.println("2. Обнаружена пустая ячейка");
        System.out.println("3. Требуется повторная проверка");
    }

    public String getStatusByChoice(String choice) {
        switch (choice) {
            case "1":
                return "Полный";

            case "2":
                return "Обнаружена пустая ячейка";

            case "3":
                return "Требуется повторная проверка";

            default:
                return null;
        }
    }



}