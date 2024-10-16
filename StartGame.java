import javax.swing.JFrame;
import java.awt.Component;

public class StartGame extends JFrame
{
  private static final int WIDTH = 540;
  private static final int HEIGHT = 570;

  public StartGame()
  {
    super("WHAC-A-MOLE");
    setSize(WIDTH,HEIGHT);

    Game game = new Game();
    ((Component)game).setFocusable(true);

    getContentPane().add(game);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main( String args[] )
  {
    StartGame run = new StartGame();
  }
}
