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
public class CorImageIterator implements StudyImageIterator, Serializable {
    private int index;
    private int size;

    /**
     * Creates a WindowImageIterator and starts it at the beginning of the list
     * @param study The study that contains the images that will be iterated over
     * @author Jeffrey Haines
     */
    public CorImageIterator(Study study) {
        this.index = 0;
        this.size = study.getStudyImages().get(0).getResolution()[1];
    }
    
    /**
     * Creates a WindowImageIterator and starts it at the specified index
     * @param study study that contains the images that will be used
     * @param index Index to start at
     * @author Jeffrey Haines
     */
    public CorImageIterator(Study study, int index) {
        this.index = index;
        this.size = study.getStudyImages().get(0).getResolution()[1];
    }
    
    /**
     * Next gets the next reconstructed image
     * @param study The study that contains the images
     * @return returns a boolean.
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
     * prev gets the previous image
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
     * getImages takes a study and then gets a set of buffered images that are
     * then reconstructed
     * @param study the study that contains the images
     * @return Returns a set of reconstructed images
     * @author Jeffrey Haines
     */
    @Override
    public BufferedImage[] getImages(Study study) {
        BufferedImage[] set = new BufferedImage[1];
        set[0] = study.getRec().construct2D('y', index);
        return set;
    }
    
    /**
     * gets the current index
     * @return returns an int that is the current index
     * @author Jeffrey Haines
     */
    @Override
    public int getIndex() {
        return index;
    }
}
