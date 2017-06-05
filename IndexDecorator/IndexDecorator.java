package medicalimageviewer.IndexDecorator;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This is the class IndexDecorator, this allows lines on the image which 
 * tells the user where they are while scrolling through a reconstruction
 *
 * @author Jeffrey Haines
 */
public class IndexDecorator {
    private int sagIndex;
    private int corIndex;
    
    /**
     * Index Decorator.
     * @param sI the index of the sagittal iterator at the time this is updated
     * @param cI The index of the corneal iterator at the time this is updated
     * @author Jeffrey Haines
     */
    public IndexDecorator(int sI, int cI){
        this.sagIndex = sI;
        this.corIndex = cI;
    }
    
    /**
     * Draws a line over the buffered image iterator
     * @param pic The buffered image that will be decorated
     * @return returns the decorated image
     * @author Jeffrey Haines
     */
    public BufferedImage draw(BufferedImage pic){
        pic= drawSag(pic);
        pic= drawCor(pic);
        return pic;
    }
    
    /**
     * draws a line in the proper orientation on the buffered image
     * @param pic The picture that is to be decorated
     * @return the decorated picture
     * @author Jeffrey Haines
     */
    public BufferedImage drawSag(BufferedImage pic){
        for(int y = 0; y<pic.getHeight();y++){
            pic.setRGB(sagIndex, y, Color.blue.getRGB());
        }
        
        return pic;
    }
    
    /**
     * Draws a line in the proper orientation on the buffered image
     * @param pic The picture that is to be decorated
     * @return The decorated image
     * @author Jeffrey Haines
     */
    public BufferedImage drawCor(BufferedImage pic){
        for(int x = 0; x<pic.getWidth(); x++){
            pic.setRGB(x, corIndex, Color.red.getRGB());
        }
        return pic;
    }
}
