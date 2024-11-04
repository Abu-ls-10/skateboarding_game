// Imports necessary modules and classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JPanel;

// GameScreen class inherits from JPanel functioning with Runnable and KeyListener interfaces
public class GameScreen extends JPanel implements Runnable, KeyListener{
    
    // Declares values to remain constant throughout the game
    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;

    // Instance variables of GameScreen object
    private Skateboard skateboarder;
    private ObstacleHandler obstacleHandler;
    private CoinCollector coinCollector;
    private Thread thread;
    private boolean isKeyPressed;
    private int gameState = START_GAME_STATE;
    private BufferedImage gameOverImage;

    /**
     * Initializes a GameScreen object as window panel
     * Precondition: Instance variables must be declared.
     * Postcondition: Instance variables skateboarder, obstacleHandler, coinCollector, thread, isKeyPressed, and gameOverImage are initialized.
     */
    public GameScreen()
    {
        skateboarder = new Skateboard();
        skateboarder.setSpeedX(8);
        gameOverImage = Resources.getResourcesImage("game_over.png");
        obstacleHandler = new ObstacleHandler(skateboarder);
        coinCollector = new CoinCollector(skateboarder);
    }

    // Starts trail of events (thread) initiated by Runnable
    public void startGame() 
    {
        thread = new Thread(this);
        thread.start();
    }

    // Updates all game objects, game rendering, and game states
    public void gameUpdate() 
    {
        if (gameState == GAME_PLAYING_STATE) 
        {
            skateboarder.update();
            obstacleHandler.update();
            coinCollector.update();
            
            if (obstacleHandler.isCollision()) 
            {
                gameState = GAME_OVER_STATE;
                skateboarder.crash(true);
            }

            else if (coinCollector.isCollected())
            {
                skateboarder.addScore();
            }
        }
    }

    /**
     * Draws graphics of all sub-objects in GameScreen object
     * Precondition: All instance variables and sub-methods must be initialized.
     * Postcondition: Graphics of game rendered and drawn according to their game states
     * 
     * @param g -Graphics to be rendered for Skateboard object
     */ 
    public void paint(Graphics g) 
    {
        g.setColor(Color.LIGHT_GRAY); //decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());
        switch (gameState) 
        {
            case START_GAME_STATE:
                skateboarder.draw(g);
                break;

            case GAME_PLAYING_STATE:
                coinCollector.draw(g);

            case GAME_OVER_STATE:
                obstacleHandler.draw(g);
                skateboarder.draw(g);
                g.setColor(Color.BLACK);
                g.drawString("Score " +skateboarder.score, 350, 20);

                if (gameState == GAME_OVER_STATE) 
                {
                    g.drawImage(gameOverImage, 150, 50, null);
                }
                break;
        }
    }

    /**
     * Implements interface Runnable to create thread, 
     * Precondition: GameScreen must be initialized and implement Runnable
     * Postcondition: Starts thread and causes all GameScreen sub-object methods to be assessed and executed
     */
    @Override
    public void run() 
    {
        int FPS = 100;
        long msPerFrame = 1000 * 1000000 / FPS;
        long lastTime = 0;
        long elapsed;

        int msSleep;
        int nanoSleep;
        long endProcessGame;
        long lag = 0;

        // Game loop starts
        while (true) 
        {
            gameUpdate();
            repaint();
            endProcessGame = System.nanoTime();
            elapsed = (lastTime + msPerFrame - System.nanoTime());
            msSleep = (int) (elapsed / 1000000);
            nanoSleep = (int) (elapsed % 1000000);

            if (msSleep <= 0) 
            {
                lastTime = System.nanoTime();
                continue;
            }
            try 
            {
                Thread.sleep(msSleep, nanoSleep);
            } 
            
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            lastTime = System.nanoTime();
        }
    }

    /**
     * Invoked when a key has been pressed.
     * Precondition: Game states must be defined
     * Postcondition: KeyEvents are accepted as input functionality for GameScreen object
     * 
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (!isKeyPressed) 
        {
            isKeyPressed = true;
            switch (gameState) 
            {
                // Starts game when user hits enter
                case START_GAME_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                    {
                        gameState = GAME_PLAYING_STATE;
                    }
                    break;

                // During gameplay, skateboarder jumps at space, moves right when right-key pressed, and moves left when left-key pressed    
                case GAME_PLAYING_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) 
                    {
                        skateboarder.jump();
                    }
                    
                    else if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
                    {
                        skateboarder.right();
                    }
                    
                    else if (e.getKeyCode() == KeyEvent.VK_LEFT) 
                    {
                        skateboarder.left();
                    }
                    break;

                // Score is recorded and appended into scores.txt file, errors are handled, and game restarts if user hits 'R' key
                case GAME_OVER_STATE:

                    int userScore = skateboarder.getScore();
                    try (FileWriter writer = new FileWriter("scores.txt", true))
                    {
                        BufferedWriter bWriter = new BufferedWriter(writer);
                        PrintWriter printer = new PrintWriter(bWriter);

                        printer.println(userScore);

                        printer.close();
                        bWriter.close();
                        writer.close();
                    }
                    
                    catch (IOException l) 
                    {
                        System.out.println("File Error: " +l.getMessage());
                    }

                    if (e.getKeyCode() == KeyEvent.VK_R) 
                    {
                        gameState = GAME_PLAYING_STATE;
                        resetGame();
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        // TODO Auto-generated method stub
    }

    /**
     * Clears and restarts game 
     * Precondition: GameScreen object with skateboarder, obstacleHandler, and coinCollector must be initialized
     * Postcondition: Game rendering is cleared and reset
     */
    private void resetGame() 
    {
        obstacleHandler.reset();
        skateboarder.crash(false);
        skateboarder.reset();
    }
}