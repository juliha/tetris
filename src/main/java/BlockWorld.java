import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;


/**
 * Created by julia on 21.04.14.
 */
public class BlockWorld extends JPanel {

  private int width =396;
  private int height = 600;
  private Grid grid;
  private double factor = 396/12.0;
  Color border = Color.LIGHT_GRAY;

  BlockWorld() {
    super();
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
            grid.getRectangles().get(0).moveLeft();
            break;
          case KeyEvent.VK_RIGHT :
            grid.getRectangles().get(0).moveRight();
            break;
          case KeyEvent.VK_UP :
            grid.getRectangles().get(0).rotate();
            break;
          case KeyEvent.VK_DOWN :
            grid.getRectangles().get(0).moveDown();
            break;
        }
        repaint();
      }
    });

    grid.addRectangle(new Rectangle(0, 4, 0, 1, Color.MAGENTA));
    grid.addRectangle(new Rectangle(0,3,0,1, Color.BLUE));


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

    //repaint();
  }



  private void createAndShowGUI() {
    JFrame frame = new JFrame("Bloxx");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);

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
