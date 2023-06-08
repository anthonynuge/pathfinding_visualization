import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JFrame;

public class MyFrame extends JFrame {
  private JPanel optionPanel;
  private JPanel gridPanel;
  private JPanel menuPanel;
  private JButton printBtn;
  public JButton runBtn;
  public JLabel[][] labelArr;
  private int x; // grid size
  Timer timer;

  public MyFrame(Grid g) {
    x = g.grid.length;
    setTitle("A* Path Finding Visualizer");
    setLayout(new BorderLayout());

    setUpMenu();
    setUpGrid(g);
    setUpOptions(g);

    add(menuPanel, BorderLayout.NORTH);
    add(gridPanel, BorderLayout.CENTER);
    add(optionPanel, BorderLayout.SOUTH);

    setSize(1000, 1000);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void setUpGrid(Grid g) {
    gridPanel = new JPanel(new GridLayout(x, x));
    gridPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    labelArr = new JLabel[x][x];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < x; j++) {
        labelArr[i][j] = new JLabel("(" + i + "," + j + ")");
        labelArr[i][j].setOpaque(true);
        int value = g.grid[i][j];
        switch (value) {
          case 0:
            labelArr[i][j].setBackground(Color.darkGray);
            break;
          case 1:
            labelArr[i][j].setBackground(Color.white);
            break;
        }

        GMouse gridMouse = new GMouse(i, j, g);
        labelArr[i][j].addMouseListener(gridMouse);
        gridPanel.add(labelArr[i][j]);
      }
    }
    labelArr[g.src.row][g.src.column].setBackground(Color.blue);
    labelArr[g.dest.row][g.dest.column].setBackground(Color.orange);
  }

  public void setUpOptions(Grid g) {
    optionPanel = new JPanel(new GridLayout(1, 2));
    optionPanel.setPreferredSize(new Dimension(100, 250));
    printBtn = new JButton("Print");
    printBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        g.printGrid();
      }
    });
    runBtn = new JButton("Run");
    optionPanel.add(printBtn);
    optionPanel.add(runBtn);
  }

  public void setUpMenu() {
    menuPanel = new JPanel();
    menuPanel.setPreferredSize(new Dimension(100, 50));
  }

  // delays the color change when drawing the path;
  public void drawPath(ArrayList<AStar.Pair> path) {
    int delay = 500;
    int endIndex = path.size() - 1;
    timer = new Timer(delay, new ActionListener() {
      int index = 1;

      @Override
      public void actionPerformed(ActionEvent e) {
        if (index < endIndex) {
          int first = path.get(index).first;
          int second = path.get(index).second;
          labelArr[first][second].setBackground(Color.PINK);
          index++;
        } else {
          timer.stop();
        }
      }
    });
    timer.start();
  }
}
