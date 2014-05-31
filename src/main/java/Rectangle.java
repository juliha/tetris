import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by julia on 21.04.14.
 */
public class Rectangle extends AbstractBlock {


    public Rectangle(int x, int y, int[][] shape ) {
        super(x, y,  shape);
        color = Color.MAGENTA;
    }
    public Rectangle(int x, int y) {
        this(x, y,  new int[][] {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}});
        color = Color.MAGENTA;
    }

    @Override
    public AbstractBlock copyBlock() {
        return new Rectangle(this.getX(), this.getY(),  this.blockShape);
    }



}



