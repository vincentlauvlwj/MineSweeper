import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Vincent Lau
 */
public class Counter extends JPanel{
    private int num;
    private ImageIcon[] numImgs = {
            new ImageIcon(this.getClass().getResource("c0.gif")),
            new ImageIcon(this.getClass().getResource("c1.gif")),
            new ImageIcon(this.getClass().getResource("c2.gif")),
            new ImageIcon(this.getClass().getResource("c3.gif")),
            new ImageIcon(this.getClass().getResource("c4.gif")),
            new ImageIcon(this.getClass().getResource("c5.gif")),
            new ImageIcon(this.getClass().getResource("c6.gif")),
            new ImageIcon(this.getClass().getResource("c7.gif")),
            new ImageIcon(this.getClass().getResource("c8.gif")),
            new ImageIcon(this.getClass().getResource("c9.gif"))
    };
    private JButton[] counter = {
            new JButton(numImgs[0]),
            new JButton(numImgs[0]),
            new JButton(numImgs[0]),
    };

    public Counter(int num) {
        for(int i = 0;i < 3;i ++) {
            counter[i].setMaximumSize(new Dimension(13, 23));
            counter[i].setPreferredSize(new Dimension(13, 23));
            counter[i].setMargin(new Insets(0, 0, 0, 0));
            add(counter[i]);
        }
        setSize(23, 39);
        setVisible(true);
        reset(num);
    }

    public void reset(int num) {
        this.num = num;
        this.num = this.num<0 ? 0 : this.num;
        this.num = this.num>999 ? 999 : this.num;
        resetImage();
        repaint();
    }

    public void add() {
        num ++;
        num = num>999 ? 999 : num;
        resetImage();
        repaint();
    }

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
