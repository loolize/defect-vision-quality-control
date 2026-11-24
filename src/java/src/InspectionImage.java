import com.google.gson.annotations.SerializedName;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class InspectionImage {

    @SerializedName("image_id")
    private final int imageId;

    @SerializedName("blister_id")
    private final int blisterId;

    @SerializedName("file_path")
    private String filePath;

    @SerializedName("captured_at")
    private String capturedAt;

    private transient Mat image;



    
    public InspectionImage(int imageId, int blisterId, String filePath) {
        this.imageId = imageId;
        this.blisterId = blisterId;
        this.filePath = filePath;
    }

    public boolean load() {
        image = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_GRAYSCALE);

        return !image.empty();
    }

    public boolean checkQuality() {
        if (image == null || image.empty()) {return false;
        }

        double brightness = Core.mean(image).val[0];

        Mat laplacian = new Mat();
        Imgproc.Laplacian(image, laplacian, CvType.CV_64F);

        MatOfDouble mean = new MatOfDouble();
        MatOfDouble stddev = new MatOfDouble();

        Core.meanStdDev(laplacian, mean, stddev);

        double sharpness = Math.pow(stddev.toArray()[0], 2);


        laplacian.release();
        mean.release();
        stddev.release();

        return brightness >= 50 && brightness <= 230 && sharpness >= 15;
    }

    public Mat getImage() {return image;}

    public int getImageId() {return imageId;}

    public int getBlisterId(){return blisterId;}

    public String getFilePath(){return filePath;}

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getCapturedAt(){return capturedAt;
    }

    public void setCapturedAt(String capturedAt){
        this.capturedAt = capturedAt;
    }


}

