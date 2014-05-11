import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.Timer;


/**
 * Created by julia on 21.04.14.
 */
public class BlockWorld extends JPanel {

  private int width =396;
  private int height = 660;
  private Grid grid;
  private double factor = 396/12.0;
  Color border = Color.LIGHT_GRAY;
  private int speed;
  java.util.Timer timer;


  BlockWorld() {
    super();

    timer = new Timer();
    this.setPreferredSize(new Dimension(width, height));
    this.setFocusable(true);
    grid = new Grid();
    final Graphics2D g2d = (Graphics2D) this.getGraphics();
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent keyEvent) {
        System.err.println("Key typed "+ keyEvent.getKeyCode());
      }
      @Override
      public void keyPressed(KeyEvent keyEvent) {
        System.err.println("Key pressed "+ keyEvent.getKeyCode());
      }
      @Override
      public void keyReleased(KeyEvent keyEvent) {
        int code =keyEvent.getKeyCode();
        switch (code) {
          case KeyEvent.VK_LEFT :
            grid.getLastRectangle().moveLeft();
            break;
          case KeyEvent.VK_RIGHT :
            grid.getLastRectangle().moveRight();
            break;
          case KeyEvent.VK_UP :
            grid.getLastRectangle().rotate();
            break;
          case KeyEvent.VK_DOWN :
            grid.getLastRectangle().moveDown();
            break;
        }
        repaint();
      }
    });
    speed=2;
  }

  public void runGame() {
    Thread gameThread = new Thread() {
      public void run() {
          int index =0;
        Rectangle rectangle = new Rectangle(0, 4, 0, 1, Color.MAGENTA);
        grid.addRectangle(rectangle);
          index++;
        while (true) {
          update();
          repaint();
          if (rectangle.getyEnd() >= 20) {
            rectangle = new Rectangle(0, 4, 0, 1, Color.blue);
            grid.addRectangle(rectangle);
          }
          try {
            Thread.sleep(2*1000);
          } catch (InterruptedException ex) {}
        }
      }
    };
    gameThread.start();  // Invoke GaemThread.run()
  }
  public void moveCurrentBlock() {
    grid.getLastRectangle().moveDown();
  }




  public void update() {
    moveCurrentBlock();
  }


  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    Graphics2D g2d = (Graphics2D) graphics;
    for (Rectangle rec : grid.getRectangles()) {
      g2d.setColor(rec.getColor());
       for (double i = rec.getxInit(); i< rec.getxEnd(); i++) {
          System.err.println(i);
          double x = i*factor;
          double y = rec.getyInit()*factor;
          double width = factor;
           double height = (rec.getyEnd()-rec.getyInit())*factor;
           g2d.fill(new Rectangle2D.Double(x,y,width,height));
       }
    }

  }



  private void createAndShowGUI() {
    JFrame frame = new JFrame("Bloxx");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    runGame();

    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        BlockWorld grid = new BlockWorld();
        grid.createAndShowGUI();
      }
    });
  }
}
