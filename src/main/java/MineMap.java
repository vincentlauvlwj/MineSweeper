import java.util.Random;

/**
 * @author Vincent Lau
 */
public class MineMap {
    private int[][] mines;
    private int maxRow;
    private int maxCol;
    private int mineNum;
    private boolean isAvailable;

    public MineMap(int maxRow, int maxCol, int mineNum, int outRow, int outCol) {
        try {
            if(outRow < 0 || outCol < 0 || maxRow <= outRow || maxCol <= outCol || maxRow*maxCol <= mineNum)
                throw new Exception("Invalid Number!");
            this.maxRow = maxRow;
            this.maxCol = maxCol;
            this.mineNum = mineNum;
            mines = new int[maxRow][maxCol];
            setMine(outRow, outCol);
            setMineNum();
            isAvailable = true;
        }catch(Exception e) {
            System.err.println(e);
            isAvailable = false;
        }
    }

    public int element(int row, int col) {
        return mines[row][col];
    }

    private void setMine(int outRow, int outCol) {
        int num = 0;
        Random random = new Random();
        while(num < mineNum) {
            int row = random.nextInt(maxRow);
            int col = random.nextInt(maxCol);
            if(mines[row][col] !=0 || row == outRow && col == outCol) continue;
            mines[row][col] = 9;
            num ++;
        }
    }

    private void setMineNum() {
        for(int row = 0;row < maxRow;row ++)
            for(int col = 0;col < maxCol;col ++)
                mines[row][col] = mines[row][col]==9 ? 9 : checkMineNum(row, col);
    }

    private int checkMineNum(int row, int col) {
        int top = row-1<0 ? 0 : row-1;
        int bottom = row+1<maxRow ? row+1 : maxRow-1;
        int left = col-1<0 ? 0 : col-1;
        int right = col+1<maxCol ? col+1 : maxCol-1;
        int num = 0;
        for(int r = top;r <= bottom;r ++)
            for(int c = left;c <= right;c ++)
                if(mines[r][c] == 9) num ++;
        return num;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

//    public static void main(String[] args) {
//        MineMap mineMap = new MineMap(9, 9, 10, 0, 0);
//        for(int r = 0;r < mineMap.maxRow;r ++) {
//            for(int c = 0;c < mineMap.maxCol; c ++) {
//                System.out.print(mineMap.element(r, c) + " ");
//            }
//            System.out.println();
//        }
//    }
}
