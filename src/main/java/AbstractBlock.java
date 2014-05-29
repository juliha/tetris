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
    protected int[][] blockShape;

    protected AbstractBlock(int x, int y,  int[][] shape) {
        this.x = x;
        this.y = y;
        this.blockShape =shape;

    }

    public abstract AbstractBlock copyBlock() ;


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
        int[][] newShape = new int[blockShape[0].length][blockShape.length];
        final int M = blockShape.length;
        final int N = blockShape[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                newShape[c][M-1-r] = blockShape[r][c];
            }
        }
        this.blockShape = newShape;
    }





}