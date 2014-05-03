import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by julia on 26.04.14.
 */
public class Grid {
 private Integer[][] cells= new Integer[20][12];
  private List<Rectangle> rectangles;

  public Grid() {
    for (int y = 0; y < cells.length; y++) {
      for (int x=0; x < cells[y].length; x++) {
        cells[y][x] = -1;
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


  public void addRectangle(Rectangle rectangle) {
    rectangles.add(rectangle);
  }

  public void safeRectanglePosition(Rectangle rectangle) {
    int index = rectangles.indexOf(rectangle);
    for (int y = (int) rectangle.getyInit();  y < rectangle.getyEnd(); y++) {
      for (int x = (int) rectangle.getxInit(); x < rectangle.getxEnd(); x++) {
        cells[y][x] = index;
      }
    }
  }

  public boolean isOccupied(int x, int y) {
    return this.cells[y][x] != -1;
  }
}

