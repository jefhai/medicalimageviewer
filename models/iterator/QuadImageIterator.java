package medicalimageviewer.models.iterator;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import medicalimageviewer.models.Study;

/**
 * This class provides the mode for QuadImage viewing by iterating through the
 * images in sets of 4
 * 
 * @author Jeffrey Haines
 */
public class QuadImageIterator implements StudyImageIterator, Serializable {
    private int index;

    /**
     * The constructor for the quad image iterator
     * @author Jeffrey Haines
     */
    public QuadImageIterator() {
        this.index = 0;
    }
    
    /**
     * Creates a QuadImageIterator and starts it at the specified index
     * @param index Index to start at
     * @author Jeffrey Haines
     */
    public QuadImageIterator(int index) {
        this.index = (index/4)*4; // Round down to nearest 4th
    }
    
    /**
     * Allows the program to progress to the next image in a study
     * @param study The study class the contains the images that are currently being viewed
     * @return returns a boolean
     * @author Jeffrey Haines
     */
    @Override
    public boolean next(Study study) {
        if(index+4 >= study.getStudyImages().size())
            return false;
        index += 4;
        return true;
    }

    /**
     * Allows to iterate to the previous image in a study
     * @return returns a boolean
     * @author Jeffrey Haines
     */
    @Override
    public boolean prev() {
        if(index-4 < 0)
            return false;
        index -= 4;
        return true;
    }

    /**
     * Gets the next 4 images 
     * @param study the study class the contains the images that are currently being viewed
     * @return A buffered image list of 4 images
     * @author Jeffrey Haines
     */
    @Override
    public BufferedImage[] getImages(Study study) {
        BufferedImage[] set = new BufferedImage[4];
        set[0] = getOrNull(study, index);
        set[1] = getOrNull(study, index+1);
        set[2] = getOrNull(study, index+2);
        set[3] = getOrNull(study, index+3);
        return set;
    }
    
    /**
     * Gets the index of the image iterator
     * @return returns an int representing the index that the iterator is at currently
     * @author Jeffrey Haines
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * Get or null gets the next image in the study or sets it to null if there
     * is no image
     * @param study the study class the contains the images that are currently being viewed
     * @param i what view setting we are on
     * @return returns the image at the given i value, if no image is there it returns null
     * @author Jeffrey Haines
     */
    private BufferedImage getOrNull(Study study, int i) {
        if(i >= study.getStudyImages().size())
            return null;
        else
            return study.getStudyImages().get(i).getImage();
    }
}
