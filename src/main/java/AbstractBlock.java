import javax.swing.*;
import java.awt.*;
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
    protected int[][] blockShape;
    Color color =  Color.BLACK;

    protected AbstractBlock(int x, int y,  int[][] shape) {
        this.x = x;
        this.y = y;
        this.blockShape =shape;

    }

    public abstract AbstractBlock copyBlock() ;

    public Color getColor() {
        return color;
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

    public void rotate() {
        final int oldY = blockShape.length;
        final int oldX = blockShape[0].length;
        int[][] newShape = new int[oldX][oldY];
        for (int y = 0; y < oldY; y++) {
            for (int x = 0; x < oldX; x++) {
                newShape[x][oldY-1-y] = blockShape[y][x];
            }
        }
        this.blockShape = newShape;
    }





}