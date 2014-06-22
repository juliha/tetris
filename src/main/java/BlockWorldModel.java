import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by julia on 29.05.2014.
 */
public class BlockWorldModel {
    private AbstractBlock currentBlock;
    private int[][] landedBlocks;
    int width;
    int height;
    int score;
    private int correctedX;
    private int numRotate;
    List<Listener> listeners;


    public BlockWorldModel(int width, int height) {
        this.height = height;
        this.width = width;
        score = 0;
        initializeLandedBlocks(width, height);
        correctedX =-10;
        listeners = new ArrayList<Listener>();
        numRotate=0;

    }

    public void initializeLandedBlocks(int width, int height) {

        landedBlocks = new int[height][width];
        for (int i = 0; i < landedBlocks.length; i++) {
            for (int j = 0; j < landedBlocks[i].length; j++) {
                landedBlocks[i][j] = 0;
            }

        }
    }

    public int getHeight() {
        return height;
    }

    public int getCorrectedX() {
        return correctedX;
    }

    public int getNumRotate() {
        return numRotate;
    }

    public void setNumRotate(int numRotate) {
        this.numRotate = numRotate;
    }

    public void incrementNumRotate() {
        numRotate++;
    }

    public void resetCorrectedX() {
        this.correctedX = -10;
    }

    public void cleanUpModel() {
        currentBlock = null;
        score = 0;
        initializeLandedBlocks(width, height);
    }

    public boolean generateAndSetNewCurrentBlock() {
        resetCorrectedX();
        AbstractBlock block = BlockGenerator.getRandomShape();
        if (moveIsPossible(block)) {
            currentBlock = block;
            return true;
        }
        //this signals that the game is over
        return false;
    }

    public void setCurrentBlock(AbstractBlock block) {
        this.currentBlock = block;
    }

    public AbstractBlock getCurrentBlock() {
        return currentBlock;
    }

    public int[][] getLandedBlocks() {
        return landedBlocks;
    }

    public void increaseScore(int number) {
        score += number;
        notifyListeners();
    }


    public void removeFull() {
        int rowsRemoved=0;
        int i = 0;
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
                rowsRemoved++;
            }
            i++;
        }
        System.err.println(rowsRemoved);
        if (rowsRemoved != 0) {
            increaseScore((int) Math.pow(10, rowsRemoved));
        }
    }

    private void removeRow(int i) {
        int[][] newLandedBlocks = new int[landedBlocks.length][landedBlocks[0].length];
        for (int j = 0; j < newLandedBlocks.length; j++) {
            if (j < i) {
                newLandedBlocks[j + 1] = landedBlocks[j];
            }
            if (j > i) {
                newLandedBlocks[j] = landedBlocks[j];
            }
        }
        landedBlocks = newLandedBlocks;
    }

    public boolean moveIsPossible(AbstractBlock block) {
        int[][] blockShape = block.getBlockShape();
        for (int y = 0; y < blockShape.length; y++) {
            for (int x = 0; x < blockShape[y].length; x++) {
                if (blockShape[y][x] != 0) {
                    int realX = x + block.getX();
                    int realY = y + block.getY();
                    if (!isWithingBoundsY(realY) || !isWithingBoundsX(realX) || (blockShape[y][x] == 1 && landedBlocks[realY][realX] == 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isWithingBoundsX(int n) {
        return n > -1 && n < landedBlocks[0].length;
    }

    public boolean isWithingBoundsY(int n) {
        return n > -1 && n < landedBlocks.length;
    }



    public AbstractBlock wallkick(AbstractBlock block) {
        int initialX = block.getX();
        AbstractBlock blockRight = block.copyBlock();
        if (blockRight instanceof IShape) {
            blockRight.moveRight();
        }
        blockRight.moveRight();
        if (moveIsPossible(blockRight)) {
            correctedX = initialX;
            return blockRight;
        } else {
            AbstractBlock blockLeft = block.copyBlock();
            if (blockLeft instanceof IShape) {
                blockLeft.moveLeft();
            }
            blockLeft.moveLeft();
            if (moveIsPossible(blockLeft)) {
                correctedX = initialX;
                return blockLeft;
            } else {
                return block;

            }
        }

    }



    public void landBlock() {
        int[][] blockShape = currentBlock.getBlockShape();
        for (int y = 0; y < blockShape.length; y++) {
            for (int x = 0; x < blockShape[y].length; x++) {
                if (blockShape[y][x] != 0) {
                    if (landedBlocks[currentBlock.getY() + y][currentBlock.getX() + x] != 1) {
                        landedBlocks[currentBlock.getY() + y][currentBlock.getX() + x] = blockShape[y][x];
                    }
                }
            }
        }
    }



    private void notifyListeners() {
        for (Listener listener : listeners) {
            listener.update(score);
        }
    }
    public boolean update() {
        AbstractBlock block = getCurrentBlock().copyBlock();
        block.moveDown();
        if (!moveIsPossible(block)) {
            return false;
        }
        getCurrentBlock().moveDown();
        return true;
    }

    public void register(Listener listener) {
        listeners.add(listener);
    }

    public void unregister(Listener listener) {
        listeners.remove(listener);
    }

    interface Listener {
        public void update(int score);
    }


}
