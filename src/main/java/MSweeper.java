import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Vincent Lau
 */
public class MSweeper extends JFrame implements ActionListener, MouseListener {
    public static final int EASY_MAX_ROW = 9;
    public static final int EASY_MAX_COL = 9;
    public static final int EASY_MINE_NUM = 10;
    public static final int MEDIUM_MAX_ROW = 16;
    public static final int MEDIUM_MAX_COL = 16;
    public static final int MEDIUM_MINE_NUM = 40;
    public static final int EXPERT_MAX_ROW = 16;
    public static final int EXPERT_MAX_COL = 30;
    public static final int EXPERT_MINE_NUM = 99;

    private ImageIcon[] faceIcon = {
            new ImageIcon(this.getClass().getResource("smile.gif")),
            new ImageIcon(this.getClass().getResource("Ooo.gif"))
    };
    private ImageIcon[] bombStatus = {
            new ImageIcon(this.getClass().getResource("mine.gif")),
            new ImageIcon(this.getClass().getResource("wrongmine.gif")),
            new ImageIcon(this.getClass().getResource("bomb.gif"))
    };
    private ImageIcon[] flagStatus = {
            new ImageIcon(this.getClass().getResource("blank.gif")),
            new ImageIcon(this.getClass().getResource("flag.gif")),
            new ImageIcon(this.getClass().getResource("question.gif"))
    };
    private ImageIcon[] mineNumIcon = {
            new ImageIcon(this.getClass().getResource("0.gif")),
            new ImageIcon(this.getClass().getResource("1.gif")),
            new ImageIcon(this.getClass().getResource("2.gif")),
            new ImageIcon(this.getClass().getResource("3.gif")),
            new ImageIcon(this.getClass().getResource("4.gif")),
            new ImageIcon(this.getClass().getResource("5.gif")),
            new ImageIcon(this.getClass().getResource("6.gif")),
            new ImageIcon(this.getClass().getResource("7.gif")),
            new ImageIcon(this.getClass().getResource("8.gif"))
    };

    private JMenuBar menuBar = new JMenuBar();
    private JMenu gameMenu = new JMenu("Game");
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem newGameItem = new JMenuItem("New Game   ");
    private JRadioButtonMenuItem easyItem = new JRadioButtonMenuItem("Easy");
    private JRadioButtonMenuItem mediumItem = new JRadioButtonMenuItem("Medium");
    private JRadioButtonMenuItem expertItem = new JRadioButtonMenuItem("Expert");
    private JMenuItem customItem = new JRadioButtonMenuItem("Custom...");
    private JMenuItem randomItem = new JRadioButtonMenuItem("Random");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JMenuItem exitItem = new JMenuItem("Exit");
    private JMenuItem aboutItem = new JMenuItem("About Mine Sweeper for Java...");

    private JPanel controlPane = new JPanel();
    private JPanel minePane = new JPanel();
    private JPanel contentPane = new JPanel();
    private JButton faceBut = new JButton(faceIcon[0]);

    private Counter mineCounter = new Counter(0);
    private Counter timeCounter = new Counter(0);
    private MineButton[][] mineButton;

    private int maxRow;
    private int maxCol;
    private int mineNum;
    private int flagNum;
    private boolean isStarted;
    private Timer timer;
    private MineMap mineMap;

    public MSweeper(String title) {
        super(title);
        initComponents();

        easyItem.setSelected(true);
        setNewGame(EASY_MAX_ROW, EASY_MAX_COL, EASY_MINE_NUM);
    }

    private void initComponents() {
        newGameItem.addActionListener(this);
        easyItem.addActionListener(this);
        mediumItem.addActionListener(this);
        expertItem.addActionListener(this);
        customItem.addActionListener(this);
        randomItem.addActionListener(this);
        exitItem.addActionListener(this);
        aboutItem.addActionListener(this);

        newGameItem.setMnemonic(KeyEvent.VK_N);
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        buttonGroup.add(easyItem);
        buttonGroup.add(mediumItem);
        buttonGroup.add(expertItem);
        buttonGroup.add(customItem);
        buttonGroup.add(randomItem);
        gameMenu.add(easyItem);
        gameMenu.add(mediumItem);
        gameMenu.add(expertItem);
        gameMenu.add(customItem);
        gameMenu.add(randomItem);
        gameMenu.addSeparator();
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        exitItem.setMnemonic(KeyEvent.VK_X);
        gameMenu.add(exitItem);
        aboutItem.setMnemonic(KeyEvent.VK_A);
        helpMenu.add(aboutItem);
        gameMenu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(gameMenu);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        faceBut.setMaximumSize(new Dimension(26, 27));
        faceBut.setPreferredSize(new Dimension(26, 27));
        faceBut.setMargin(new Insets(0, 0, 0, 0));
        faceBut.addMouseListener(this);
        faceBut.setPressedIcon(faceIcon[1]);
        faceBut.setBackground(Color.LIGHT_GRAY);
        faceBut.setFocusPainted(false);

        controlPane.add(mineCounter);
        controlPane.add(faceBut);
        controlPane.add(timeCounter);

        minePane.setLayout(new GridBagLayout());
        minePane.setBackground(Color.LIGHT_GRAY);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void setNewGame(int maxRow, int maxCol, int mineNum) {
        if(this.maxRow == maxRow && this.maxCol == maxCol && this.mineNum == mineNum) {
            resetGame(null);
            return;
        }
        this.maxRow = maxRow;
        this.maxCol = maxCol;
        this.mineNum = mineNum;
        this.flagNum = 0;
        isStarted = false;
        mineMap = null;
        faceBut.setIcon(faceIcon[0]);
        if(timer != null) timer.cancel();

        contentPane.removeAll();

        mineCounter.reset(mineNum);
        timeCounter.reset(0);
        contentPane.add(controlPane);

        minePane.removeAll();
        mineButton = new MineButton[maxRow][maxCol];
        for(int row = 0;row < maxRow;row ++) {
            for(int col = 0;col < maxCol;col ++) {
                mineButton[row][col] = new MineButton(row, col, flagStatus[0]);
                mineButton[row][col].addMouseListener(this);
                mineButton[row][col].setMargin(new Insets(0, 0, 0, 0));
                mineButton[row][col].setBackground(Color.LIGHT_GRAY);
                mineButton[row][col].setFocusPainted(false);
                mineButton[row][col].setMaximumSize(new Dimension(17, 17));
                mineButton[row][col].setPreferredSize(new Dimension(17, 17));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = col;
                gbc.gridy = row;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                minePane.add(mineButton[row][col], gbc);
            }
        }

        contentPane.add(minePane);
        setContentPane(contentPane);
        pack();
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width-getSize().width)/2, (height-getSize().height)/2);
        repaint();
        System.gc();
    }

    public void resetGame(MineMap mineMap) {
        flagNum = 0;
        isStarted = false;
        this.mineMap = mineMap;
        faceBut.setIcon(faceIcon[0]);
        if(timer != null) timer.cancel();
        mineCounter.reset(mineNum);
        timeCounter.reset(0);
        for(int row = 0;row < maxRow;row ++) {
            for(int col = 0;col < maxCol;col ++) {
                mineButton[row][col].setFlag(0);
                mineButton[row][col].setClicked(false);
                mineButton[row][col].setIcon(flagStatus[0]);
                mineButton[row][col].setEnabled(true);
            }
        }
        repaint();
    }

    private void startGame(int outRow, int outCol) {
        if(mineMap == null) mineMap = new MineMap(maxRow, maxCol, mineNum, outRow, outCol);
        if(! mineMap.isAvailable()) return;
        isStarted = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeCounter.add();
            }
        }, 1000, 1000);
    }

    private void setRandomGame() {
        Random random = new Random();
        while(true) {
            int maxRow = random.nextInt(16) + 9;
            int maxCol = random.nextInt(22) + 9;
            int mineNum = random.nextInt(659) + 10;
            if(mineNum > maxRow*maxCol/3) continue;
            setNewGame(maxRow, maxCol, mineNum);
            break;
        }
    }

    private void checkMine(int row, int col) {
        if(mineButton[row][col].isClicked() || mineButton[row][col].getFlag() != 0) return;
        if(mineMap.element(row, col) != 9)
            mineButton[row][col].setIcon(mineNumIcon[mineMap.element(row, col)]);
        mineButton[row][col].setClicked(true);
        if(mineMap.element(row, col) == 0) {
            //mineButton[row][col].setEnabled(false);
            int top = row-1<0 ? 0 : row-1;
            int bottom = row+1<maxRow ? row+1 : maxRow-1;
            int left = col-1<0 ? 0 : col-1;
            int right = col+1<maxCol ? col+1 : maxCol-1;
            for(int r = top;r <= bottom;r ++)
                for(int c = left;c <= right;c ++)
                    checkMine(r, c);
        }
    }

    private void flagMine(int row, int col) {
        if(mineButton[row][col].isClicked()) return;
        if(mineButton[row][col].getFlag() == 0) {
            flagNum ++;
        }else if(mineButton[row][col].getFlag() == 1) {
            flagNum --;
        }
        mineCounter.reset(mineNum - flagNum);
        mineButton[row][col].setFlag((mineButton[row][col].getFlag() + 1) % 3);
        mineButton[row][col].setIcon(flagStatus[mineButton[row][col].getFlag()]);
    }

    private void sweepMine(int row, int col) {
        if(! mineButton[row][col].isClicked()) return;
        int top = row-1<0 ? 0 : row-1;
        int bottom = row+1<maxRow ? row+1 : maxRow-1;
        int left = col-1<0 ? 0 : col-1;
        int right = col+1<maxCol ? col+1 : maxCol-1;
        int count = 0;
        for(int r = top;r <= bottom;r ++)
            for(int c = left;c <= right;c ++)
                if(mineButton[r][c].getFlag() == 1) count ++;
        if(count == mineMap.element(row, col))
            for(int r = top;r <= bottom;r ++)
                for(int c = left;c <= right;c ++)
                    checkMine(r, c);
    }


    private boolean isWin() {
        for(int row = 0;row < maxRow;row ++)
            for(int col = 0;col < maxCol;col ++)
                if(mineMap.element(row, col) != 9 && ! mineButton[row][col].isClicked())
                    return false;
        return true;
    }

    private void winGame() {
        timer.cancel();
        new OverDialog(this, true).setVisible(true);
    }

    private boolean isLose() {
        for(int row = 0;row < maxRow;row ++)
            for(int col = 0;col < maxCol;col ++)
                if(mineMap.element(row, col) == 9 && mineButton[row][col].isClicked())
                    return true;
        return false;
    }

    private void loseGame() {
        for(int row = 0;row < maxRow;row ++) {
            for(int col = 0;col < maxCol;col ++) {
                if(mineMap.element(row, col) == 9 && mineButton[row][col].isClicked()) {
                    mineButton[row][col].setIcon(bombStatus[2]);
                    Toolkit.getDefaultToolkit().beep();
                }else if(mineMap.element(row, col) == 9 && ! mineButton[row][col].isClicked()) {
                    mineButton[row][col].setIcon(bombStatus[0]);
                }else if(mineMap.element(row, col) != 9 && mineButton[row][col].getFlag() == 1) {
                    mineButton[row][col].setIcon(bombStatus[1]);
                }
            }
        }
        timer.cancel();
        faceBut.setIcon(faceIcon[1]);
        new OverDialog(this, false).setVisible(true);
    }

    public MineMap getMineMap() {
        return mineMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == aboutItem) {
            new AboutDialog(this).setVisible(true);
        }else if(src == newGameItem) {
            setNewGame(maxRow, maxCol, mineNum);
        }else if(src == easyItem) {
            setNewGame(EASY_MAX_ROW, EASY_MAX_COL, EASY_MINE_NUM);
        }else if(src == mediumItem) {
            setNewGame(MEDIUM_MAX_ROW, MEDIUM_MAX_COL, MEDIUM_MINE_NUM);
        }else if(src == expertItem) {
            setNewGame(EXPERT_MAX_ROW, EXPERT_MAX_COL, EXPERT_MINE_NUM);
        }else if(src == customItem) {
            new CustomDialog(this).setVisible(true);
        }else if(src == randomItem) {
            setRandomGame();
        }else if(src == exitItem) {
            System.exit(0);
        }
    }

    private boolean doubleClickFlag;
    private int clickTimes;

    @Override
    public void mouseClicked(final MouseEvent e) {
        if(e.getSource() == faceBut) {
            setNewGame(maxRow, maxCol, mineNum);
            return;
        }

        final int row = ((MineButton) e.getSource()).getRow();
        final int col = ((MineButton) e.getSource()).getCol();
        if(!isStarted && e.getButton() == MouseEvent.BUTTON1) startGame(row, col);

        doubleClickFlag = false;
        if(clickTimes == 1) {
            sweepMine(row, col);
            if(isWin()) winGame();
            if(isLose()) loseGame();
            clickTimes = 0;
            doubleClickFlag = true;
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(doubleClickFlag) {
                    clickTimes = 0;
                    this.cancel();
                    return;
                }
                if(clickTimes == 1) {
                    if(e.getButton() == MouseEvent.BUTTON1) {
                        checkMine(row, col);
                        if(isWin()) winGame();
                        if(isLose()) loseGame();
                    }else if(e.getButton() == MouseEvent.BUTTON3) {
                        flagMine(row, col);
                    }
                    doubleClickFlag = true;
                    clickTimes = 0;
                    this.cancel();
                    return;
                }
                clickTimes ++;
            }
        }, new Date(), 20);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public int getMineNum() {
        return mineNum;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        String os = System.getProperty("os.name");
        if (os == null) {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } else if (os.contains("Windows")) {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } else if (os.contains("Linux")) {
            UIManager.setLookAndFeel(new MotifLookAndFeel());
        } else if (os.contains("OS X")) {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } else {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        }

        new MSweeper("Mine Sweeper for Java").setVisible(true);
    }
}
