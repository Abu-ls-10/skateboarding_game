// Imports necessary modules
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Resources 
{
    /**
     * Identifies source and handles errors of graphical file imports
     * Precondition: Path must be established and image should be accessible.
     * Postcondition: Refines sizing and quality to fit game rendering.
     * 
     * @param path -String to confirm path and check through possible sourcing errors
     * @return img -BufferedImage object
     */
    public static BufferedImage getResourcesImage(String path) 
    {
        BufferedImage img = null;
        try 
        {
            img = ImageIO.read(new File(path));
        } 
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return img;
    }
}