package medicalimageviewer.models.iterator;

import java.awt.image.BufferedImage;
import medicalimageviewer.models.Study;

/**
 * Study Image Iterator
 * 
 * @author Jeffrey Haines
 */
public interface StudyImageIterator {
    /**
     * Advances the iterator to the next image set
     * @param study the study that contains the images to be iterated over
     * @return True if successful, or false if the iterator is at the end
     * @author Jeffrey Haines
     */
    public abstract boolean next(Study study);
    
    /**
     * Rewinds the iterator to the previous image set
     * @return True if successful, or false if the iterator is at the beginning
     * @author Jeffrey Haines
     */
    public abstract boolean prev();
    
    
    /**
     * Returns the current image set
     * @return image set. Items in here can be null
     * @author Jeffrey Haines
     */
    public abstract BufferedImage[] getImages(Study study);
    
    /**
     * Gets the index of the current image set
     * @return index
     * @author Jeffrey Haines
     */
    public abstract int getIndex();
}
