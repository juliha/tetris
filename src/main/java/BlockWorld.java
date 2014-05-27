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

    BlockWorld() {
        super();

        timer = new Timer();
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        final Graphics2D g2d = (Graphics2D) this.getGraphics();

        landedBlocks = new int[16][10];
        for (int i = 0; i < landedBlocks.length; i++) {
            for (int j = 0; j < landedBlocks[i].length; j++) {
                landedBlocks[i][j] = 0;
            }
        }

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
                        if (moveIsPossible(block)) {
                            currentBlock.moveRight();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        block.moveLeft();
                        if (moveIsPossible(block)) {
                            currentBlock.moveLeft();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        block.rotate();
                        if (moveIsPossible(block)) {
                            currentBlock.rotate();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        block.moveDown();
                        if (moveIsPossible(block)) {
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
        speed = 6;
    }

    private int removeFull(int i) {
        while (i < landedBlocks.length) {
            int max = landedBlocks[i].length;
            int c = 0;
            for (int j = 0; j < max; j++) {
                if (landedBlocks[i][j] == 1) {
                    c++;
                }
            }
            if (c == max) {
                removeRow(i);
                removeFull(i);
            }
            i++;
        }
        return i;
    }

    private void removeRow(int i) {
        int[][] newLandedBlocks = new int[landedBlocks.length][landedBlocks[0].length];
        for (int j = 0; j < newLandedBlocks.length ; j++) {
            if (j < i) {
                newLandedBlocks[j + 1] = landedBlocks[j];
            }
            if (j > i) {
                newLandedBlocks[j] =landedBlocks[j];
            }
        }
        landedBlocks = newLandedBlocks;
    }

    private boolean moveIsPossible(AbstractBlock block) {
        int[][] blockShape = block.getBlockShape();
        for (int y = 0; y < blockShape.length; y++) {
            for (int x = 0; x < blockShape[y].length; x++) {
                int realX = x + block.getX();
                int realY = y + block.getY();
                if (!isWithingBoundsY(realY) || !isWithingBoundsX(realX) || (blockShape[y][x] == 1 && landedBlocks[realY][realX] == 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWithingBoundsX(int n) {
        return n > -1 && n < landedBlocks[0].length;
    }

    private boolean isWithingBoundsY(int n) {
        return n > -1 && n < landedBlocks.length;
    }

    public void runGame() {
        Thread gameThread = new Thread() {
            public void run() {
                //List<int[][]> shape = BlockGenerator.getRandomShape();

                currentBlock = new LShape(4, 0, 0);

                while (true) {
                    boolean isFalling = update();
                    repaint();
                    if (isFalling == false) {
                        int[][] blockShape = currentBlock.getBlockShape();
                        landBlock(blockShape);
                        try {
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    removeFull(0);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }


                        //shape = BlockGenerator.getRandomShape();
                        currentBlock = new LShape(4, 0, 0);
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

    private void landBlock(int[][] blockShape) {
        for (int y = 0; y < blockShape.length; y++) {
            for (int x = 0; x < blockShape[y].length; x++) {
                if (landedBlocks[currentBlock.getY() + y][currentBlock.getX() + x] != 1) {
                    landedBlocks[currentBlock.getY() + y][currentBlock.getX() + x] = blockShape[y][x];
                }
            }
        }
    }

    public boolean update() {
        AbstractBlock block = currentBlock.copyBlock();
        block.moveDown();
        if (!moveIsPossible(block)) {
            return false;
        }
        currentBlock.moveDown();
        return true;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) graphics;
        for (int y = 0; y < landedBlocks.length; y++) {
            for (int x = 0; x < landedBlocks[y].length; x++) {
                g2d.setColor(Color.lightGray);
                g2d.drawRect(x * factor, y * factor, factor, factor);
                if (landedBlocks[y][x] == 1) {
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(x * factor, y * factor, factor, factor);
                    g2d.setColor(Color.lightGray);
                    g2d.drawRect(x * factor, y * factor, factor, factor);
                }

            }
        }


        for (int y = 0; y < currentBlock.getBlockShape().length; y++) {
            for (int x = 0; x < currentBlock.getBlockShape()[y].length; x++) {
                int[][] blockShape = currentBlock.getBlockShape();
                System.out.println(blockShape[y][x]);
                if (blockShape[y][x] == 1) {
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect((currentBlock.getX()-currentBlock.getCenterX() + x) * factor, (currentBlock.getY() - currentBlock.getCenterY() + y) * factor,  factor, factor);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.drawRect((currentBlock.getX() -currentBlock.getCenterX() + x) * factor, (currentBlock.getY() - currentBlock.getCenterY() + y) * factor, factor,  factor);

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
