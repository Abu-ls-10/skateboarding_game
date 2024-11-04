import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class CoinCollector {
 
    // Instance Variables for CoinCollector
    private BufferedImage coin;
    private ArrayList<Coin> coins;
    private Skateboard skateboarder;

    /**
     * Initializes a CoinCollector object. 
     * Precondition: CoinCollector object must take a Skateboard skateboarder
     * Postcondition: Instance variables Skateboard skateboarder, BufferedImage coin, and ArrayList coins are initialized
     * 
     * @param skateboarder -Skateboard to sense for collisions
     */
    public CoinCollector(Skateboard skateboarder) 
    {
        coin = Resources.getResourcesImage("Coin1.png");
        coins = new ArrayList<Coin>();
        this.skateboarder = skateboarder;
        coins.add(createCoin());
    }
 
    // Updates rendering for coin in coins and respawns when out of screen
    public void update() 
    {
        for (Coin c : coins)
        {
            c.update();
        }

        Coin coin = coins.get(0);
        
        if (coin.isOutOfScreen()) 
        {
            coins.clear();
            coins.add(createCoin());
        }
    }

    /**
     * Handles graphics of multiple Coin objects.
     * Precondition: Coin object must be initialized.
     * Postcondition: Graphics of Coin object are drawn
     * 
     * @param g -Graphics to be rendered for Coin object
     */
    public void draw(Graphics g) 
    {
        for (Coin c : coins) 
        {
            c.draw(g);
        }
    }
 
    // Creates a Coin object with constant atttributes
    private Coin createCoin() 
    {
        return new Coin(skateboarder, 900, coin.getWidth(), coin.getHeight(), coin);
        
    }
 
    // Checks if Skateboard object collects Coin through intersecting bounds
    public boolean isCollected() 
    {
        for (Coin c : coins)
        {
            if (skateboarder.getBounds().intersects(c.getBounds())) 
            {
                coins.clear();
                coins.add(createCoin());
                return true;
            }
        }
        return false;
    }
 
    // Clears rendering of previous coins if user chooses to restart
    public void reset() 
    {
        coins.clear();
        coins.add(createCoin());
    }
 
}