import com.google.gson.annotations.SerializedName;

public enum CellState {

    @SerializedName("empty")
    EMPTY("empty"),

    @SerializedName("filled")
    FILLED("filled"),

    @SerializedName("uncertain")
    UNCERTAIN("uncertain");

    private final String value;

    CellState(String value) {
        this.value = value;
    }


    
    @Override
    public String toString() {return value;}


}
