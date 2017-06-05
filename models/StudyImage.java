package medicalimageviewer.models;

import java.awt.image.BufferedImage;

/**
 * The Image class provides the image file and associates it with a name.
 * 
 * @author Jeffrey Haines
 */
public class StudyImage {
    private String imageName;   // e.g. 1.jpeg
    private int[] resolution;   // [height, width]
    private BufferedImage image;
    
    /**
     * Image File.
     * 
     * @param name e.g. 1.jpeg, where 1 is the name.
     * @param resolution [height, width]
     * @param image the image object
     * @author Jeffrey Haines
     */
    public StudyImage(String name, int[] resolution, BufferedImage image) {
        this.imageName = name;    
        this.resolution = new int[2];
        this.resolution = resolution;
        this.image = image;   
    }
    
    /**
     * Get the Image File.
     * 
     * @return returns the study image
     * @author Jeffrey Haines
     */
    public BufferedImage getImage() {
        return image; 
    }
    
    /**
     * Set the Image File.
     * 
     * @param image the new image
     * @author Jeffrey Haines
     */
    public void setImage(BufferedImage image) {
        this.image = image; 
    }
    
    /**
     * Get the Resolution of Image.
     * 
     * @return return [height, width]
     * @author Jeffrey Haines
     */
    public int[] getResolution() {
        return resolution;
    }
    
    /**
     * Set Resolution of Image.
     * 
     * @param resolution [height, width]
     * @author Jeffrey Haines
     */
    public void setResolution(int[] resolution) {
        this.resolution = resolution;
    }
    
    /**
     * Get Name of the Image.
     * 
     * @return returns the name
     * @author Jeffrey Haines
     */
    public String getName() {
        return imageName;
    }
    
    /**
     * Set Name of the Image.
     * 
     * @param imageName the new name of the image
     * @author Jeffrey Haines
     */
    public void setName(String imageName) {
        this.imageName = imageName;
    }
    
    /**
     * Formats image's name and converts it to String.
     * 
     * @return e.g. Image name: 1    Resolution: [1280,720]
     * @author Jeffrey Haines
     */
    @Override
    public String toString() {
        return "Image name: " + imageName + "    Resolution: " + resolution[0] + ", " + resolution[1];
    }
}