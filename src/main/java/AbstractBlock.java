import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by julia on 21.04.14.
 */
public class AbstractBlock {

    protected int x;
    protected int y;
    protected int[][] blockShape;

    protected AbstractBlock(int x, int y, int[][] shape) {
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

    public int[][] getBlockShape() {
        return blockShape;
    }

    public void setBlockShape(int[][] blockShape) {
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
        if (blockShape.length >= 0) {
            int[][] tmp = new int[blockShape[0].length][blockShape.length];

            for (int i = 0; i < blockShape.length; i++) {
                for (int j = 0; j < blockShape[i].length; j++) {
                    tmp[j][i] = blockShape[i][j];
                }
            }
            System.out.println(Arrays.deepToString(tmp));
            blockShape = tmp;

        }
    }


}
