import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Bomb extends Thing
{

  private Image image;
  private int time;
  private int squishplodeTime;
  private boolean squishploded;
  private boolean spec;

  public Bomb(int x, int y, int w, int h, boolean b)
  {
    super(x, y, w, h);
    time = 0;
    spec = b;
    try
    {
      if (!b){
        URL url = getClass().getResource("media/bomb.png");
        image = ImageIO.read(url);
      }
      else{
        URL url = getClass().getResource("media/icebomb.png");
        image = ImageIO.read(url);
      }
    }
    catch(Exception e)
    {
    }
    squishplodeTime = 0;
    squishploded = false;
  }

  public int getTime() {
    return time;
  }

  public void setSquishploded()
  {
    squishploded = true;
    // insert explosion image
    try
      {
        if (!spec){
          URL url = getClass().getResource("media/bomb2.png");
          image = ImageIO.read(url);
        }
        else{
          URL url = getClass().getResource("media/icebomb2.png");
          image = ImageIO.read(url);
        }
      }
      catch(Exception e)
      {
      }
  }

  public boolean getSquishploded()
  {
    return squishploded;
  }

  public int getSquishplodeTime()
  {
    return squishplodeTime;
  }

  public void draw(Graphics window)
  {
    window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
    time += 5;
    if (squishploded){
      squishplodeTime += 5;
    }
  }
}
