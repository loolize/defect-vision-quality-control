import org.opencv.core.Mat;
import java.util.ArrayList;
import java.util.List;

public class CellAnalyzer{
    private final int pixelThreshold = 150;
    private final double emptyThreshold = 0.06;
    private final double filledThreshold = 0.18;


    public CellState analyzeCell(Mat image, TemplateCell cell) {

        int darkPixelCount = 0;
        int pixelCount = cell.getWidth()*cell.getHeight();

        for (int y = cell.getY(); y < cell.getY() + cell.getHeight(); y++){

            for (int x = cell.getX(); x < cell.getX() + cell.getWidth(); x++) {

                double pixel = image.get(y, x)[0];

                if (pixel < pixelThreshold){darkPixelCount++;}
            }
            
        }

        double ratio = (double) darkPixelCount / pixelCount;



        if (ratio < emptyThreshold) {return CellState.EMPTY;
        } else if (ratio >= filledThreshold){
            
            return CellState.FILLED;
        } else {
            return CellState.UNCERTAIN;
        }
    }


    public List<CellResult> analyzeCells(Mat image, List<TemplateCell> cells, int inspectionId){
        List<CellResult> results = new ArrayList<>();

        for (TemplateCell cell:cells){

            CellState state = analyzeCell(image, cell);

            results.add(new CellResult(inspectionId, cell.getCellId(), state));
        }

        return results;
    }



}


