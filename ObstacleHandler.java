
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class ObstacleHandler {
 
    // Instance Variables for ObstacleHandler
    private BufferedImage box;
    private ArrayList<Obstacle> obstacles;
    private Skateboard skateboarder;

    /**
     * Initializes an ObstacleHandler object. 
     * Precondition: ObstacleHandler object must take a Skateboard skateboarder
     * Postcondition: Instance variables Skateboard skateboarder, BufferedImage obstacle, and ArrayList obstacles are initialized
     * 
     * @param skateboarder -Skateboard to sense for collisions
     */
    public ObstacleHandler(Skateboard skateboarder) 
    {
        box = Resources.getResourcesImage("Crate1.png");
        obstacles = new ArrayList<Obstacle>();
        this.skateboarder = skateboarder;
        obstacles.add(createObstacle());
    }
 
    // Updates rendering for obstacle in obstacles, respawns when out of screen, and adds score when obstacle is avoided
    public void update() 
    {
        for (Obstacle obs : obstacles)
        {
            obs.update();
        }
        Obstacle obstacle = obstacles.get(0);
        
        if (obstacle.isOutOfScreen()) 
        {
            skateboarder.addScore();
            obstacles.clear();
            obstacles.add(createObstacle());
        }
    }
 
     /**
     * Handles graphics of multiple Obstacle objects.
     * Precondition: Obstacle object must be initialized.
     * Postcondition: Graphics of Obstacle object are drawn
     * 
     * @param g -Graphics to be rendered for Obstacle object
     */
    public void draw(Graphics g) 
    {
        for (Obstacle obs : obstacles) 
        {
            obs.draw(g);
        }
    }
 
    // Creates an Obstacle object with constant atttributes and randomized x-position
    private Obstacle createObstacle() 
    {
        return new Roadblock(skateboarder, (int)(Math.random() * (1200+1 - 800) + 800), box.getWidth(), box.getHeight(), box);
    }
 
    // Checks if Skateboard object has collided with obstacles
    public boolean isCollision() 
    {
        for (Obstacle obs : obstacles)
        {
            if (skateboarder.getBounds().intersects(obs.getBounds())) 
            {
                return true;
            }
        }
        return false;
    }
 
    // Clears rendering of previous obstacles if user chooses to restart
    public void reset() 
    {
        obstacles.clear();
        obstacles.add(createObstacle());
    }
 
}