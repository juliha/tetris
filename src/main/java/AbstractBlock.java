import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by julia on 21.04.14.
 */
public class AbstractBlock {

    protected int x;
    protected int y;
    int orientation;
    List<int[][]> blockShapes;
    // protected int[][] blockShape;

    protected AbstractBlock(int x, int y, List<int[][]> shapes, int orientation) {
        this.x = x;
        this.y = y;
        this.blockShapes = shapes;
        this.orientation = orientation;
    }

    public AbstractBlock copyBlock() {
        List<int[][]> blocks = new ArrayList<int[][]>();
        blocks.addAll(blockShapes);
        return new AbstractBlock(this.x, this.y, blocks, orientation);
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
        return blockShapes.get(orientation);
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
        int[][] shape = blockShapes.get(orientation);

        double width = ((int) shape[0].length)/2;
        double height = ((int) shape.length)/2;

        if (orientation % 2 == 0) {
            x = x + (int) (width / 2);
            y = y - (int) (width / 2);
        } else {
            x = x - (int) (height / 2);
            y = y + (int) (height / 2);
        }
        if (orientation == 3) {

            orientation = 0;
        } else {
            orientation++;
        }


    }
}