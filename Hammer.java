import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Hammer extends Thing
{
  private Image image;
  private int xSpeed;
  private int ySpeed;

  public Hammer(int x, int y, int w, int h, int xs, int ys)
  {
    super(x, y, w, h);
    xSpeed = xs;
    ySpeed = ys;
    try
    {
      URL url = getClass().getResource("media/hammer3.png");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
    }
  }

  public void setXSpeed(int s)
  {
    xSpeed = s;
  }

  public int getXSpeed() 
  {
    return xSpeed;
  }

  public void setYSpeed(int s)
  {
    ySpeed = s;
  }

  public int getYSpeed()
  {
    return ySpeed;
  }

  public void pressed()
  {
    try
    {
      URL url = getClass().getResource("media/hammer4.png");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
    }
  }

  public void released()
  {
    try
    {
      URL url = getClass().getResource("media/hammer3.png");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
    }
  }

  public void move()
  {
    setY(getY() + ySpeed);
    setX(getX() + xSpeed);
  }

  public void draw(Graphics window)
  {
    window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
  }
}
