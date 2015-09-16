import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Vincent Lau
 * @filename AboutFrame.java
 * @description 
 * @date 2014-1-21
 */

@SuppressWarnings("serial")
public class AboutDialog extends JDialog implements ActionListener{
	private JPanel aboutPane = new JPanel();
	private GridBagConstraints constraints = new GridBagConstraints();
	private GridBagLayout gridBag = new GridBagLayout();
	private JLabel nameLabel = new JLabel("Mine Sweeper for Java.");
	private JLabel versionLabel = new JLabel("Version: 2.5");
	private JLabel rightLabel = new JLabel("Copyright (c) 2013-2014 Vincent Lau, S.M.U. All rights reserved.");
	private JLabel dateLabel = new JLabel("Release Date: 2014-01-24");
	private JLabel emailLabel = new JLabel("E-mail: vincent_lauvl@163.com, vincentlauvlwj@gmail.com");
	private JLabel resourcesLabel = new JLabel("Resources: Windows XP");
	private JLabel referenceLabel = new JLabel("Reference: Jerry Shen(Shen Ji Feng)");
	private JLabel linkLabel = new JLinkLabel("http://bbs.chinaunix.net/thread-1069189-1-1.html");
	private JLabel blanketLabel = new JLabel(" ");
	private JButton okBut = new JButton("OK");
	private MSweeper parent;
	
	public AboutDialog(MSweeper parent) {
		super(parent, "About", true);
		this.parent = parent;
		initComponents();
	}

	private void initComponents() {
		okBut.addActionListener(this);
		okBut.setFocusPainted(false);
		aboutPane.setLayout(gridBag);
		addComponent(nameLabel, 1);
		addComponent(versionLabel, 2);
		addComponent(rightLabel, 3);
		addComponent(dateLabel, 4);
		addComponent(emailLabel, 5);
		addComponent(resourcesLabel, 6);
		addComponent(referenceLabel, 7);
		addComponent(linkLabel, 8);
		addComponent(blanketLabel, 9);
		addComponent(okBut, 10);
		
		setContentPane(aboutPane);
		pack();
		setResizable(false);
		
		int x = parent.getLocation().x;
		int y = parent.getLocation().y;
		int w1 = parent.getSize().width;
		int h1 = parent.getSize().height;
		int w2 = this.getSize().width;
		int h2 = this.getSize().height;
		this.setLocation(x+(w1-w2)/2, y+(h1-h2)/2);
	}
	
	private void addComponent(JComponent component, int y) {
		constraints.gridy = y;
		aboutPane.add(component, constraints);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okBut) {
			dispose();
		}
	}
}