package diningphilosopher;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class AppGUI extends JFrame implements OnPhilosopherActionListenter, ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String[] eatingResources = {"resources/eating-background-left.PNG",
            "resources/eating-background-right.PNG"};
    private String[] thinkingResources = {"resources/thinking-background-left.PNG",
            "resources/thinking-background-right.PNG"};
    private String[] grabAndReleaseForkResources = {"resources/0.PNG", "resources/1.PNG", "resources/2.PNG",
            "resources/3.PNG", "resources/4.PNG", "resources/5.PNG", "resources/6.PNG", "resources/7.PNG",
            "resources/8.PNG", "resources/9.PNG"};
    private String[] hungryResources = {"resources/hungry-background-left.PNG",
            "resources/hungry-background-right.PNG"};
    private JLabel[] actionLabels = new JLabel[5];
    private JLabel backgroundLabel;
    private JButton btnExit;
    private JButton btnPauseOrPlay;
    private JButton btnReset;
    private static DiningTable table;
    private boolean isPlay = true;
    private boolean isReset = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppGUI frame = new AppGUI();
                    table = new DiningTable();
                    table.initTable(frame);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AppGUI() {
        setTitle("DINING PHILOSOPHER SIMULATOR");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 830, 700);
        init();
    }

    private void init() {
        contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(28, 10, 1000, 600);
        panel.setLayout(null);
        contentPane.add(panel);

        backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon("resources/bgr.PNG"));
        backgroundLabel.setBounds(103, 20, 600, 600);
        panel.add(backgroundLabel);

        actionLabels[0] = new JLabel();
        actionLabels[0].setIcon(new ImageIcon(thinkingResources[0]));
        actionLabels[0].setBounds(330, 0, 186, 113);
        panel.add(actionLabels[0]);

        actionLabels[1] = new JLabel();
        actionLabels[1].setIcon(new ImageIcon(thinkingResources[0]));
        actionLabels[1].setBounds(640, 200, 186, 113);
        panel.add(actionLabels[1]);

        actionLabels[2] = new JLabel();
        actionLabels[2].setIcon(new ImageIcon(thinkingResources[0]));
        actionLabels[2].setBounds(640, 370, 186, 113);
        panel.add(actionLabels[2]);

        actionLabels[3] = new JLabel();
        actionLabels[3].setIcon(new ImageIcon(thinkingResources[0]));
        actionLabels[3].setBounds(0, 400, 180, 118);
        panel.add(actionLabels[3]);

        actionLabels[4] = new JLabel();
        actionLabels[4].setIcon(new ImageIcon(thinkingResources[0]));
        actionLabels[4].setBounds(0, 175, 180, 118);
        panel.add(actionLabels[4]);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBounds(120, 600, 571, 48);
        contentPane.add(buttonPanel);

        btnReset = new JButton("Reset");
        btnReset.setBounds(135, 11, 89, 23);
        btnReset.addActionListener(this);
        buttonPanel.add(btnReset);

        btnPauseOrPlay = new JButton("Pause");
        btnPauseOrPlay.setBounds(254, 11, 89, 23);
        btnPauseOrPlay.addActionListener(this);
        buttonPanel.add(btnPauseOrPlay);

        btnExit = new JButton("Exit");
        btnExit.addActionListener(this);
        btnExit.setBounds(366, 11, 89, 23);
        buttonPanel.add(btnExit);
    }

    @Override
    public void onThinking(Philosopher philosopher) {
        int id = philosopher.getPhilId();
        if (id == 0 || id == 2 || id == 1) {
            try {
                actionLabels[id].setIcon(new ImageIcon(thinkingResources[0]));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        } else {
            try {
                actionLabels[id].setIcon(new ImageIcon(thinkingResources[1]));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        putFork();
    }

    @Override
    public void onEating(Philosopher philosopher) {
        int id = philosopher.getPhilId();
        if (id == 0 || id == 1 || id == 2) {
            try {
                actionLabels[id].setIcon(new ImageIcon(eatingResources[0]));
            } catch (ArrayIndexOutOfBoundsException e) {
            }

        } else {
            try {
                actionLabels[id].setIcon(new ImageIcon(eatingResources[1]));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        putFork();
    }

    @Override
    public void onHungry(Philosopher philosopher) {
        int id = philosopher.getPhilId();
        if (id == 0 || id == 2 || id == 1) {
            try {
                actionLabels[id].setIcon(new ImageIcon(hungryResources[0]));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        } else {
            try {
                actionLabels[id].setIcon(new ImageIcon(hungryResources[1]));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }

    }

    private void putFork() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < actionLabels.length; i++) {
            String path = actionLabels[i].getIcon().toString();
            System.out.println("path in put Fork: " + path);
            if (eatingResources[0].equals(path) || eatingResources[1].equals(path)) {
                ids.add(i);
            }
        }
        if (ids.size() == 1) {
            try {
                System.out.println("put Fork with ids.size = 1 " + ids.get(0));
                backgroundLabel.setIcon(new ImageIcon(grabAndReleaseForkResources[ids.get(0)]));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        } else if (ids.size() == 2) {
            try {
                System.out.println("put Fork with ids.size = 2 " + (ids.get(0) + ids.get(1) + 3));
                backgroundLabel.setIcon(new ImageIcon(grabAndReleaseForkResources[ids.get(0) + ids.get(1) + 3]));
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnExit)) {
            System.exit(0);
        } else if (source.equals(btnPauseOrPlay)) {
            if (isPlay) {
                btnPauseOrPlay.setText("Play");
                isPlay = false;
                table.suspend();
            } else {
                if (isReset) {
                    table.play();
                    isReset = false;
                }
                btnPauseOrPlay.setText("Pause");
                isPlay = true;
                table.resume();
            }
        } else if (source.equals(btnReset)) {
            btnPauseOrPlay.setText("Play");
            table.reset();
            isReset = true;
            isPlay = false;
            resetUI();
        }
    }

    private void resetUI() {
        backgroundLabel.setIcon(new ImageIcon("resources/bgr.PNG"));
        for (int i = 0; i < actionLabels.length; i++) {
            if (i == 3 || i == 4) {
                try {
                    actionLabels[i].setIcon(new ImageIcon(thinkingResources[1]));
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            } else {
                try {
                    actionLabels[i].setIcon(new ImageIcon(thinkingResources[0]));
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }
}