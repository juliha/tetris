import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by julia on 29.05.2014.
 */
public class BlockWorldModel {
    private volatile AbstractBlock currentBlock;
    private int[][] landedBlocks;
    int width;
    int height;
    int score;
    private int correctedX;
    List<Listener> listeners;


    public BlockWorldModel(int width, int height) {
        this.height = height;
        this.width = width;
        score = 0;
        initializeLandedBlocks(width, height);
        correctedX =-10;
        listeners = new ArrayList<Listener>();

    }

    public void initializeLandedBlocks(int width, int height) {
        landedBlocks = new int[height][width];
        for (int i = 0; i < landedBlocks.length; i++) {
            for (int j = 0; j < landedBlocks[i].length; j++) {
                landedBlocks[i][j] = 0;
            }

        }
    }

    public int getCorrectedX() {
        return correctedX;
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

    public void removeFull() {
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
                score += 5;
                notifyListeners();
            }
            i++;
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


    public AbstractBlock wallkick(AbstractBlock block) {
        int initialX = block.getX();
        AbstractBlock blockRight = block.copyBlock();
        blockRight.moveRight();
        if (moveIsPossible(blockRight)) {
            System.out.println("Will advise move right");
            correctedX = initialX;
            System.out.println("CorrectedX set to "+ correctedX);
            return blockRight;
        } else {
            AbstractBlock blockLeft = block.copyBlock();
            blockLeft.moveLeft();
            if (moveIsPossible(blockLeft)) {
                System.out.println("Will advise move left");
                correctedX = initialX;
                System.out.println("CorrectedX set to "+ correctedX);
                return blockLeft;
            } else {
                System.out.println("Will advise move remain same");
                return block;

            }
        }

    }


    private boolean isWithingBoundsX(int n) {
        return n > -1 && n < landedBlocks[0].length;
    }

    private boolean isWithingBoundsY(int n) {
        return n > -1 && n < landedBlocks.length;
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
