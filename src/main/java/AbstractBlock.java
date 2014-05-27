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
    protected int centerX;
    protected int centerY;
    int orientation;
    List<int[][]> blockShapes;
    protected int[][] blockShape;

    protected AbstractBlock(int x, int y,  int centerX, int centerY, List<int[][]> shapes, int orientation) {
        this.x = x;
        this.y = y;
        this.blockShapes = shapes;
        blockShape = shapes.get(0);
        this.orientation = orientation;
    }

    public AbstractBlock copyBlock() {
        List<int[][]> blocks = new ArrayList<int[][]>();
        blocks.addAll(blockShapes);
        return new AbstractBlock(this.x, this.y, this.centerX, this.centerY, blocks, orientation);
    }

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

    public int[][] getSingleBlockShape() {
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

    protected void rotate() {
//        int[][] shape = blockShapes.get(orientation);
//        int width = shape[0].length;
//        int height = shape.length;
//        int[][] newShape = new int[width][height];
//
//        double angle = Math.toRadians(90.0);
//        double x0 = 0.5 *(width-1);
//        double y0 = 0.5 * (height-1);
//
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                double a = x-x0;
//                double b = y-y0;
//                int xNew = (int) (+a * Math.cos(angle) -b * Math.sin(angle) + x0);
//                int yNew = (int) (+a * Math.sin(angle) + b * Math.cos(angle) + y0);
//                System.out.println("new X " +xNew);
//                System.out.println("new Y " + yNew);
//
//                newShape[x][y] = blockShape[xNew][yNew];
//
//            }
//        }
//        blockShape = newShape;
//
//
//        System.out.println(Arrays.deepToString(newShape));


        if (orientation == 3) {
            orientation = 0;
        } else {
            orientation++;
        }


    }
}