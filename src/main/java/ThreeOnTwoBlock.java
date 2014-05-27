import java.util.Arrays;

/**
 * Created by julia on 27.05.2014.
 */
abstract class ThreeOnTwoBlock extends AbstractBlock {
    public ThreeOnTwoBlock(int x, int y, int centerX, int centerY, int[][] shape, int orientation) {
        super(x, y, centerX, centerY, shape, orientation);
    }

    public ThreeOnTwoBlock(int x, int y, int orientation) {
        this(x, y, 1, 1, new int[][]{{1, 0, 0}, {1, 1, 1}}, orientation);
    }


    public abstract AbstractBlock copyBlock() ;

    public void rotate() {
        int[][] newShape = new int[getBlockShape()[0].length][getBlockShape().length];
        final int M = getBlockShape().length;
        final int N = getBlockShape()[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                newShape[c][M-1-r] = getBlockShape()[r][c];
            }
        }
        orientation++;
        switch (orientation) {
            case 1 :
                centerX--;
                break;
            case 2 :
                centerX++;
                centerY--;
                break;
            case 3 :
                centerY++;
                break;
            case 4 :
                orientation =0;
                break;
        }

        System.out.println(Arrays.deepToString(newShape));
        blockShape = newShape;
    }
}
