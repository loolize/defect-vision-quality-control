import com.google.gson.annotations.SerializedName;

public class Blister {
    @SerializedName("blister_id")
    private final int blisterId;

    @SerializedName("series_id")
    private final int seriesId;

    @SerializedName("template_id")
    private final int templateId;

    public Blister(int blisterId, int seriesId, int templateId) {
        this.blisterId = blisterId;
        this.seriesId = seriesId;
        this.templateId = templateId;
    }

    public int getBlisterId(){return blisterId;}

    public int getSeriesId(){return seriesId;}

    public int getTemplateId(){return templateId;}

}


