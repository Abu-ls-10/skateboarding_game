// Imports necessary modules
import java.awt.*;
import java.awt.image.*;

// Coin class inherits from abstract class Obstacle
public class Coin extends Obstacle
{
    // Defines ground level for Coin object
    public static final int GROUND_LEVEL = 570;

    // Instance variables for Coin
    private int posX;
    private int posY;
    private int width;
    private int height;
    private BufferedImage image;
    private Skateboard skateboarder;
    private Rectangle rectBound;
 
    /**
     * Initializes a Coin object. 
     * Precondition: Coin object must take a Skateboard skateboarder, int posX, int, width, int height, and BufferedImage image
     * Postcondition: Instance variables skateboarder, posX, posY, width, height, and image are initialized
     * 
     * @param skateboarder -Skateboard to initialize sensing of other objects
     * @param posX - int to initialize positioning on x-axis for Coin object
     * @param width -int to initialize width of Coin object
     * @param height - int to initialize height of Coin object
     * @param image -BufferedImage to initialize graphics for Coin object
     */
    public Coin(Skateboard skateboarder, int posX, int width, int height, BufferedImage image) 
    {
        this.posX = posX;
        this.posY = GROUND_LEVEL;
        this.width = width;
        this.height = height;
        this.image = image;
        this.skateboarder = skateboarder;
        rectBound = new Rectangle();
    }

    /**
     * Updates x-position of object relative to skateboarder speed
     * Precondition: Roadblock and Skateboard object must be initialized
     * Postcondition: Roadblock will be gradually moving closer to Skateboard skateboarder
     */
    public void update() 
    {
        posX -= skateboarder.getSpeedX();
    }

    /**
     * Draws graphics of a given Coin object.
     * Precondition: Coin object must be initialized.
     * Postcondition: Graphics of Coin object are drawn
     * 
     * @param g -Graphics to be rendered for Coin object
     */
    public void draw(Graphics g) 
    {
        g.drawImage(image, posX, posY, null);
        g.setColor(Color.red);

        //Rectangle bound = getBounds();
        //g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    /**
     * Sets boundaries of Coin object
     * Precondition: Coin object must be initialized.
     * Postcondition: A non-visible rectangle is formed around Coin object as bounds.
     * 
     * @return rectBound -Rectangle object with set dimensions and positions
     */
    public Rectangle getBounds() 
    {
        rectBound = new Rectangle();
        rectBound.x = posX;
        rectBound.y = posY;
        rectBound.width = width;
        rectBound.height = height;
        return rectBound;
    }

    /**
     * Checks if Coin object is out of screen
     * Precondition: Coin object and its graphics must initialized.
     * Postcondition: Coin object will respawn
     * 
     * @return true -if Coin object is out of screen
     * @return false -if Coin object is within screen
     */
    public boolean isOutOfScreen() 
    {
        if(posX < -image.getWidth()) 
        {
            return true;
        }
        return false;
    }

    
}
