import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julia on 18.05.2014.
 */
public class BlockGenerator {




    public static final int[][] lShape20 = {{0,0,1},{1 ,1, 1}};
    public static final int[][] lShape21 = {{1,0}, {1,0}, {1,1}};
    public static final int[][] lShape22 = {{1,1,1},{1 ,0, 0}};
    public static final int[][] lShape23 = {{1,1},{0 ,1}, {0, 1}};
    public static final List<int[][]> lShape2 = ImmutableList.of(lShape20, lShape21, lShape22, lShape23);





    public static final int[][] zShape20 = {{0,1,1},{1 ,1, 0}};
    public static final int[][] zShape21 = {{0,1}, {1,1} ,{1, 0}};
    public static final int[][] zShape22 = {{0,1,1},{1 ,1, 0}};
    public static final int[][] zShape23 = {{0,1}, {1,1} ,{0, 1}};

    public static final List<int[][]> zShape2 = ImmutableList.of(zShape20, zShape21, zShape22, zShape23);


    public static AbstractBlock getRandomShape() {
        double high = 4;
        int i = (int) (Math.random() * (high));
        switch(i) {
            case 0 :
                return new LShape(4,0);
            case 1 :
                return  new TShape(4,0);
            case 2 :
                return new Rectangle(4,0);
            case 3 :
                return new SShape(4, 0);
            case 4:
                return new Quad(4, 0);
            default:
                return new LShape(4,0);
        }
    }

}
