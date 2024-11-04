// Imports necessary modules
import java.awt.*;
import java.awt.image.*;

public class Skateboard 
{
    // Declares values to remain constant throughout the game
    public static final int GROUND = 300;
    public static final double GRAVITY = 4.5;
    private static final int RIDING = 0;
    private static final int OLLIE = 1;
    private static final int CRASH = 2;

    // Instance variables of Skateboard object
    private float posY;
    private float posX;
    private int speedX;
    private Rectangle rectBound;
    public int score = 0;
    private int state = RIDING;
    private Animation ridingAnimation;
    private BufferedImage ollie;

    /**
     * Initializes a Skateboard object. 
     * Precondition: Skateboard object instance variable must be declared
     * Postcondition: Instance variables posX, posY, rectBound, ridingAnimation, and ollie are initialized
     */
    public Skateboard() 
    {        
        posX = 50;
        posY = GROUND;
        rectBound = new Rectangle();
        ridingAnimation = new Animation(90);
        ridingAnimation.addFrame(Resources.getResourcesImage( "Skateboarder1.png"));
        //ridingAnimation.addFrame(Resources.getResourcesImage("Skateboarder2.png"));
        ollie = Resources.getResourcesImage("Skateboarder1.png");
    }
  
    /**
     * Returns Speed on x-axis
     * Precondition: Skateboard object must be initialized.
     * Postcondtiion: Returns speedX accessed from Skateboard Object.
     * 
     * @return speedX -the speed of the Skateboard object on the x-axis
     */
    public int getSpeedX()
    {
        return speedX;
    }

    /**
     * Adds hours to the variable numHours.
     * Precondition: ActivityLog object must be intialized.
     * Postcondition: Updates numHours by adding hours givn by user
     * 
     * @param speedX -the speed of the Skateboard object on the x-axis
     */
    public void setSpeedX(int speedX) 
    {
        this.speedX = speedX;
    }
    
    /**
     * Draws graphics of a given Skateboard object in different states.
     * Precondition: Skateboard object must be initialized.
     * Postcondition: Graphics of Skateboard object are drawn
     * 
     * @param g -Graphics to be rendered for Skateboard object
     */ 
    public void draw(Graphics g) 
    {
        // Assesses and renders each state independently
        switch(state) 
        {
            case RIDING:
                g.drawImage(ridingAnimation.getFrame(), (int) posX, (int) posY, null);
                break;

            case OLLIE:
                g.drawImage(ollie, (int) posX, (int) posY, null);
                break;

            case CRASH:
                break;
        }
    }
    
    // Makes sure the Skateboard object is always at gound level
    public void update() 
    {
        // Falls with gravity if in air
        if (posY < GROUND) 
        {
            posY += GRAVITY;
        } 
        
        // Confirms ground level as default y-position
        else 
        {
            posY = GROUND;
        }
        
        // Updates animation of skateboard
        ridingAnimation.updateFrame();
    }
    
    // Makes skateboard jump inverse to double GRAVITY when at ground level and adjusts state of player
    public void jump() 
    {
        if (posY == GROUND)
        {
            posY -= 70*GRAVITY;
            state = OLLIE;
        }
    }

    // Moves Skateboard object right if x-position is less then 800
    public void right() 
    {
        if (posX < 800)
        {
            posX += 50;
            speedX += 1;
        }
    }

    // Moves Skateboard object left if x-position is greater then 60
    public void left() 
    {
        if (posX > 60)
        {
            posX -= 50;
            speedX -= 1;
        }
    }

    /**
     * Sets boundaries of Skateboard object
     * Precondition: Skateboard object must be initialized.
     * Postcondition: A non-visible rectangle is formed around Skateboard object as bounds.
     * 
     * @return rectBound -Rectangle object with dimension and position of Skateboard object
     */
    public Rectangle getBounds() 
    {
        rectBound = new Rectangle();
        rectBound.x = (int) posX;
        rectBound.y = (int) posY;
        rectBound.width = ridingAnimation.getFrame().getWidth();
        rectBound.height = ridingAnimation.getFrame().getHeight();
        return rectBound;
    }
    
    // Checks if Skateboard object has collided with obstacle and manipulates states accordingly
    public void crash(boolean hasCrashed) 
    {
        if (hasCrashed) 
        {
            state = CRASH;
        } 
        
        else 
        {
            state = RIDING;
        }
    }
    

    // Clears score and sets Skateboard object back to ground level
    public void reset() 
    {
        posY = GROUND;
        score = 0;
    }

    /**
     * Returns score of user
     * Precondition: Skateboard object must be initialized.
     * Postcondtiion: Returns score accessed from Skateboard Object.
     * 
     * @return score -the score associated with the Skateboard object controlled by the user
     */
    public int getScore() 
    {
        return score;
    }

    /**
     * Adds to the score
     * Precondition: Skateboard object must be initialized.
     * Postcondtiion: Increases score by 1 point.
     */
    public void addScore() 
    {
        score += 1;
    }
}

