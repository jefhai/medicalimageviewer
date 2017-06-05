package medicalimageviewer.models.iterator;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import medicalimageviewer.models.Study;

/**
 * This class provides the mode for Window image viewing by iterating through the
 * images in sets of 4
 * 
 * @author Jeffrey Haines
 */
public class WindowImageIterator implements StudyImageIterator, Serializable {
    private int index;
    private int min;
    private int max;

    /**
     * Creates a WindowImageIterator and initializes it at the beginning of the
     * list
     * @author Jeffrey Haines
     */
    public WindowImageIterator() {
        this.index = 0;
    }
    
    /**
     * Creates a WindowImageIterator and initializes it at the specified index
     * @param index Index to start at
     * @param min 
     * @param max
     * @author Jeffrey Haines
     */
    public WindowImageIterator(int index, int min, int max) {
        this.index = index;
        this.min = min;
        this.max = max;
    }
    
    /**
    * Method for Next (to iterate to next image)
    * @param study study contains the images that are being iterated over
    * @return returns the boolean
    * @author Jeffrey Haines
    */
    @Override
    public boolean next(Study study) {
        if(index == study.getStudyImages().size()-1)
            return false;
        index++;
        return true;
    }
    
    /**
    * Method for Prev (to iterate to previous image)
    * @return returns the boolean
    * @author Jeffrey Haines
    */
    @Override
    public boolean prev() {
        if(index == 0)
            return false;
        index--;
        return true;
    }

    /**
     * Returns reconstructed image set
     * @param study the study that contains the images that are being read in
     * @return returns a set of images
     * @author Jeffrey Haines
     */
    @Override
    public BufferedImage[] getImages(Study study) {
        BufferedImage[] set = new BufferedImage[1];
        set[0] = study.getRec().window('z', index, min, max);
        return set;
    }
    
    /**
     * Method for getting the index of the image currently in
     * @return returns the index as an int
     * @author Jeffrey Haines
     */
    @Override
    public int getIndex() {
        return index;
    }
}
