import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;



public class Main {

    public static void main(String[] args){
        OpenCV.loadLocally();

        try (Scanner scanner = new Scanner(System.in)){new Main().run(scanner);

        } catch (IOException e){
            System.out.println("Ошибка работы с файлами: " + e.getMessage());
        }

    }

    public void run(Scanner scanner) throws IOException{

        JsonStorage storage = new JsonStorage();

        storage.loadTemplate("src/template.json");

        Series series = new Series(1, "TEST-001");

        Blister blister = new Blister(1, series.getSeriesId(), storage.getTemplate().getTemplateId()
        );

        System.out.print("Путь к изображению (enter(шаблон)): ");

        String path = scanner.nextLine();

        if (path.isEmpty()) {path = "src/images/blister.png";}

        InspectionImage image = new InspectionImage(1, blister.getBlisterId(), path);



        boolean suitable = getImageWithRetries(image, scanner);

        List<TemplateCell> cells = storage.getCells();

        if (suitable){
            System.out.println("\nКачество изображения проверено");

            if (!validateCells(image, cells)){return;}

            System.out.println("Изображение загружено");
            System.out.println("Количество ячеек: " + cells.size());
        
        }



        System.out.print("\nВведите имя оператора: ");

        Operator operator = new Operator(1, scanner.nextLine());

        Inspection inspection = new Inspection(1, image.getImageId(), operator.getOperatorId(), currentTime());

        InspectionMethod method;

        if (suitable) {
            method = new AutomaticInspection(image, cells);
        } else {
            method = new ManualInspection();
        }

        String finalStatus = method.inspect(inspection, scanner);

        SpecialistReview specialist = new SpecialistReview();

        finalStatus = specialist.checkReview(finalStatus, scanner);
        inspection.confirm(finalStatus, currentTime());

        prepareImageForSaving(image, scanner);

        storage.saveInspection(
                "src/inspection_java.json", series,
                blister, image,
                operator, inspection
        );

        System.out.println("\nИтоговое заключение: " + finalStatus);

        System.out.println("Результат сохранен в inspection_java.json");
    }

    private boolean getImageWithRetries(InspectionImage image, Scanner scanner) {
        for (int attempt = 1; attempt <= 3; attempt++) {


            System.out.println("\nПопытка " + attempt);

            if (image.load() && image.checkQuality()) {
                System.out.println("Изображение пригодно для проверки");

                return true;

            }

            System.out.println("Изображение не прошло проверку качества");

            if (attempt < 3){
                System.out.print("Введите путь к новому изображению: ");
                image.setFilePath(scanner.nextLine());
            }
        }

        return false;
    }

    private boolean validateCells(InspectionImage image, List<TemplateCell> cells) {

        for (TemplateCell cell : cells){

            if (!cell.isValid(image.getImage().cols(), image.getImage().rows())) {

                System.out.println("Некорректные координаты ячейки");
                return false;
            }

        }

        return true;

    }

    private void prepareImageForSaving(InspectionImage image, Scanner scanner) {

        String path = image.getFilePath();

        if (path == null || path.isEmpty() || !Files.isRegularFile(Path.of(path))) {
            image.setFilePath(null);
            return;
        }

        System.out.print("Дата съемки (ГГГГ-ММ-ДД ЧЧ:ММ, Enter - неизвестно): ");

        String dateText = scanner.nextLine().trim();

        if (!dateText.isEmpty()) {
            try {

                LocalDateTime date = LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm").withResolverStyle(
                                java.time.format.ResolverStyle.STRICT
                        )
                );

                image.setCapturedAt(date.format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm")));

            } catch (DateTimeParseException e) {

                System.out.println("Неверный формат даты");
            }

        }

        Mat original = Imgcodecs.imread(path);

        String savedPath ="src/images/inspection_image_java.png";

        if (!original.empty() && Imgcodecs.imwrite(savedPath, original)) {
            image.setFilePath(savedPath);

        } else {
            image.setFilePath(null);
        }

        original.release();

    }

    private String currentTime(){

        return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss"));
    }




}

