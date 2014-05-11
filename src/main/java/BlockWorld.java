import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
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
    private Integer[][] landedBlocks;

    BlockWorld() {
        super();

        timer = new Timer();
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        final Graphics2D g2d = (Graphics2D) this.getGraphics();

        landedBlocks = new Integer[16][10];
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
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
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
        });
        speed = 2;
    }

    private boolean moveIsPossible(AbstractBlock block) {
        for (int y = 0; y < block.getBlockShape().length ; y++) {
            for (int x = 0; x < block.getBlockShape()[y].length; x++) {
                int realX = x + block.getX();
                int realY = block.getY();
                if (!isWithingBoundsY(realY) || !isWithingBoundsX(realX) || landedBlocks[realY][realX] != 0) {
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
                Integer[][] shape = {{1,1}};
                currentBlock = new Rectangle(4, 0, shape);

                while (true) {
                    boolean isFalling =update();
                    repaint();
                    if (isFalling == false) {
                        for (int y = 0; y < currentBlock.getBlockShape().length; y++) {
                            for (int x = 0; x < currentBlock.getBlockShape()[y].length; x++) {
                                landedBlocks[currentBlock.getY()][currentBlock.getX()+x] =1;
                            }
                        }
                        Integer[][] otherShape = {{1, 1}};
                        currentBlock = new Rectangle(4, 0, shape);
                        repaint();
                    }
                    System.out.println(Arrays.deepToString(landedBlocks));
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        gameThread.start();  // Invoke GaemThread.run()
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
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(x*factor , y*factor , factor,  factor );
                if (landedBlocks[y][x] != 0) {
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(x * factor, y * factor,  factor,  factor);
                }
            }
        }

        for (int y = 0; y < currentBlock.getBlockShape().length; y++) {
            for (int x = 0; x < currentBlock.getBlockShape()[y].length; x++) {
                g2d.setColor(Color.BLUE);
                g2d.fillRect((currentBlock.getX() + x) * factor, (currentBlock.getY() + y) * factor, x + factor, y + factor);
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
