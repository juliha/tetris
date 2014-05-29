import java.awt.*;

/**
 * Created by julia on 29.05.2014.
 */
public class SShape2 extends AbstractBlock {

    public SShape2(int x, int y, int[][] shape) {
        super(x, y, shape);
        color = Color.ORANGE;
    }
    public SShape2(int x, int y) {
        super(x, y, new int[][] {
                {0,1,1},
                {1,1,0},
                {0,0,0}
        });
        color = Color.ORANGE;
    }

    @Override
    public AbstractBlock copyBlock() {
        return new SShape2(this.getX(), this.getY(), this.getBlockShape());
    }
}
