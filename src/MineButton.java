import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Vincent Lau
 * @filename MineButton.java
 * @description 
 * @date 2014-1-23
 */

@SuppressWarnings("serial")
public class MineButton extends JButton{
	private int row;				//行数
	private int col;				//列数
	private int flag;				//标记状态，0为无标记，1为旗子，2为问号
	private boolean isClicked;		//是否已被点开
	
	public MineButton(int row, int col, ImageIcon icon) {
		super(icon);
		this.row = row;
		this.col = col;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
