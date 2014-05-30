import java.util.Arrays;

/**
 * Created by julia on 29.05.2014.
 */
public class BlockWorldModel {
    private AbstractBlock currentBlock;
    private int[][] landedBlocks;
    int width;
    int height;


    public BlockWorldModel(int width, int height) {
        this.height = height;
        this.width =width;
        initializeLandedBlocks(width, height);

    }

    public void initializeLandedBlocks(int width, int height) {
        landedBlocks =new int[height][width];
        for (int i = 0; i < landedBlocks.length; i++) {
            for (int j = 0; j < landedBlocks[i].length; j++) {
                landedBlocks[i][j] =0;
            }

        }
    }

    public void cleanUpModel() {
        currentBlock = null;
        initializeLandedBlocks(width,height);
    }

    public boolean setNewCurrentBlock() {
        AbstractBlock block = BlockGenerator.getRandomShape();
        if (moveIsPossible(block)) {
            currentBlock = block;
            return true;
        }
        //this signals that the game is over
         return false;

    }

    public AbstractBlock getCurrentBlock() {
        return currentBlock;
    }

    public int[][] getLandedBlocks() {
        return landedBlocks;
    }

    public void removeFull() {
        int i=0;
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
            }
            i++;
        }
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

    private boolean isWithingBoundsY(int n) {
        return n > -1 && n < landedBlocks.length;
    }
    public void landBlock() {
        int[][] blockShape = currentBlock.getBlockShape();
        for (int y = 0; y < blockShape.length; y++) {
            for (int x = 0; x < blockShape[y].length; x++) {
                if (blockShape[y][x]!=0) {
                    if (landedBlocks[currentBlock.getY() + y][currentBlock.getX() + x] != 1) {
                        landedBlocks[currentBlock.getY() + y][currentBlock.getX() + x] = blockShape[y][x];
                    }
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


}
