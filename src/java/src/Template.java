import com.google.gson.annotations.SerializedName;

public class Template {

    @SerializedName("template_id")
    private final int templateId;

    private final String name;

    public Template(int templateId, String name){
        this.templateId = templateId;
        this.name = name;
    }

    public int getTemplateId(){return templateId;}

    public String getName(){return name;}


}

