import java.awt.*;

/**
 * Created by julia on 29.05.2014.
 */
public class Quad extends AbstractBlock {
    protected Quad(int x, int y, int[][] shape) {
        super(x, y, shape);
        color = Color.YELLOW;
    }
    public Quad(int x, int y) {
        this(x, y, new int[][]{
                {1, 1},
                {1, 1}
        });
        color = Color.YELLOW;
    }

    @Override
    public AbstractBlock copyBlock() {
        return new Quad(this.getX(), this.getY(), this.getBlockShape());
    }
    @Override
    public void rotate() {
        return;
    }
}
