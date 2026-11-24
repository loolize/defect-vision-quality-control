import com.google.gson.annotations.SerializedName;

public class CellResult {
    @SerializedName("inspection_id")
    private final int inspectionId;

    @SerializedName("cell_id")
    private final int cellId;

    @SerializedName("automatic_state")
    private final CellState automaticState;



    public CellResult(int inspectionId, int cellId, CellState automaticState){
        this.inspectionId = inspectionId;
        this.cellId = cellId;
        this.automaticState = automaticState;
    }

    public int getInspectionId(){return inspectionId;}

    public int getCellId(){return cellId;}

    public CellState getAutomaticState(){return automaticState;
    }

    public boolean isEmpty(){
        return automaticState == CellState.EMPTY;
    }

    public boolean isUncertain() {
        return automaticState == CellState.UNCERTAIN;
    }




}
