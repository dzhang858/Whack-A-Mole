import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements KeyListener, Runnable {


  // instance variables
  private Hammer hammer;
  private Grid grid;

  private int score;
  private int highScore;
  private boolean gameOver;
  private boolean gameStart;
  private boolean lev1;
  private boolean lev2;
  private boolean lev3;
  private boolean specialMode;
  private int bombSpeed;
  private int moleSpeed;
  private int moleRemoveSpeed;
  private int bombRemoveSpeed;
  private String levString;

  private BufferedImage back;
  private boolean[] keys;

  private long currentTime;

  public Game() {
    // instantiate objects
    hammer = new Hammer(100, 100, 40, 62, 2, 1);
    grid = new Grid(4);

    currentTime = System.currentTimeMillis();

    score = 0;
    highScore = 0;
    gameOver = false;
    gameStart = true;
    lev1 = false;
    lev2 = false;
    lev3 = false;
    specialMode = false;
    bombSpeed = 0;
    moleSpeed = 0;
    moleRemoveSpeed = 0;
    bombRemoveSpeed = 0;
    levString = "";

    keys = new boolean[5];
    setBackground(Color.LIGHT_GRAY);
    setVisible(true);
    new Thread(this).start();
    addKeyListener(this);
  }

  public void update(Graphics window) {
    paint(window);
  }

  public void paint(Graphics window) {
    Graphics2D twoDGraph = (Graphics2D) window;
    if (back == null)
      back = (BufferedImage) (createImage(getWidth(), getHeight()));
    Graphics graphToBack = back.createGraphics();
    graphToBack.setColor(Color.LIGHT_GRAY);
    graphToBack.fillRect(0, 0, 540, 570);

    if (gameStart) {
      graphToBack.setColor(Color.BLACK);
      graphToBack.drawString("Press 1 for Level 1", 225, 160);
      graphToBack.drawString("Press 2 for Level 2", 225, 185);
      graphToBack.drawString("Press 3 for Level 3", 225, 210);
      graphToBack.drawString("Press y for Santa Mode", 210, 235);

      if (keys[1]) {
        lev1 = true;
        gameStart = false;
      } else lev1 = false;
      if (keys[2]){
        lev2 = true;
        gameStart = false;
      } else lev2 = false;
      if (keys[3]) {
        lev3 = true;
        gameStart = false;
      } else lev3 = false;
      if (keys[4]){
        if (!specialMode) {
          specialMode = true;
          keys[4] = false;
        }
        else {
          specialMode = false;
          keys[4] = false;
        }
      }
      if (specialMode){
        graphToBack.drawString("yes", 380, 235);
      }
      else{
        graphToBack.drawString("no", 380, 235);
      }
    }

    if (!gameOver && !gameStart) {
      graphToBack.setColor(Color.BLACK);
      graphToBack.drawString("Score: " + score, 15, 15);
      graphToBack.drawString(levString, 250,75);

      if (specialMode) grid.setSpec(true); else grid.setSpec(false);

      if (lev1) {
        hammer.setXSpeed(3);
        hammer.setYSpeed(2);
        bombSpeed = 3000;
        moleSpeed = 2000;
        moleRemoveSpeed = 2000;
        bombRemoveSpeed = 1000;
        lev1 = false;
        levString = "Level 1";
      }

      //level 2
      if (lev2) {
        hammer.setXSpeed(4);
        hammer.setYSpeed(3);
        bombSpeed = 2000;
        moleSpeed = 2000;
        moleRemoveSpeed = 1500;
        bombRemoveSpeed = 2000;
        lev2 = false;
        levString = "Level 2";
      }

      //level 3
      if (lev3) {
        hammer.setXSpeed(5);
        hammer.setYSpeed(4);
        bombSpeed = 1500;
        moleSpeed = 1500;
        moleRemoveSpeed = 1000;
        bombRemoveSpeed = 2500;
        lev3 = false;
        levString = "Level 3";
      }


      if (keys[0]) {
        hammer.pressed();
        for (int i = 0; i < grid.getMoles().size(); i++){
          if (hammer.didOverlap(grid.getMoles().get(i))){
            if (!grid.getMoles().get(i).getSquishploded()) {
              grid.getMoles().get(i).setSquishploded();
              score++;
            }
          }
        }
        for (int i = 0; i < grid.getBombs().size(); i++){
          if (hammer.didOverlap(grid.getBombs().get(i))){
            if (!grid.getBombs().get(i).getSquishploded()) {
              grid.getBombs().get(i).setSquishploded();
            }
          }
        }
      }
      else {
        hammer.released();
      }
      for (int i = 0; i < grid.getMoles().size(); i++){
        if (grid.getMoles().get(i).getSquishploded() && grid.getMoles().get(i).getSquishplodeTime() >= 50) {
          grid.removeMole(i);
        }
      }
      for (int i = 0; i < grid.getBombs().size(); i++){
        if (grid.getBombs().get(i).getSquishploded() && grid.getBombs().get(i).getSquishplodeTime() >= 50) {
          grid.removeBomb(i);
          gameOver = true;
        }
      }


      // change direction if hammer leaves the grid
      if (hammer.getX() < 100 || hammer.getX() + hammer.getWidth() > 455){
        hammer.setXSpeed(-hammer.getXSpeed());
      }
      if (hammer.getY() < 100 || hammer.getY() + hammer.getHeight() > 455){
        hammer.setYSpeed(-hammer.getYSpeed());
      }

      // randomly add mole and bombs
      if (System.currentTimeMillis() - currentTime > (int)(Math.random()*moleSpeed + 1000)) {
        grid.addMole();
        currentTime = System.currentTimeMillis();
      }

      if (System.currentTimeMillis() - currentTime > (int)(Math.random()*bombSpeed + 1000)) {
        grid.addBomb();
        currentTime = System.currentTimeMillis();
      }

      // remove moles and bombs
      for (int i = 0; i < grid.getMoles().size(); i++){
        if (grid.getMoles().get(i).getTime() >= moleRemoveSpeed){
          grid.removeMole(i);
        }
      }
      for (int j = 0; j < grid.getBombs().size(); j++){
        if (grid.getBombs().get(j).getTime() >= bombRemoveSpeed){
          grid.removeBomb(j);
        }
      }

      // draw methods
      grid.draw(graphToBack);
      hammer.move();
      hammer.draw(graphToBack);
    }

    if (gameOver) {
      if (score > highScore) {
        highScore = score;
      }
      graphToBack.setColor(Color.BLACK);
      graphToBack.drawString("Score: " + score, 250, 190);
      graphToBack.drawString("High Score: " + highScore, 240, 215);
      graphToBack.drawString("Press 1 for Level 1", 225, 240);
      graphToBack.drawString("Press 2 for Level 2", 225, 265);
      graphToBack.drawString("Press 3 for Level 3", 225, 290);
      graphToBack.drawString("Press y for Santa mode", 210, 320);

      if (keys[1])
      {
        gameOver = false;
        score = 0;
        lev1 = true;
      }
      if (keys[2])
      {
        gameOver = false;
        score = 0;
        lev2 = true;
      }
      if (keys[3])
      {
        gameOver = false;
        score = 0;
        lev3 = true;
      }
      if (keys[4]){
        if (!specialMode) {
          specialMode = true;
          keys[4] = false;
        }
        else {
          specialMode = false;
          keys[4] = false;
        }
      }
      if (specialMode){
        graphToBack.drawString("yes", 380, 320);
      }
      else{
        graphToBack.drawString("no", 380, 320);
      }
    }

    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_1)
    {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_2)
    {
      keys[2] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_3)
    {
      keys[3] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_Y)
    {
      keys[4] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_1)
    {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_2)
    {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_3)
    {
      keys[3] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_Y)
    {
      keys[4] = false;
    }
    repaint();
  }

  public void keyTyped(KeyEvent e){}

  public void run() {
    try {
      while (true) {
        Thread.currentThread().sleep(5);
        repaint();
      }
    } 
    catch (Exception e) {
    }
  }

}
