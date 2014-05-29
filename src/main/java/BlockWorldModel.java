/**
 * Created by julia on 29.05.2014.
 */
public class BlockWorldModel {
    private AbstractBlock currentBlock;
    private int[][] landedBlocks;

    public BlockWorldModel(int width, int height) {
        landedBlocks =new int[height][width];
    }

    public int removeFull() {
        System.out.println("in remove Full");
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
                ///removeFull(i);
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

    public boolean moveIsPossible(AbstractBlock block) {
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
    public void landBlock(int[][] blockShape) {
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


}
