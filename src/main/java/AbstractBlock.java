import java.util.Arrays;

/**
 * Created by julia on 21.04.14.
 */
public class AbstractBlock {

    protected int x;
    protected int y;
    protected Integer[][] blockShape;

    protected AbstractBlock(int x, int y, Integer[][] shape) {
        this.x = x;
        this.y = y;
        this.blockShape = shape;
    }

    public AbstractBlock copyBlock() {
        return new AbstractBlock(this.x, this.y, this.blockShape.clone());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Integer[][] getBlockShape() {
        return blockShape;
    }

    public void setBlockShape(Integer[][] blockShape) {
        this.blockShape = blockShape;
    }

    public void moveRight() {
        this.x++;
    }

    public void moveLeft() {
        this.x--;
    }

    public void moveDown() {
        this.y++;
    }

    protected void rotate() {
//    double width = xEnd-xInit;
//    double height = yEnd-yInit;
//
//    double centerX = xInit+width/2;
//    double centerY = yInit+(height/2);
//
//    double xInitNew = centerX-(height/2) ;
//    double xEndNew = xInitNew+height;
//
//    double yInitNew =  centerY-(width/2);
//    double yEndNew = yInitNew+width;
//
//
//    if (xInitNew  >= 0 && xEndNew <= 12
//        && yEndNew <= 20) {
//      xInit = (int) Math.ceil(xInitNew);
//      xEnd = (int) Math.ceil(xInit + height);
//      yInit = (int) Math.ceil(yInitNew);
//      yEnd = (int) Math.ceil(yInit + width);
//    }
  }


}
