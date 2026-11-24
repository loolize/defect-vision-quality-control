import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;


public class JsonStorage{

    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    private Template template;
    private List<TemplateCell> cells;

    public void loadTemplate(String path) throws IOException {

        String json = Files.readString(Path.of(path));

        JsonObject data = gson.fromJson(json, JsonObject.class);

        template = gson.fromJson(data.get("template"), Template.class);

        cells = gson.fromJson(data.get("cells"), new TypeToken<List<TemplateCell>>() {}.getType()
        );


    }

    public Template getTemplate(){return template;}

    public List<TemplateCell> getCells(){return List.copyOf(cells);}

    public void saveInspection(
            String path, Series series,
            Blister blister, InspectionImage image,
            Operator operator, Inspection inspection) throws IOException{

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("series", List.of(series));
        data.put("blisters", List.of(blister));
        data.put("templates", List.of(template));
        data.put("template_cells", cells);
        data.put("images", List.of(image));
        data.put("operators", List.of(operator));
        data.put("inspections", List.of(inspection));
        data.put("cell_results", inspection.getResults());

        Files.writeString(Path.of(path), gson.toJson(data));

        System.out.println("Данные записаны в файл: " + path);
    }



}

