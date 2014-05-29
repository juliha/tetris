import java.awt.*;

/**
 * Created by julia on 27.05.2014.
 */
public class TShape extends AbstractBlock {


    public TShape(int x, int y, int[][] shape) {
        super(x, y, shape);
        color = Color.cyan;
    }
    public TShape(int x, int y) {
        super(x, y, new int[][]{
                {0, 1, 0},
                {1, 1, 1},
                {0, 0, 0}});
        color = Color.cyan;
    }
    @Override
    public AbstractBlock copyBlock() {
        return new TShape(this.getX(),this.getY(), this.blockShape);
    }
}
