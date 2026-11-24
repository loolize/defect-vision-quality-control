import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Inspection{
    @SerializedName("inspection_id")
    private final int inspectionId;

    @SerializedName("image_id")
    private final int imageId;

    @SerializedName("operator_id")
    private final int operatorId;

    @SerializedName("created_at")
    private final String createdAt;

    @SerializedName("automatic_status")
    private String automaticStatus;

    @SerializedName("final_status")
    private String finalStatus;

    @SerializedName("confirmed_at")
    private String confirmedAt;


    private transient List<CellResult> results;



    public Inspection(int inspectionId, int imageId, int operatorId, String createdAt) {
        this.inspectionId = inspectionId;
        this.imageId = imageId;
        this.operatorId = operatorId;
        this.createdAt = createdAt;
        this.results = new ArrayList<>();
    }

    public void addResult(CellResult result){
        results.add(result);
    }

    public List<CellResult> getResults(){
        return List.copyOf(results);
    }

    public List<Integer> getEmptyCells() {
        List<Integer> emptyCells = new ArrayList<>();


        for (CellResult result : results){
            if (result.isEmpty()){
                emptyCells.add(result.getCellId());
            }


        }


        return emptyCells;
    }

    public List<Integer> getUncertainCells(){
        List<Integer> uncertainCells = new ArrayList<>();

        for (CellResult result : results){
            if (result.isUncertain()){
                uncertainCells.add(result.getCellId());
            }

        }

        
        return uncertainCells;
    }

    public String makePreliminaryDecision(){
        if (!getUncertainCells().isEmpty()){
            automaticStatus = "Требуется повторная проверка";
        } else if (!getEmptyCells().isEmpty()) {
            automaticStatus = "Обнаружена пустая ячейка";
        } else {
            automaticStatus = "Полный";
        }

        return automaticStatus;
    }

    public int getInspectionId(){return inspectionId;}

    public String getAutomaticStatus(){return automaticStatus;}

    public void setAutomaticStatus(String automaticStatus){
        this.automaticStatus = automaticStatus;
    }

    public String getFinalStatus(){return finalStatus;}

    public void confirm(String finalStatus, String confirmedAt){
        this.finalStatus = finalStatus;
        this.confirmedAt = confirmedAt;
    }

}
