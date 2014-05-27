/**
 * Created by julia on 27.05.2014.
 */
public class TShape extends ThreeOnTwoBlock {
    public TShape(int x, int y, int centerX, int centerY, int[][] shape, int orientation) {
        super(x, y, centerX, centerY, shape, orientation);
    }

    public TShape(int x, int y, int orientation) {
        super(x, y, orientation);
    }

    @Override
    public AbstractBlock copyBlock() {
        return new TShape(this.getX(),this.getY(), this.getCenterX(), this.getCenterY(), this.blockShape, this.orientation);
    }
}
