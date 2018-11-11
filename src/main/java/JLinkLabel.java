import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author Vincent Lau
 */
public class JLinkLabel extends JLabel implements MouseListener {
    private boolean isMouseEntered;

    public JLinkLabel(String text) {
        if(! text.startsWith("http://"))
            text = "http://" + text;
        setText(text);
        setForeground(Color.BLUE);
        addMouseListener(this);
        isMouseEntered = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(isMouseEntered) {
            g.setColor(getForeground());
            Rectangle r = g.getClipBounds();
            int descent = getFontMetrics(getFont()).getDescent();
            int width = getFontMetrics(getFont()).stringWidth(getText());
            g.drawLine(0, r.height - descent, width, r.height - descent);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        isMouseEntered = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        isMouseEntered = false;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            Desktop.getDesktop().browse(new URL(getText()).toURI());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}
