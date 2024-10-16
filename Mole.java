import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Mole extends Thing
{

  private Image image;
  private int time;
  private int squishplodeTime;
  private boolean squishploded;

  public Mole(int x, int y, int w, int h, boolean b)
  {
    super(x, y, w, h);
    try
    {
      if (!b){
        URL url = getClass().getResource("media/mole.png");
        image = ImageIO.read(url);
      }
      else{
        URL url = getClass().getResource("media/santamole.png");
        image = ImageIO.read(url);
      }
    }
    catch(Exception e)
    {
    }
    time = 0;
    squishplodeTime = 0;
    squishploded = false;
  }

  public int getTime()
  {
    return time;
  }


  public void setSquishploded()
  {
    squishploded = true;
    setY(getY() + 34);
    setHeight(40);
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
    window.drawImage(image, getX(), getY(), getWidth(), getHeight(),null);
    time += 5;
    if (squishploded) {
      squishplodeTime += 5;
    }
  }
}
