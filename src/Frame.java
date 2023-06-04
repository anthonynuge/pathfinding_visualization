import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

public class Frame {
  private JFrame frame;
  private JPanel optionPanel;
  private JPanel gridPanel;
  private JPanel menuPanel;
  private JButton button1;
  private JButton button2;
  private int width;
  private int height;
  private JLabel[][] labelArr;
  private int x;
  // private JPanel cell;

  // constructor
  public Frame(int w, int h, int[][] grid) {
    x = grid.length;
    frame = new JFrame();
    frame.setLayout(new BorderLayout());

    // gridPanel = new JPanel(new GridLayout(25, 25));
    gridPanel = new JPanel(new GridLayout(grid.length, grid.length));
    gridPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

    optionPanel = new JPanel(new GridLayout(1, 2));
    menuPanel = new JPanel();

    optionPanel.setPreferredSize(new Dimension(100, 250));
    menuPanel.setPreferredSize(new Dimension(100, 50));

    button1 = new JButton("Test");
    button2 = new JButton("Test 2");
    width = w;
    height = h;
    setUpGUI(grid);
  }

  public void setUpGrid(JPanel gp, int[][] grid) {
    labelArr = new JLabel[x][x];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < x; j++) {
        labelArr[i][j] = new JLabel("(" + i + "," + j + ")");
        labelArr[i][j].setOpaque(true);
        // labelArr[i][j].setBackground(Color.WHITE);

        if (grid[i][j] == 0) {
          labelArr[i][j].setBackground(Color.darkGray);
        } else if (grid[i][j] == 1) {
          labelArr[i][j].setBackground(Color.white);
        }

        GMouse mouse = new GMouse(i, j, grid[i][j]);
        labelArr[i][j].addMouseListener(mouse);
        gp.add(labelArr[i][j]);
      }
    }
  }

  public void setUpGUI(int[][] grid) {
    frame.setSize(width, height);
    frame.setTitle("A* Path Finding Visualizer");

    // frame.add(setUpGrid(gridPanel, 10));
    setUpGrid(gridPanel, grid);
    frame.add(gridPanel, BorderLayout.CENTER);

    optionPanel.add(button1);
    optionPanel.add(button2);
    // optionPanel.setBounds(50, 800, 1500, 150);
    frame.add(menuPanel, BorderLayout.NORTH);
    frame.add(optionPanel, BorderLayout.SOUTH);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
