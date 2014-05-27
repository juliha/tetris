import java.util.Arrays;
import java.util.List;

/**
 * Created by julia on 21.04.14.
 */
public class Rectangle extends AbstractBlock {


    public Rectangle(int x, int y, int centerX, int centerY, int[][] shape, int orientation) {
        super(x, y, centerX, centerY, shape, orientation);
    }
    public Rectangle(int x, int y, int orientation) {
        this(x, y, 2, 2, new int[][] {{1,1,1,1}}, orientation);
    }

    @Override
    public AbstractBlock copyBlock() {
        return new Rectangle(this.getX(), this.getY(), this.getCenterX(), this.getCenterY(), this.getBlockShape(), this.getOrientation());
    }

    @Override
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
                centerX -=2;
                centerY +=2;
                break;
            case 2 :
                centerX+=2;
                centerY-=2;
                break;
            case 3 :
                centerX -= 2;
                centerY +=2;
                break;
            case 4 :
                orientation =0;
                centerX +=2;
                centerY -=2;
                break;
        }

            System.out.println(Arrays.deepToString(newShape));
            blockShape = newShape;
        }

}



