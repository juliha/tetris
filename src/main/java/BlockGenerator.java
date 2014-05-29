import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julia on 18.05.2014.
 */
public class BlockGenerator {

    public static final int[][] quad0 = {{1,1}, {1,1}};
    public static final int[][] quad1 = {{1,1}, {1,1}};
    public static final int[][] quad2 = {{1,1}, {1,1}};
    public static final int[][] quad3 = {{1,1}, {1,1}};
    public static final List<int[][]> quad = ImmutableList.of(quad0, quad1, quad2, quad3);
    public static final int[][] rect0 = {{1,1,1,1}};
    public static final int[][] rect1 = {{1},{1},{1},{1}};
    public static final int[][] rect2 = {{1,1,1,1}};
    public static final int[][] rect3 = {{1},{1},{1},{1}};
    public static final List<int[][]> rect = ImmutableList.of(rect0, rect1, rect2, rect3);
    public static final int[][] lShape10 = {{1,0,0},{1 ,1, 1}};
    public static final int[][] lShape11 = {{1,1},{1,0} ,{1,0}};
    public static final int[][] lShape12 = {{1,1,1},{0 ,0, 1}};
    public static final int[][] lShape13 = {{0,1},{0,1},{1, 1}};
    public static final List<int[][]> lShape1 = ImmutableList.of(lShape10, lShape11, lShape12, lShape13);

    public static final int[][] lShape20 = {{0,0,1},{1 ,1, 1}};
    public static final int[][] lShape21 = {{1,0}, {1,0}, {1,1}};
    public static final int[][] lShape22 = {{1,1,1},{1 ,0, 0}};
    public static final int[][] lShape23 = {{1,1},{0 ,1}, {0, 1}};
    public static final List<int[][]> lShape2 = ImmutableList.of(lShape20, lShape21, lShape22, lShape23);

    public static final int[][] tShape0 = {{0,1,0},{1 ,1, 1}};
    public static final int[][] tShape1 = {{1,0}, {1,1},{1,0}};
    public static final int[][] tShape2 = {{1,1,1},{0 ,1, 0}};
    public static final int[][] tShape3 = {{0,1},{1,1},{0 ,1}};
    public static final List<int[][]> tShape = ImmutableList.of(tShape0, tShape1, tShape2, tShape3);

    public static final int[][] zShape10 = {{1,1,0},{0 ,1, 1}};
    public static final int[][] zShape11 = {{0,1}, {1,1},{1 ,0}};
    public static final int[][] zShape12 = {{1,1,0},{0 ,1, 1}};
    public static final int[][] zShape13 = {{0,1}, {1,1} ,{1, }};
    public static final List<int[][]> zShape1 = ImmutableList.of(zShape10, zShape11, zShape12, zShape13);

    public static final int[][] zShape20 = {{0,1,1},{1 ,1, 0}};
    public static final int[][] zShape21 = {{0,1}, {1,1} ,{1, 0}};
    public static final int[][] zShape22 = {{0,1,1},{1 ,1, 0}};
    public static final int[][] zShape23 = {{0,1}, {1,1} ,{0, 1}};

    public static final List<int[][]> zShape2 = ImmutableList.of(zShape20, zShape21, zShape22, zShape23);


    public static AbstractBlock getRandomShape() {
        double high = 2;
        int i = (int) (Math.random() * (high));
        switch(i) {
            case 0 :
                return new LShape(4,0);
            case 1 :
                return  new TShape(4,0);
            case 2 :
                return new Rectangle(4,0);
            default:
                return new LShape(4,0);
        }
    }

}
