import java.awt.*;

/**
 * Created by julia on 27.05.2014.
 */
public class LShape2 extends AbstractBlock {

    public LShape2(int x, int y, int[][] shape) {
        super(x, y,  shape);
        color = Color.CYAN;
    }

    public LShape2(int x, int y) {
        super(x, y,  new int[][]{
                {0, 0, 1},
                {1, 1, 1,},
                {0,0,0}});
        color = Color.cyan;
    }

    @Override
    public AbstractBlock copyBlock() {
        return new LShape2(this.getX(), this.getY(),  this.blockShape);
    }
}
