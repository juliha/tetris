import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julia on 18.05.2014.
 */
public class BlockGenerator {

    public static final int[][] quad = {{1,1}, {1,1}};
    public static final int[][] rect = {{1,1,1,1}};
    public static final int[][] lShape1 = {{1,0,0},{1 ,1, 1}};
    public static final int[][] lShape2 = {{0,0,1},{1 ,1, 1}};
    public static final int[][] tShape = {{0,1,0},{1 ,1, 1}};
    public static final int[][] zShape1 = {{1,1,0},{0 ,1, 1}};
    public static final int[][] zShape2 = {{0,1,1},{1 ,1, 0}};

    public static final List<int[][]> possibleShapes =ImmutableList.of(quad, rect, lShape1, lShape2, tShape, zShape1, zShape2);

    public static int[][] getRandomShape() {
        double high = possibleShapes.size()-1;
        int i = (int) (Math.random() * (high));
        return possibleShapes.get(i);
    }

}
