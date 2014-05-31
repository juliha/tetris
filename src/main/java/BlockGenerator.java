import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julia on 18.05.2014.
 */
public class BlockGenerator {

    public static AbstractBlock getRandomShape() {
        double high = 7;
        int i = (int) (Math.random() * (high));
        System.out.println("case " + i);
        switch (i) {
            case 0:
                return new LShape(4, 0);
            case 1:
                return new TShape(4, 0);
            case 2:
                return new Rectangle(4, 0);
            case 3:
                return new Quad(4, 0);
            case 4:
                return new LShape2(4, 0);
            case 5:
                return new SShape2(4, 0);
            case 6 :
                return new SShape(4, 0);

            default:
                return new LShape(4, 0);
        }
    }

}
