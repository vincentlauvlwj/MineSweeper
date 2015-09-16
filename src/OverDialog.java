
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author Vincent Lau
 * @filename MsgFrame.java
 * @description 
 * @date 2014-1-22
 */

@SuppressWarnings("serial")
public class OverDialog extends JDialog implements ActionListener {
	private JLabel msgLabel = new JLabel();
	private JButton newGameBut = new JButton("New Game");
	private JButton againBut = new JButton("Try Again");
	private JButton exitBut = new JButton("Exit");
	private MSweeper parent;
	
	public OverDialog(final MSweeper parent, boolean isWin) {
		super(parent, true);
		this.parent = parent;
		
		if(isWin) {
			setTitle("Congratulations!");
			msgLabel.setText("Congratulations! You WIN!");
		}else {
			setTitle("Game Over!");
			msgLabel.setText("Unfortunately,  you LOSE!");
		}
		
		initComponents();
		Toolkit.getDefaultToolkit().beep();
	}
	
	private void initComponents() {
		msgLabel.setFont(new Font("Serief", Font.ITALIC + Font.BOLD, 18));
		msgLabel.setBounds(60, 40, 300, 20);
		newGameBut.setBounds(20, 100, 100, 20);
		againBut.setBounds(125, 100, 100, 20);
		exitBut.setBounds(230, 100, 100, 20);
		
		newGameBut.addActionListener(this);
		againBut.addActionListener(this);
		exitBut.addActionListener(this);
		newGameBut.setFocusPainted(false);
		againBut.setFocusPainted(false);
		exitBut.setFocusPainted(false);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				parent.resetGame(null);
			}
		});
		
		add(msgLabel);
		add(newGameBut);
		add(againBut);
		add(exitBut);
		setLayout(null);
		setSize(345, 200);
		setResizable(false);
		
		int x = parent.getLocation().x;
		int y = parent.getLocation().y;
		int w1 = parent.getSize().width;
		int h1 = parent.getSize().height;
		int w2 = this.getSize().width;
		int h2 = this.getSize().height;
		this.setLocation(x+(w1-w2)/2, y+(h1-h2)/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGameBut) {
			parent.resetGame(null);
			dispose();
		}else if(e.getSource() == againBut) {
			parent.resetGame(parent.getMineMap());
			dispose();
			JOptionPane.showMessageDialog(parent, 
					"Be careful ! ! !  The mines' location is exactly the same as the last time, \n" +
					"you may probably lose the game when you click the fist field.",
					"Warning!", JOptionPane.WARNING_MESSAGE);
		}else if(e.getSource() == exitBut) {
			System.exit(0);
		}
	}
}
