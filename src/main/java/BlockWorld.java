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

    private int factor = 20;
    private int width = 10 * factor;
    private int height = 17 * factor;
    Color border = Color.LIGHT_GRAY;
    private int speed;
    java.util.Timer timer;
    private AbstractBlock currentBlock;
    private int[][] landedBlocks;

    BlockWorldModel model;

    BlockWorld() {
        super();

        timer = new Timer();
        this.setPreferredSize(new Dimension(width, height));
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
                AbstractBlock block = currentBlock.copyBlock();
                switch (code) {
                    case KeyEvent.VK_RIGHT:
                        block.moveRight();
                        if (model.moveIsPossible(block)) {
                            currentBlock.moveRight();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        block.moveLeft();
                        if (model.moveIsPossible(block)) {
                            currentBlock.moveLeft();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        block.rotate();
                        if (model.moveIsPossible(block)) {
                            currentBlock.rotate();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        block.moveDown();
                        if (model.moveIsPossible(block)) {
                            currentBlock.moveDown();
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
                currentBlock = BlockGenerator.getRandomShape();

                while (true) {
                    boolean isFalling = model.update();
                    repaint();
                    if (isFalling == false) {
                        int[][] blockShape = currentBlock.getBlockShape();
                        model.landBlock(blockShape);
                        try {
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    model.removeFull();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        currentBlock = BlockGenerator.getRandomShape().copyBlock();
                        System.out.print(currentBlock.getClass());
                        repaint();
                    }
                    // System.out.println(Arrays.deepToString(landedBlocks));
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



        for (int y = 0; y < currentBlock.getBlockShape().length; y++) {
            for (int x = 0; x < currentBlock.getBlockShape()[y].length; x++) {
                int[][] blockShape = currentBlock.getBlockShape();
                if (blockShape[y][x] == 1) {
                    g2d.setColor(Color.YELLOW);
                    g2d.fillRect((currentBlock.getX() + x) * factor, (currentBlock.getY() + y) * factor, factor, factor);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("1", (currentBlock.getX() + x) * factor + (factor / 2), (currentBlock.getY() + y) * factor + (factor / 2));
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.drawRect((currentBlock.getX() /*-currentBlock.getCenterX()*/ + x) * factor, (currentBlock.getY() /*- currentBlock.getCenterY()*/ + y) * factor, factor, factor);

                }
            }
        }

        for (int y = 0; y < landedBlocks.length; y++) {
            for (int x = 0; x < landedBlocks[y].length; x++) {
                g2d.setColor(Color.lightGray);
                g2d.drawRect(x * factor, y * factor, factor, factor);
                if (landedBlocks[y][x] == 1) {
                    g2d.setColor(Color.YELLOW);
                    g2d.fillRect(x * factor, y * factor, factor, factor);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("1*", x *factor +  (factor/2), y*factor +(factor/2));
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
                BlockWorld game = new BlockWorld();
                game.createAndShowGUI();
            }
        });
    }
}
