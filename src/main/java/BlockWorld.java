import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;


/**
 * Created by julia on 21.04.14.
 */
public class BlockWorld extends JPanel implements Runnable {

    private int factor;
    private int width;
    private int height;
    Color color = Color.LIGHT_GRAY;
    private int speed;

    private Thread animation;
    private volatile boolean isRunning=false;
    private volatile boolean gameOver =false;
    private BlockWorldModel model;

    BlockWorld(int width, int height, int factor) {
        super();
        setBackground(Color.WHITE);
        this.factor = factor;
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width * factor, height * factor));
        this.setFocusable(true);
        final Graphics2D g2d = (Graphics2D) this.getGraphics();
        model = new BlockWorldModel(width, height);

        setBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY));

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

    public BlockWorldModel getModel() {
        return model;
    }

    public void addNotify() {
        super.addNotify();
       startGame();
    }


    private void startGame() {
        if (animation == null || !isRunning) {
            animation = new Thread(this);
            animation.start();
        }
    }

    public void stopGame() {
        isRunning =false;
        repaint();
    }

    @Override
    public void run() {
        isRunning =true;
        while (isRunning) {
            gameUpdate();
            repaint();
            try {
                Thread.sleep(speed * 1000);
            } catch (InterruptedException ex) {
            }
        }
        if (!isRunning) {
            int n = gameOver();
            if (n == 0) {
                cleanup();
                startGame();
            } else {
                System.exit(0);
            }
        }
    }

    private void cleanup() {
        model.cleanUpModel();
        //repaint();
    }


    private void gameUpdate() {
        if (!gameOver) {
            if (model.getCurrentBlock() == null) {
                boolean isRunning = model.setNewCurrentBlock();
            }
            boolean isFalling = model.update();
            if (isFalling == false) {
                model.landBlock();
                model.removeFull();
                isRunning = model.setNewCurrentBlock();

            }
            if (isRunning == false) {
                stopGame();
            }
        }
    }




    public int gameOver() {
        Object[] options = {"Restart",
                "Quit"};
        int n = JOptionPane.showOptionDialog(this,
                "Game Over",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title
        return n;

    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        AbstractBlock block = model.getCurrentBlock();
        if (model.getCurrentBlock() != null) {
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






}
