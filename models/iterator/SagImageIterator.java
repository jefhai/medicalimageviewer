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
public class SagImageIterator implements StudyImageIterator, Serializable {
    private int index;
    private int size; 
    
    
    /**
     * Creates a WindowImageIterator and starts it at the beginning of the list
     * @param study the study that contains the images to be iterated over
     * @author Jeffrey Haines
     */
    public SagImageIterator(Study study) {
        this.index = 0;
        this.size = study.getStudyImages().get(0).getResolution()[0];
    }
    
    /**
     * Creates a WindowImageIterator and starts it at the specified index
     * @param study the study that contains the images to be iterated over
     * @param index Index to start at.
     * @author Jeffrey Haines
     */
    public SagImageIterator(Study study, int index) {
        this.index = index;
        this.size = study.getStudyImages().get(0).getResolution()[0];
    }
    
    /**
     * next gets the next image in a study
     * @param study the study that contains the images to be iterated over
     * @return returns a boolean
     * @author Jeffrey Haines
     */
    @Override
    public boolean next(Study study) {
        if(index == this.size - 1)
            return false;
        index++;
        return true;
    }

    /**
     * gets the previous image from the study
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
     * Gets the images that are contained within the study
     * @param study the study that contains the images
     * @return returns a set of pixels
     * @author Jeffrey Haines
     */
    @Override
    public BufferedImage[] getImages(Study study) {
        BufferedImage[] set = new BufferedImage[1];
        set[0] = study.getRec().construct2D('x', index);
        return set;
    }
    
    /**
     * gets the index that the iterator is currently at
     * @return returns the index
     * @author Jeffrey Haines
     */
    @Override
    public int getIndex() {
        return index;
    }
}
