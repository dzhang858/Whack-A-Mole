import java.awt.Color;
import java.awt.Graphics;

public abstract class Thing
{
  private int xPos;
  private int yPos;
  private int width;
  private int height;

  public Thing(int x, int y, int w, int h) 
  {
    xPos = x;
    yPos = y;
    width = w;
    height = h;
  }

  public boolean didOverlap(Thing other)
  {
  if (getY() + getHeight() < other.getY() + 32 || getY() + 32 > other.getY() + other.getHeight())
    return false;
  if (getX() + getWidth() < other.getX() - 20 || getX() - 20 > other.getX() + other.getWidth())
    return false;
  return true;
  }

  public abstract void draw(Graphics window);

  public void setPos( int x, int y)
  {
    xPos = x;
    yPos = y;
  }

  public void setX(int x)
  {
    xPos = x;
  }

  public void setY(int y)
  {
    yPos = y;
  }

  public int getX()
  {
    return xPos;
  }

  public int getY()
  {
    return yPos;
  }

  public void setWidth(int w)
  {
    width = w;
  }

  public void setHeight(int h)
  {
    height = h;
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }
}
