import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * Created by julia on 27.05.2014.
 */
public class RotationDemo extends JComponent {
    
    private int[][] shape;

    private int x;
    private int y;
    int orientation=0;
    int[][] grid = new int[10][10];
    private static final int factor = 20;
    
    public RotationDemo () {
        this.setPreferredSize(new Dimension(10*factor, 10*factor));
        this.setFocusable(true);
        //shape = new int[][]{{1, 0, 0}, {1, 1, 1}};
       // shape = new int[][]{{0, 0, 1}, {1, 1, 1}};
        shape = new int[][]{{0, 1, 0}, {1, 1, 1},{0, 0, 0}};
//        shape = new int[][]{{1, 0, 0}, {1, 1, 1,}, {0,0,0}};
//        shape = new int[][]{{0,0,0,0},{1, 1, 1,1}, {0,0,0,0},{0,0,0,0} };

        x=4;
        y=4;

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int code = keyEvent.getKeyCode();

                switch (code) {
                    case KeyEvent.VK_RIGHT:

                        break;
                    case KeyEvent.VK_LEFT:

                        break;
                    case KeyEvent.VK_UP:
                        rotate();

                        break;
                    case KeyEvent.VK_DOWN:

                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) graphics;

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] == 1) {
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect( (this.x+ x) * factor, (this.y +y) * factor,  factor, factor);
                    g2d.setColor(Color.lightGray);
                    g2d.drawRect((this.x  + x) * factor, (this.y  + y) * factor, factor, factor);

                }
            }
        }
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                g2d.setColor(Color.lightGray);
                g2d.drawRect(x * factor, y * factor, factor, factor);
                    if (y == 5 && x == 5) {
                        g2d.setColor(Color.red);
                        g2d.fillRect(x*factor , y*factor , factor, factor);
                    }


            }
        }
    }

    public void rotate() {
        int[][] newShape = new int[shape[0].length][shape.length];
        final int M = shape.length;
        final int N = shape[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                newShape[c][M-1-r] = shape[r][c];
            }
        }
        orientation++;
        int height = shape.length;
        int width = shape[0].length;

        int centerX = width/2;
        int centerY = height/2;

//        x = y+centerX-centerY;
//        y = centerX + centerY -x - width;

//        switch (orientation) {
//            case 1 :
//               x -=2;
//                y +=2;
//                break;
//            case 2 :
//               y-=2;
//                x+=2;
//               break;
//            case 3 :
//                x -= 2;
//                y +=2;
//                break;
//            case 4 :
//                orientation =0;
//                x +=2;
//                y -=2;
//                break;
//        }
        double angle = Math.toRadians(90);
//        int x1 =  (int) (Math.cos(angle)* (x-centerX) - Math.sin(angle) *(y-centerY) +centerX);
//        int y1 =(int) (Math.sin(angle)* (x-centerX) + Math.cos(angle) *(y-centerY) +centerY);

        System.out.println(Arrays.deepToString(newShape));
        shape = newShape;



    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Bloxx");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RotationDemo demo = new RotationDemo();
                demo.createAndShowGUI();
            }
        });
    }
}
