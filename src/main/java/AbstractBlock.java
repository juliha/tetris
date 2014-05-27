import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by julia on 21.04.14.
 */
abstract class AbstractBlock {

    protected int x;
    protected int y;
    protected int centerX;
    protected int centerY;
    int orientation;
    protected int[][] blockShape;

    protected AbstractBlock(int x, int y,  int centerX, int centerY, int[][] shape, int orientation) {
        this.x = x;
        this.y = y;
        this.blockShape =shape;
        this.orientation = orientation;
    }

    public abstract AbstractBlock copyBlock() ;

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getOrientation() {
        return orientation;
    }

    public int getX() {
        return x-getCenterX();
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y-getCenterY();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getBlockShape() {
        return blockShape;

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

    public abstract void rotate();





}