import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;
import java.util.Timer;


/**
 * Created by julia on 21.04.14.
 */
public class BlockWorld extends JPanel {

    private int factor;
    private int width;
    private int height;
    Color border = Color.LIGHT_GRAY;
    private int speed;
    java.util.Timer timer;

    BlockWorldModel model;

    BlockWorld(int width, int height, int factor) {
        super();
        this.factor = factor;
        this.width = width;
        this.height = height;
        timer = new Timer();
        this.setPreferredSize(new Dimension(width * factor, height * factor));
        this.setFocusable(true);
        final Graphics2D g2d = (Graphics2D) this.getGraphics();
        model = new BlockWorldModel(width, height);


        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int code = keyEvent.getKeyCode();
                AbstractBlock block = model.getCurrentBlock().copyBlock();
                switch (code) {
                    case KeyEvent.VK_RIGHT:
                        block.moveRight();
                        if (model.moveIsPossible(block)) {
                            model.getCurrentBlock().moveRight();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        block.moveLeft();
                        if (model.moveIsPossible(block)) {
                            model.getCurrentBlock().moveLeft();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        block.rotate();
                        if (model.moveIsPossible(block)) {
                            model.getCurrentBlock().rotate();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        block.moveDown();
                        if (model.moveIsPossible(block)) {
                            model.getCurrentBlock().moveDown();
                        }
                        break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        speed = 1;
    }

    public void runGame() {
        Thread gameThread = new Thread() {
            public void run() {
                model.setNewCurrentBlock();

                while (true) {
                    boolean isFalling = model.update();
                    System.out.println("is Falling " + isFalling);
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            @Override
                            public void run() {
                                repaint();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (isFalling == false) {
                        model.landBlock();
                        model.removeFull();
                        model.setNewCurrentBlock();
                    }
                    try {
                        Thread.sleep(speed * 1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        gameThread.start();  // Invoke GaemThread.run()
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) graphics;


        AbstractBlock block = model.getCurrentBlock();
        int[][] blockShape = block.getBlockShape();
        for (int y = 0; y < blockShape.length; y++) {
            for (int x = 0; x < blockShape[y].length; x++) {
                if (blockShape[y][x] == 1) {
                    g2d.setColor(block.getColor());
                    g2d.fillRect((block.getX() + x) * factor, (block.getY() + y) * factor, factor, factor);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.drawRect((block.getX() + x) * factor, (block.getY() + y) * factor, factor, factor);

                }
            }
        }
        int[][] landedBlocks = model.getLandedBlocks();
        for (int y = 0; y < landedBlocks.length; y++) {
            for (int x = 0; x < landedBlocks[y].length; x++) {
                g2d.setColor(Color.lightGray);
                g2d.drawRect(x * factor, y * factor, factor, factor);
                if (landedBlocks[y][x] == 1) {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillRect(x * factor, y * factor, factor, factor);
                    g2d.setColor(Color.lightGray);
                    g2d.drawRect(x * factor, y * factor, factor, factor);
                }

            }
        }
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Bloxx");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        runGame();
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BlockWorld game = new BlockWorld(10, 17, 20);
                game.createAndShowGUI();
            }
        });
    }
}
