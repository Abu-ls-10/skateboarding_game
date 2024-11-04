import java.awt.*;
public abstract class Obstacle 
{
    public abstract void update();

    public abstract void draw(Graphics g);

    public abstract Rectangle getBounds();

    public abstract boolean isOutOfScreen();

    public Obstacle get(int i) {
        return null;
    }
}