package medicalimageviewer.memento;

import java.util.Stack;

/**
 * Iterator Care Taker
 *
 * @author Jeffrey Haines
 */
public class IteratorCareTaker {
    private Stack<IteratorMemento> changes;
    
    /**
     * Iterator Care Taker
     * @author Jeffrey Haines
     */
    public IteratorCareTaker() {
        changes = new Stack<>();
    }
    
    /**
     * Push
     * @param m the list of iterators to be saved
     * @author Jeffrey Haines
     */
    public void push(IteratorMemento m)
    {
        changes.push(m);
    }
    
    /**
     * Pop
     * @return the list of iterators to be restored
     * @author Jeffrey Haines
     */
    public IteratorMemento pop()
    {
        return changes.pop();
    }
}
