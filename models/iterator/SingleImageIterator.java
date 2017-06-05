package medicalimageviewer.models.iterator;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import medicalimageviewer.models.Study;

/**
 * Single Image Iterator. This class provides the mode for single
 * Image viewing
 * 
 * @author Jeffrey Haines
 */
public class SingleImageIterator implements StudyImageIterator, Serializable {
    private int index;
    
    /**
     * Creates a SingleImageIterator and starts it at the beginning of the list
     * @author Jeffrey Haines
     */
    public SingleImageIterator() {
        this.index = 0;
    }
    
    /**
     * Creates a SingleImageIterator and starts it at the specified index
     * @param index Index to start at, rounded down to the nearest fourth
     * @author Jeffrey Haines
     */
    public SingleImageIterator(int index) {
        this.index = index;
    }
    
    /**
     * Goes to the next image in a study
     * @param study Is the study that is currently loaded into the program
     * @return returns a boolean for if there is a next or not
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
     * Goes to the previous image in a study
     * @return returns a boolean
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
     * The get Images function provides an access point to the images in a 
     * study
     * @param study The study that is currently loaded into the program
     * @return returns a set of images
     * @author Jeffrey Haines
     */
    @Override
    public BufferedImage[] getImages(Study study) {
        BufferedImage[] set = new BufferedImage[1];
        set[0] = study.getStudyImages().get(index).getImage();
        return set;
    }
    
    /**
     * returns the index of the current picture
     * @return returns the int of the index
     * @author Jeffrey Haines
     */
    @Override
    public int getIndex() {
        return index;
    }
}
