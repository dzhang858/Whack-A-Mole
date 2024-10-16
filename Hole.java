import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Hole extends Thing
{
  private Image image;
  private boolean occupied;

  public Hole(int x, int y, int w, int h)
  {
    super(x, y, w, h);
    try
    {
      URL url = getClass().getResource("media/hole.png");
      image = ImageIO.read(url);
    }
    catch(Exception e)
    {
    }
  }

  public boolean getOccupied()
  {
    return occupied;
  }

  public void setOccupied()
  {
    occupied = true;
  }

  public void setUnoccupied()
  {
    occupied = false;
  }

  public void draw(Graphics window)
  {
    window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
  }
}
