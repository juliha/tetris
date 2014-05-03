import javax.swing.*;
import java.awt.*;

/**
 * Created by julia on 21.04.14.
 */
public abstract class AbstractBlock extends JComponent {

  protected double xInit;
  protected double xEnd;
  protected double yInit;
  protected double yEnd;
  protected Color color;

  protected double speed;

  protected AbstractBlock(int xInit, int xEnd, int yInit, int yEnd, Color color) {
    this.color = color;
    this.yEnd = yEnd;
    this.yInit = yInit;
    this.xEnd = xEnd;
    this.xInit = xInit;
  }

  public double getxInit() {
    return xInit;
  }

  public double getxEnd() {
    return xEnd;
  }

  public double getyInit() {
    return yInit;
  }

  public double getyEnd() {
    return yEnd;
  }

  public Color getColor() {
    return color;
  }

  public double getSpeed() {
    return speed;
  }

  protected void moveLeft() {
    if (xInit - 1 >= 0) {
      xInit--;
      xEnd--;
    }
  }

  protected void moveRight() {
    if (xEnd <= 11) {
      xInit++;
      xEnd++;
    }
  }
  protected void moveDown() {
    if (yEnd <= 19) {
      yInit++;
      yEnd++;
      System.out.println("yend " +yEnd);
    }
  }

  protected void rotate() {
    double width = xEnd-xInit;
    System.err.println("width "+width);
    double height = yEnd-yInit;
    System.err.println("height " + height);
    double centerX = xInit+width/2;
    double centerY = yInit+(height/2);

    double xInitNew = centerX-(height/2) ;
    double xEndNew = xInitNew+height;

    double yInitNew =  centerY-(width/2);
    double yEndNew = yInitNew+width;




    xInit = xInitNew;
    xEnd = xInit +height;
    yInit =  yInitNew;
    yEnd =  yInit+width;
    System.err.println(xInit +" "+xEnd +" " +yInit +" "+yEnd);







  }

//  @Override
//  public void paintComponent(Graphics g) {
//    Graphics2D g2d = (Graphics2D) g;
//  }



}
