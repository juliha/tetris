/**
 * Created by julia on 27.05.2014.
 */
public class LShape extends ThreeOnTwoBlock {

    public LShape(int x, int y, int centerX, int centerY, int[][] shape, int orientation) {
        super(x, y, centerX, centerY, shape, orientation);
    }

    public LShape(int x, int y, int orientation) {
        this(x, y, 1, 1, new int[][]{{1, 0, 0}, {1, 1, 1}}, orientation);
    }

    @Override
    public AbstractBlock copyBlock() {
        return new LShape(this.getX(), this.getY(), this.getCenterX(), this.getCenterY(), this.blockShape, this.orientation);
    }
}
