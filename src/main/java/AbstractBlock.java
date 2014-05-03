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
    double height = yEnd-yInit;

    double centerX = xInit+width/2;
    double centerY = yInit+(height/2);

    double xInitNew = centerX-(height/2) ;
    double xEndNew = xInitNew+height;

    double yInitNew =  centerY-(width/2);
    double yEndNew = yInitNew+width;


    //TODO check if this position is still free

    if (xInitNew  >= 0 && xEndNew <= 12
        && yEndNew <= 21) {
      xInit = xInitNew;
      xEnd = xInit + height;
      yInit = yInitNew;
      yEnd = yInit + width;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AbstractBlock that = (AbstractBlock) o;

    if (Double.compare(that.xEnd, xEnd) != 0) return false;
    if (Double.compare(that.xInit, xInit) != 0) return false;
    if (Double.compare(that.yEnd, yEnd) != 0) return false;
    if (Double.compare(that.yInit, yInit) != 0) return false;
    if (color != null ? !color.equals(that.color) : that.color != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(xInit);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(xEnd);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(yInit);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(yEnd);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (color != null ? color.hashCode() : 0);
    return result;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  //  @Override
//  public void paintComponent(Graphics g) {
//    Graphics2D g2d = (Graphics2D) g;
//  }



}
