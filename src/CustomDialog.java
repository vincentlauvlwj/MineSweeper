import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

/**
 * @author Vincent Lau
 * @filename CustomFrame.java
 * @description 
 * @date 2014-1-21
 */

@SuppressWarnings("serial")
public class CustomDialog extends JDialog implements ActionListener{
	private JLabel heightLabel = new JLabel("Height (9-24): ");
	private JLabel widthLabel = new JLabel("Width (9-30): ");
	private JLabel numLabel = new JLabel("Mine Num (10-668): ");
	private JTextField heightField = new JTextField();
	private JTextField widthField = new JTextField();
	private JTextField numField = new JTextField();
	private JButton submitBut = new JButton("Submit");
	private JButton clearBut = new JButton("Clear");
	private MSweeper parent;
	
	public CustomDialog(MSweeper parent) {
		super(parent, "Custom", true);
		this.parent = parent;
		initComponents();
	}

	private void initComponents() {
		initTextField(heightField, parent.getMaxRow());
		initTextField(widthField, parent.getMaxCol());
		initTextField(numField, parent.getMineNum());
		
		heightLabel.setBounds(10, 10, 120, 20);
		widthLabel.setBounds(10, 30, 120, 20);
		numLabel.setBounds(10, 50, 120, 20);
		heightField.setBounds(130, 10, 60, 20);
		widthField.setBounds(130, 30, 60, 20);
		numField.setBounds(130, 50, 60, 20);
		submitBut.setBounds(20, 80, 75, 20);
		clearBut.setBounds(110, 80, 75, 20);
		
		submitBut.addActionListener(this);
		clearBut.addActionListener(this);
		submitBut.setFocusPainted(false);
		clearBut.setFocusPainted(false);
		
		add(heightLabel);
		add(widthLabel);
		add(numLabel);
		add(heightField);
		add(widthField);
		add(numField);
		add(submitBut);
		add(clearBut);
		setSize(210, 150);
		setLayout(null);
		setResizable(false);
		
		int x = parent.getLocation().x;
		int y = parent.getLocation().y;
		int w1 = parent.getSize().width;
		int h1 = parent.getSize().height;
		int w2 = this.getSize().width;
		int h2 = this.getSize().height;
		this.setLocation(x+(w1-w2)/2, y+(h1-h2)/2);
	}
	
	private void initTextField(final JTextField field, int num) {
		field.setDocument(new PlainDocument(){
			@Override
			public void insertString(int offset, String s, AttributeSet attrSet) {
				try {
					Integer.parseInt(s);
					super.insertString(offset, s, attrSet);
				}catch (Exception e) {
					System.err.println(e);
				}
			}
		});
		field.setText(Integer.toString(num));
		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				field.selectAll();
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == submitBut) {
			try {
				int height = Integer.parseInt(heightField.getText());
				int width = Integer.parseInt(widthField.getText());
				int num = Integer.parseInt(numField.getText());
				if(width <9 || width > 30 || height < 9 || height > 24 || num < 10 || num > 668 || height*width <= num) {
					throw new Exception("Invalid Number!");
				}else {
					parent.setNewGame(height, width, num);
					dispose();
				}
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}else if(src == clearBut) {
			heightField.setText("");
			widthField.setText("");
			numField.setText("");
		}
	}
}