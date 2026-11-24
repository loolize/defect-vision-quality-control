import com.google.gson.annotations.SerializedName;

public class TemplateCell {

    @SerializedName("cell_id")
    private final int cellId;

    @SerializedName("template_id")
    private final int templateId;

    @SerializedName("position_number")
    private final int positionNumber;

    
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public TemplateCell(int cellId, int templateId, int positionNumber, int x, int y, int width, int height){
        this.cellId = cellId;
        this.templateId = templateId;
        this.positionNumber = positionNumber;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;


    }

    public int getCellId(){return cellId;}

    public int getTemplateId(){return templateId;}

    public int getPositionNumber(){return positionNumber;}

    public int getX(){return x;}
    public int getY(){return y;}

    public int getWidth(){return width;}

    public int getHeight(){return height;
    }

    public boolean isValid(int imageWidth, int imageHeight) {
        return x >= 0 && y >= 0
                && width > 0 && height > 0
                && x + width <= imageWidth
                && y + height <= imageHeight;
    }




}

