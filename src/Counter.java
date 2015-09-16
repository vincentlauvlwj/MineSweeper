import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Vincent Lau
 * @filename Counter.java
 * @description 
 * @date 2014-1-22
 */

@SuppressWarnings("serial")
public class Counter extends JPanel{
	private int num;
	private ImageIcon[] numImgs = {
			new ImageIcon(this.getClass().getResource("resources/c0.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c1.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c2.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c3.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c4.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c5.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c6.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c7.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c8.gif")), 
			new ImageIcon(this.getClass().getResource("resources/c9.gif"))
	};
	private JButton[] counter = {
			new JButton(numImgs[0]), 
			new JButton(numImgs[0]), 
			new JButton(numImgs[0]), 
	};
	
	public Counter(int num) {
		for(int i = 0;i < 3;i ++) {
			counter[i].setSize(13, 23);
			counter[i].setMargin(new Insets(0, 0, 0, 0));
			add(counter[i]);
		}
		setSize(23, 39);
		setVisible(true);
		reset(num);
	}
	
	//重置计数器
	public void reset(int num) {
		this.num = num;
		this.num = this.num<0 ? 0 : this.num;
		this.num = this.num>999 ? 999 : this.num;
		resetImage();
		repaint();
	}
	
	//计数器加一
	public void add() {
		num ++;
		num = num>999 ? 999 : num;
		resetImage();
		repaint();
	}
	
	//重置计数器图标
	private void resetImage() {
		int ones = num % 10;
		int tens = num % 100 / 10;
		int hundreds = num % 1000 / 100;
		counter[0].setIcon(numImgs[hundreds]);
		counter[1].setIcon(numImgs[tens]);
		counter[2].setIcon(numImgs[ones]);
	}

	public int getNum() {
		return num;
	}
}
