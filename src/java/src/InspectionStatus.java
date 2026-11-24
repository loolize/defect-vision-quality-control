import com.google.gson.annotations.SerializedName;

public enum InspectionStatus {

    @SerializedName("Полный")
    FULL("Полный"),

    @SerializedName("Обнаружена пустая ячейка")
    EMPTY_CELL("Обнаружена пустая ячейка"),

    @SerializedName("Требуется повторная проверка")
    RECHECK("Требуется повторная проверка"),

    @SerializedName("Изображение непригодно")
    UNSUITABLE_IMAGE("Изображение непригодно");

    private final String value;

    InspectionStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString(){return value;}

}
