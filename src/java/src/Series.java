import com.google.gson.annotations.SerializedName;

public class Series{

    @SerializedName("series_id")
    private final int seriesId;

    @SerializedName("series_number")
    private final String seriesNumber;


    public Series(int seriesId, String seriesNumber) {
        this.seriesId = seriesId;
        this.seriesNumber = seriesNumber;
    }

    public int getSeriesId(){return seriesId;}

    public String getSeriesNumber(){return seriesNumber;}
}


