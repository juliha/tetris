import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;


/**
 * Created by julia on 21.04.14.
 */
public class BlockWorld extends JPanel implements Runnable {

    private int factor;
    Color color = Color.LIGHT_GRAY;
    private int speed;

    private Thread animation;
    private volatile boolean isRunning = false;
    private volatile boolean gameOver = false;
    private BlockWorldModel model;
    private List<Integer> removeRows;
    private boolean isFalling = true;

    BlockWorld(int width, int height, int factor) {
        super();
        setBackground(Color.WHITE);
        this.factor = factor;
        this.setPreferredSize(new Dimension(width * factor, height * factor));
        this.setFocusable(true);
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
                final AbstractBlock block = model.getCurrentBlock().copyBlock();
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
                        int correctedX = model.getCorrectedX();
                        block.rotate();
                       if (correctedX != -10) {
                           block.setX(correctedX);
                        }
                        if (model.moveIsPossible(block)) {
                            model.getCurrentBlock().rotate();
                            if (correctedX != -10) {
                                model.getCurrentBlock().setX(correctedX);
                            }
                        } else {
                            if (model.isWithingBoundsY(block.getY() + block.getBlockShape().length)) {
                                AbstractBlock wallkickedBlock = model.wallkick(block);
                                model.setCurrentBlock(wallkickedBlock.copyBlock());

                            }

                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        block.moveDown();
                        if (model.moveIsPossible(block)) {
                            model.getCurrentBlock().moveDown();
                        }
                        break;
                    case KeyEvent.VK_SPACE :
                        int score = model.getHeight() - block.getY();
                        model.increaseScore(score);
                        while (true) {
                            block.moveDown();
                            if (!model.moveIsPossible(block)) {
                                return;
                            }
                            model.getCurrentBlock().moveDown();
                            repaint();
                        }
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

    @Override
    public void run() {
        isRunning = true;


        while (isRunning) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        //gameUpdate();
                        if (!gameOver) {
                            if (model.getCurrentBlock() == null) {
                                isRunning = model.generateAndSetNewCurrentBlock();
                            }
                            isFalling = model.update();

                            if (isFalling == false) {
                                model.landBlock();
                                removeRows =model.removeFull();

                            }

                        }

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (removeRows != null && removeRows.size() != 0) {
                repaint();
                try {
                    Thread.sleep(speed * 500);
                } catch (InterruptedException ex) {
                }
            }


            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        if (isFalling == false) {
                            for (int i = 0; i < removeRows.size(); i++) {
                                model.removeRow(removeRows.get(i));
                            }
                        isRunning = model.generateAndSetNewCurrentBlock();
                        }
                        if (isRunning == false) {
                            stopGame();
                        }
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (removeRows != null) {
                removeRows.clear();
            }
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

    public void addNotify() {
        super.addNotify();
        startGame();
    }


    public void startGame() {
        if (animation == null || !isRunning) {
            animation = new Thread(this);
            animation.start();
        }
    }

    public void stopGame() {
        isRunning = false;
        repaint();
    }


    private void cleanup() {
        model.cleanUpModel();
    }


    private void gameUpdate() {
        if (!gameOver) {
            if (model.getCurrentBlock() == null) {
                isRunning = model.generateAndSetNewCurrentBlock();
            }
            boolean isFalling = model.update();

            if (isFalling == false) {

                model.landBlock();
                removeRows =model.removeFull();
                isRunning = model.generateAndSetNewCurrentBlock();
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
        if (block != null) {
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
                    if (removeRows.contains(y)) {
                        g2d.setColor(Color.WHITE);
                    }
                    g2d.fillRect(x * factor, y * factor, factor, factor);
                    g2d.setColor(Color.lightGray);
                    g2d.drawRect(x * factor, y * factor, factor, factor);


                }
            }
        }
    }


}
