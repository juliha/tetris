import javax.swing.*;
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
        x++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveDown() {
        this.y++;
    }

    protected void rotate() {
       //TODO
    }


}
