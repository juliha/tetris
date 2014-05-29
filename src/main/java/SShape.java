import java.awt.*;

/**
 * Created by julia on 29.05.2014.
 */
public class SShape extends AbstractBlock {

    public SShape(int x, int y, int[][] shape) {
        super(x, y, shape);
        color = Color.WHITE;
    }
    public SShape(int x, int y) {
        super(x, y, new int[][] {
                {1,1,0},
                {0,1,1},
                {0,0,0}
        });
        color = Color.WHITE;
    }

    @Override
    public AbstractBlock copyBlock() {
        return new SShape(this.getX(), this.getY(), this.getBlockShape());
    }
}
