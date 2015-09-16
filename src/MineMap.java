import java.util.Random;

/**
 * @author Vincent Lau
 * @filename MineArth.java
 * @description 
 * @date 2014-1-22
 */

public class MineMap {
	private int[][] mines;			//保存地图的整数数组，数字表示周围地雷的个数，9代表自己是地雷
	private int maxRow;				//最大行数
	private int maxCol;				//最大列数
	private int mineNum;			//雷数
	private boolean isAvailable;	//标明地图是否可用
	
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
	
	//获得mines数组的某个元素值
	public int element(int row, int col) {
		return mines[row][col];
	}
	
	//设置地雷的方法，参数表示第一个被点击的地点，此处不应有雷
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
	
	//设置雷数的方法
	private void setMineNum() {
		for(int row = 0;row < maxRow;row ++)
			for(int col = 0;col < maxCol;col ++)
				mines[row][col] = mines[row][col]==9 ? 9 : checkMineNum(row, col);
	}
	
	//统计某个点周围的雷数的方法
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
	
//	public static void main(String[] args) {
//		MineMap mineMap = new MineMap(9, 9, 10, 0, 0);
//		for(int r = 0;r < mineMap.maxRow;r ++) {
//			for(int c = 0;c < mineMap.maxCol; c ++) {
//				System.out.print(mineMap.element(r, c) + " ");
//			}
//			System.out.println();
//		}
//	}
}
