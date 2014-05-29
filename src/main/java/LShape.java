import java.awt.*;

/**
 * Created by julia on 27.05.2014.
 */
public class LShape extends AbstractBlock {

    public LShape(int x, int y, int[][] shape) {
        super(x, y,  shape);
        color = Color.BLUE;
    }

    public LShape(int x, int y) {
        super(x, y,  new int[][]{{1, 0, 0}, {1, 1, 1,}, {0,0,0}});
        color = Color.BLUE;
    }

    @Override
    public AbstractBlock copyBlock() {
        return new LShape(this.getX(), this.getY(),  this.blockShape);
    }
}
