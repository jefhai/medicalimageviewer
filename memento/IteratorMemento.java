package medicalimageviewer.memento;

import java.util.ArrayList;
import medicalimageviewer.models.iterator.StudyImageIterator;

/**
 * Iterator Memento
 *
 * @author Jeffrey Haines
 */
public class IteratorMemento {
    ArrayList<StudyImageIterator> it;
    int mode;
    
    /**
     * The constructor for the IteratorMemento
     * @param it An arraylist of StudyImageIterators that will be called within
     * @param mode An int that specifies the index that a specific iterator is at.
     * @author Jeffrey Haines
     */
    public IteratorMemento(ArrayList<StudyImageIterator> it, int mode)
    {
        this.it = it;
        this.mode = mode;
    }
    
    /**
     * Get Iterator
     * @return the list of iterators
     * @author Jeffrey Haines
     */
    public ArrayList<StudyImageIterator> getIterator()
    {
        return it;
    }
    
    /**
     * Get Mode
     * @return the view mode
     * @author Jeffrey Haines
     */
    public int getMode()
    {
        return mode;
    }
}
