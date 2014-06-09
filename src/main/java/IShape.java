import java.awt.*;

/**
 * Created by julia on 21.04.14.
 */
public class IShape extends AbstractBlock {


    public IShape(int x, int y, int[][] shape) {
        super(x, y,  shape);
        color = Color.MAGENTA;
    }
    public IShape(int x, int y) {
        this(x, y,  new int[][] {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}});
        color = Color.MAGENTA;
    }

    @Override
    public AbstractBlock copyBlock() {
        return new IShape(this.getX(), this.getY(),  this.blockShape);
    }



}



