import com.google.gson.annotations.SerializedName;

public class Operator {

    @SerializedName("operator_id")
    private final int operatorId;

    @SerializedName("display_name")
    private final String displayName;


    
    public Operator(int operatorId, String displayName){
        this.operatorId = operatorId;
        this.displayName = displayName;
    }

    public int getOperatorId() {return operatorId;}

    public String getDisplayName(){return displayName;
    }


}
