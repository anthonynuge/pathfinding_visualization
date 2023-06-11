import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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
  public JPanel centerContainer;
  private int x; // grid size
  private int y;
  Timer timer;

  public MyFrame(Grid g) {
    x = g.grid.length;
    y = g.grid[0].length;
    setTitle("A* Path Finding Visualizer");
    setLayout(new BorderLayout());
    centerContainer = new JPanel();
    centerContainer.setBackground(Color.cyan);
    centerContainer.setLayout(null);
    

    setUpMenu();
    setUpGrid(g);
    centerContainer.setLayout(null);
    centerContainer.setPreferredSize(new Dimension(1600, 960));
    centerContainer.add(gridPanel);

    setUpOptions(g);

    add(menuPanel, BorderLayout.NORTH);
    add(centerContainer, BorderLayout.CENTER);
    add(optionPanel, BorderLayout.SOUTH);

    setSize(1600, 1000);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void setUpGrid(Grid g) {
    gridPanel = new JPanel(new GridLayout(x, y));
    gridPanel.setSize(1600, 960);
    gridPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    labelArr = new JLabel[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        // labelArr[i][j] = new JLabel("(" + i + "," + j + ")");
        labelArr[i][j] = new JLabel();
        labelArr[i][j].setOpaque(true);
        labelArr[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
    optionPanel.setPreferredSize(new Dimension(100, 40));
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

  public void clearPath(ArrayList<AStar.Pair> path) {
    int endIndex = path.size() - 1;
    for (int i = 1; i < endIndex; i++) {
      int first = path.get(i).first;
      int second = path.get(i).second;
      labelArr[first][second].setBackground(Color.WHITE);
    }
  }

  // delays the color change when drawing the path;
  public void drawPath(ArrayList<AStar.Pair> path) {
    clearPath(path);
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
  public void createLayer(ArrayList<AStar.Pair> path) {
    JDialog layerDialog = new JDialog(this);
    layerDialog.setModal(true);
    layerDialog.setUndecorated(true);
    layerDialog.setBackground(new Color(0, 0, 0, 0));

    layerDialog.setSize(getSize());
    layerDialog.setLocation(getLocation());

    layerDialog.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.out.println("Layer Clicked");
        clearPath(path);
        layerDialog.dispose();
      }
    });
    layerDialog.setVisible(true);
  }
}