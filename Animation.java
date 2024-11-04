// Imports necessary modules
import java.awt.*;
import java.util.*;
import java.awt.image.*;

public class Animation {
    
    // Initializes instance variables
    private ArrayList<BufferedImage> list;
    private long deltaTime;
    private int currentFrame = 0;
    private long previousTime;

    // Creates constructor to initiate object attributes and behaviour
    public Animation(int deltaTime) 
    {
        this.deltaTime = deltaTime;
        list = new ArrayList<BufferedImage>();
        previousTime = 0;
    }

    // Updates image positioning and frame to render animation
    public void updateFrame() 
    {
        if (System.currentTimeMillis() - previousTime >= deltaTime) 
        {
            currentFrame++;
            if (currentFrame >= list.size()) 
            {
                currentFrame = 0;
            }
            previousTime = System.currentTimeMillis();
        }
    }

    // Adds frame to Animation list
    public void addFrame(BufferedImage image) 
    {
        list.add(image);
    }

    // Returns frame when asked by program
    public BufferedImage getFrame() 
    {
        return list.get(currentFrame);
    }
}
