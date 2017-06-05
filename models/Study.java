package medicalimageviewer.models;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import medicalimageviewer.models.iterator.*;

/**
 * The Study class provides functionalities such as opening images,handling
 * StudyImage in the form of lists, etc. It is also responsible for accessing
 * which iterator to use for displaying the images on the window screen.
 * @author Jeffrey Haines
 */
public class Study {
    private String studyName;
    private final List<StudyImage> studyImages;
    private int mode;   //this is the view mode selected
    private Reconstruction rec; 
    private File studyPath;
    
    // accepted image extensions
    static final String[] EXTENSIONS = new String[]{"jpeg", "jpg", "JPEG", "acr" };
    
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS)
                if (name.endsWith("." + ext)) { return (true); }
            return (false);
        }};
    
    /**
     * Study (list of images).
     * @author Jeffrey Haines
     */
    public Study() {
        this.studyImages = new LinkedList<>();
    }
    
    /**
     * Opens a study file (.sdy).
     * @param studyPath the path to the study
     * @return the appropriate iterator
     * @author Jeffrey Haines
     */
    public ArrayList<StudyImageIterator> open(File studyPath) {    
        if (studyPath != null) {
            this.studyPath = studyPath;
            studyName = studyPath.getName();
            
            // get study images
            for(File i : studyPath.listFiles(IMAGE_FILTER))
            {
                try {
                    
                    BufferedImage fImage;
                    if(i.getName().endsWith(".acr")){
                        fImage = readACR(i);
                    }
                    else{
                        FileInputStream fInput = new FileInputStream(i);
                        fImage = ImageIO.read(fInput);
                    }
                    
                    int[] res = {fImage.getHeight(), fImage.getWidth()};

                    StudyImage image = new StudyImage(i.getName(), res, fImage);
                    studyImages.add(image);     
                } catch (IOException ex) {
                    Logger.getLogger(Study.class.getName()).log(Level.SEVERE, 
                                null, ex);
                }  
            } 

            // create new reconstruction
            rec = new Reconstruction(studyImages);
            
            ArrayList<StudyImageIterator> iterators = new ArrayList<>(6);
            if (new File(studyPath, this.studyName.concat(".sdy")).exists())
            {
                try
                {
                   FileInputStream fileIn = new FileInputStream(new File(studyPath, 
                                                    this.studyName.concat(".sdy")));
                   ObjectInputStream in = new ObjectInputStream(fileIn);
                   iterators = (ArrayList<StudyImageIterator>) in.readObject();
                   this.mode = (int) in.readObject();
                   in.close();
                   fileIn.close();
                   return iterators;
                }catch(IOException i)
                {
                   return null;
                }catch(ClassNotFoundException c)
                {
                   return null;
                }
            } else {     
                iterators.add(0, new SingleImageIterator());
                iterators.add(1, new QuadImageIterator());
                iterators.add(2, new WindowImageIterator());
                iterators.add(3, new SingleImageIterator());
                iterators.add(4, new SagImageIterator(this, 0));
                iterators.add(5, new CorImageIterator(this, 0));
                return iterators;
            }
        }
        return null;
    }
    
    /**
     * Save. saves a .sdy file to the study directory
     * containing the study mode (view1/view4) and the
     * image to be viewed
     * @param iterators there iterator list to be saved
     * @author Jeffrey Haines
     */
    public void save(ArrayList<StudyImageIterator> iterators) {
        try
        {
           FileOutputStream fileOut = new FileOutputStream(new File(studyPath, 
                                                this.studyName.concat(".sdy")));
           ObjectOutputStream out = new ObjectOutputStream(fileOut);
           out.writeObject(iterators);
           out.writeObject(this.mode);
           out.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }
    
    /**
     * saveAs. save a study to a new directory
     * @param iterators an arraylist of the iterators.
     * @param savePath the path of the newly saved study
     * @return the new study with it's new file path and name.
     * @author Jeffrey Haines
     */
    public Study saveAs(ArrayList<StudyImageIterator> iterators, File savePath) {
        try {     
            if(!savePath.exists()){
                savePath.mkdir();
            } else {
                savePath = new File(savePath, savePath.getName()+"_copy");
            }
            // Save images to new path
            for(StudyImage i: this.studyImages) 
                ImageIO.write(i.getImage(), "JPEG", 
                            new File(savePath, i.getName()+".JPEG"));
            
            // Create new study, try to open and save it, then return it.
            Study newStudy = new Study();
            newStudy.open(savePath);
            newStudy.setMode(mode);
            newStudy.save(iterators); 
            return newStudy;
        } catch(IOException ex) {
            return this;
        } 
    }
    
    /**
     * Reads the .sdy file into an array list
     * @param iterators the current state iterators.
     * @return if the saved state is equivalent to the current state
     * @author Jeffrey Haines
     */
    public boolean checkWithSaved(ArrayList<StudyImageIterator> iterators) {
        if (new File(this.studyPath, this.studyName.concat(".sdy")).exists())
        {
            try 
            {
                FileInputStream fileIn = new FileInputStream(new File(this.studyPath, 
                         this.studyName.concat(".sdy")));
                ObjectInputStream in = new ObjectInputStream(fileIn);
                ArrayList<StudyImageIterator> savedIterators = (ArrayList<StudyImageIterator>) in.readObject();
                int savedMode = (int) in.readObject();
                in.close();
                fileIn.close();
               
                // check if iterators are the same
                boolean equal = false;
                for (int i = 0; i < iterators.size(); i++)
                {
                    if (!savedIterators.get(i).equals(iterators.get(i)))
                    {
                        equal = false;
                        break;
                    }
                    else
                        equal = true;
                }
                
                // check and retun if view mode is the same
                return savedMode == mode;
            }
            catch(IOException | ClassNotFoundException i)
            {
               return false;
            }
        }
        return false;
    }
    
    /**
     * Read Default. This function reads the default study.
     * @return the file path to the default study  
     * @author Jeffrey Haines
     */
    public File readDefault(){
        FileReader fr;
        BufferedReader br;
        String line;    // file line 
        File study = null; // the study the default study path points to
        File defaultSdy;
        
        Preferences userPreferences = Preferences.userRoot();
        
        String defaultValue = "";
        if ( !userPreferences.get(
                "MIV_DEFAULT_STUDY", defaultValue).equals("") ) {    
            return study = new File(userPreferences.get(
                    "MIV_DEFAULT_STUDY", defaultValue));
        }
        else 
            return null;
    }
    
    /**
     * Set Default. This function sets the study as default fore Read Default.
     * @author Jeffrey Haines
     */
    public void setDefault() {
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("MIV_DEFAULT_STUDY", studyPath.toString());
    }
    
    /**
     * Provides the copy of for the requested study object.
     * @return returns this study object
     * @author Jeffrey Haines
     */
    public Study copyOf() {
        return this;   
    }
    
    /**
     * Gets the name of the study.
     * @return returns the name
     * @author Jeffrey Haines
     */
    public String getName() {
        return studyName;
    }
    
    /**
     * Sets the name of the study.
     * @param studyName the new name of the study
     * @author Jeffrey Haines
     */
    public void setName(String studyName) {
        this.studyName = studyName;   
    }
    
    /**
     * Gets the current ViewMode of thr Study.
     * @return mode the view mode of the study
     * @auther Jeffrey Haines
     */
    public int getMode() {
       return mode;
    }
    
    /**
     * Changes the ViewMode of the study.
     * @param mode the view mode of the study
     * @auther Jeffrey Haines
     */
    public void setMode(int mode) {
       this.mode = mode;
    }
    
    /**
     * Gets the File path.
     * @return mode the view mode of the study
     * @author Jeffrey Haines
     */
    public File getPath() {
       return studyPath;
    }
    
    /**
     * To String.
     * @return e.g. Study name: myFirst
     * @author Jeffrey Haines
     */
    @Override
    public String toString() {
        return "Study name: " + studyName;
    }
    
    /**
     * Gets the reconstruction from the study
     * @return returns the reconstruction.
     * @auther Jeffrey Haines
     */
    public Reconstruction getRec() {
        return rec;
    }
    
    /**
     * Allows for access to the study images.
     * @return returns the study images.
     * @auther Jeffrey Haines
     */
    public List<StudyImage> getStudyImages() {
        return studyImages;
    }
    
    /**
     * Read ACR.
     * @param file the ACR file
     * @return the created buffered Image
     * @auther Jeffrey Haines
     */
    public BufferedImage readACR(File file){
        FileImageInputStream imageFile = null;
        try{
            imageFile = new FileImageInputStream(file);
            imageFile.seek(0x2000);
        } catch(IOException e) {
            return null;
        }
        int sliceWidth = 256;
    	int sliceHeight = 256;
    	    
    	BufferedImage sliceBuffer = 
    	    new BufferedImage( sliceWidth,sliceHeight,
    			       BufferedImage.TYPE_USHORT_GRAY );
        
        for ( int i = 0; i < sliceBuffer.getHeight(); i++ ) {
    	    for ( int j = 0; j < sliceBuffer.getWidth(); j++ ) {
        		int pixel, pixelHigh, pixelLow;
        		try {
        		    pixelHigh = imageFile.read();
        		    pixelLow = imageFile.read();
        		    pixel = pixelHigh << 4 | pixelLow >> 4;
        		    
        		    sliceBuffer.setRGB( j, i, pixel << 16 | pixel << 8 | pixel);

        		}
        		catch (IOException e) {
        		    System.err.print("IO error readin byte: ");
        		    System.err.println(e.getMessage());
        		    System.exit(2);
        		}
    	    }
    	}
        
        return sliceBuffer;
    }
}