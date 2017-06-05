package medicalimageviewer.models;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This class provides the reconstruction functionality stated by
 * the requirement. The reconstruction functionality makes a new image by
 * obtaining the pixels from the x,y,z points of the layered images
 *
 * @author Jeffrey Haines
 */
public class Reconstruction {
    int rec[][][];
    int zDims;
    int yDims;
    int xDims;
    
    public Reconstruction (List<StudyImage> images) {
        this.rec = create3d(images);
    }

    /**
     * Creates a 3d layer of the images
     * @param images
     * @return 3d reconstruction
     * @author Jeffrey Haines
     */
    private int[][][] create3d(List<StudyImage> images) {
        int count = 0;
        BufferedImage image = images.get(0).getImage();
        this.zDims = images.size();
        this.yDims = image.getHeight();
        this.xDims = image.getWidth();
        int rec_[][][] = new int[ zDims ][ xDims ][ yDims ];
      
        for(StudyImage i : images) {
            image = i.getImage();
            
            for(int x = 0; x < image.getWidth(); x++) {
                for(int y = 0; y < image.getHeight(); y++) {
                    rec_[count][x][y] = image.getRGB(x, y);
                }
            }
            count++;
        }
        return rec_;
    }
    
    /**
     * Construct the 2D Images based on the axis stated from the constructed
     * 3d image
     * @param axis  character denoting the axis we are displaying on
     * @param index the index (pixel) that we are displaying at
     * @return Returns a created buffered image
     * @author Jeffrey Haines
     */
    public BufferedImage construct2D(char axis, int index) {
        BufferedImage creation;
        switch(axis){
            case 'x':   creation = new BufferedImage(zDims, yDims, BufferedImage.TYPE_INT_RGB);
                        for(int z = 0; z<zDims;z++){
                            for(int y = 0; y<yDims;y++){
                                creation.setRGB(zDims-(z+1), y, rec[z][index][y]);
                            }
                        }
                        break;
            case 'y':   creation = new BufferedImage(xDims, zDims, BufferedImage.TYPE_INT_RGB);
                        for(int z = 0; z<zDims;z++){
                            for(int x = 0; x<xDims;x++){
                                creation.setRGB(x, zDims-(z+1), rec[z][x][index]);
                            }
                        }
                        break;
            case 'z':   creation = new BufferedImage(xDims, yDims, BufferedImage.TYPE_INT_RGB);
                        for(int y = 0; y<yDims;y++){
                            for(int x = 0; x<xDims;x++){
                                creation.setRGB(x, y, rec[index][x][y]);
                            }
                        }
                        break;
            default:    return null;
        }
        return creation;
    }
    
    /**
     * Displays the 2d image on view based on the reconstruction (Windowing)
     * @param axis  character denoting the axis we are displaying on
     * @param index the index (pixel) that we are displaying at
     * @param min the minimum intensity
     * @param max the maximum intensity
     * @return a windowed buffered image
     * @author Jeffrey Haines
     */
    public BufferedImage window(char axis, int index, int min, int max)
    {
        BufferedImage temp = construct2D(axis, index);
        BufferedImage out = new BufferedImage(temp.getWidth(null), temp.getWidth(null), BufferedImage.TYPE_INT_ARGB);
        
        Color minColor = new Color(min, min, min);
        Color maxColor = new Color(max, max, max);
        
        for(int y = 0; y < temp.getHeight() - 1; y++) {
            for(int x = 0; x < temp.getWidth() - 1; x++) {
                Color currentColor = new Color(temp.getRGB(x, y), false);
                if(currentColor.getBlue() > maxColor.getBlue())
                    out.setRGB(x, y, Color.WHITE.getRGB());
                else if(currentColor.getBlue() < minColor.getBlue())
                    out.setRGB(x, y, Color.BLACK.getRGB());
                else 
                {
                    int newColor = generateScaledColor(currentColor.getBlue(), minColor, maxColor);
                    out.setRGB(x, y, new Color(newColor, newColor, newColor).getRGB());
                }
            }
        }
        
        return out;
    }
    
    /**
     * Generate Scale Color
     * @param color color to be scaled
     * @param min the minimum color
     * @param max the maximum color
     * @return a scaled color value
     * @author Jeffrey Haines
     */
    private int generateScaledColor(int color, Color min, Color max) {
        double slope = 255 / (max.getBlue() - min.getBlue());
        int returnValue =  (int)(slope * (color - min.getBlue()));
        return returnValue;
    }
}
