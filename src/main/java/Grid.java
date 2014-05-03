import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by julia on 26.04.14.
 */
public class Grid {
 private Integer[][] cells= new Integer[24][12];
  private List<Rectangle> rectangles;

  public Grid() {
    for (int i = 0; i < cells.length; i++) {
      for (int j=0; j < cells[i].length; j++) {
        cells[i][j] = 0;
      }
    }
    rectangles = new CopyOnWriteArrayList<Rectangle>();
  }

  public List<Rectangle> getRectangles() {
    return rectangles;
  }
  public Integer[][] getCells() {
    return cells;
  }

  public Integer getCell(int i, int j) {
    return cells[i][j];
  }









}

