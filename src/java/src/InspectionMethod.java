import java.util.Scanner;

public interface InspectionMethod{
    InspectionStatus inspect(Inspection inspection, Scanner scanner);
}
