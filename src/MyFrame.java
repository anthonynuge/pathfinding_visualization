import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MyFrame extends JFrame {
  private JPanel optionPanel;
  private JPanel gridPanel;
  private JPanel menuPanel;
  private JButton printBtn;
  public JButton runBtn;
  private JButton clearBtn;
  private JButton generateBtn;
  private JComboBox<String> algoOptions;
  public JLabel[][] labelArr;
  public JPanel centerContainer;
  private int x; // grid size
  private int y;
  private Grid griddy;
  Timer timer;

  public MyFrame(Grid g) {
    griddy = g;
    x = g.grid.length;
    y = g.grid[0].length;
    // setSize(1800, 1000);
    setTitle("A* Path Finding Visualizer");
    setLayout(new BorderLayout());
    // setUpMenu(g);
    setUpGrid(g);
    setUpMenu(g);

    setUpCenterContainer();
    centerContainer.add(gridPanel);

    setUpOptions(g);

    add(menuPanel, BorderLayout.NORTH);
    add(centerContainer, BorderLayout.CENTER);
    // add(optionPanel, BorderLayout.SOUTH);

    // setSize(1800, 1000);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  private void setUpCenterContainer() {
    centerContainer = new JPanel();
    centerContainer.setPreferredSize(new Dimension(1600, 920));
    centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
    // centerContainer.setLayout(null);
    centerContainer.setPreferredSize(new Dimension(1600, 960));
  }

  public void setUpGrid(Grid g) {
    gridPanel = new JPanel(new GridLayout(x, y));
    gridPanel.setSize(1600, 920);
    // gridPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    labelArr = new JLabel[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        // labelArr[i][j] = new JLabel("(" + i + "," + j + ")");
        labelArr[i][j] = new JLabel();
        labelArr[i][j].setPreferredSize(new Dimension(20, 20));
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
    optionPanel = new JPanel();
    // optionPanel = new JPanel(new GridLayout(1, 1));
    optionPanel.setBackground(Color.GREEN);
    // optionPanel = new JPanel();
    optionPanel.setPreferredSize(new Dimension(100, 10));
    printBtn = new JButton("Print");
    printBtn.setPreferredSize(new Dimension(150, 30));
    printBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        g.printGrid();
      }
    });
    optionPanel.add(printBtn);
  }

  // menu
  // setup-------------------------------------------------------------------------------------------------------------
  public void setUpMenu(Grid g) {
    Dimension btnDim = new Dimension(150, 30);
    menuPanel = new JPanel();
    menuPanel.setPreferredSize(new Dimension(100, 50));

    createCombo();
    menuPanel.add(algoOptions);

    generateBtn = new JButton("Generate Maze");
    generateBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        g.generate();
        for (int i = 0; i < g.grid.length; i++) {
          for (int j = 0; j < g.grid[0].length; j++) {
            if (g.grid[i][j] == 0) {
              labelArr[i][j].setBackground(Color.DARK_GRAY);
            } else {
              labelArr[i][j].setBackground(Color.WHITE);
            }
          }
        }
        labelArr[g.src.row][g.src.column].setBackground(Color.BLUE);
        labelArr[g.dest.row][g.dest.column].setBackground(Color.ORANGE);
      }
    });
    menuPanel.add(generateBtn);

    clearBtn = new JButton("Clear");
    clearBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        g.clear();
        for (int i = 0; i < labelArr.length; i++) {
          for (int j = 0; j < labelArr[0].length; j++) {
            labelArr[i][j].setBackground(Color.white);
          }
        }
        labelArr[g.src.row][g.src.column].setBackground(Color.BLUE);
        labelArr[g.dest.row][g.dest.column].setBackground(Color.ORANGE);
      }
    });
    // clearBtn.setPreferredSize(new Dimension(150, 30));
    clearBtn.setPreferredSize(btnDim);
    menuPanel.add(clearBtn);

    runBtn = new JButton("Run");
    runBtn.setPreferredSize(btnDim);
    menuPanel.add(runBtn);

    printBtn = new JButton("Print");
    printBtn.setPreferredSize(btnDim);
    printBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        griddy.printGrid();
        // g.printGrid();
      }
    });
    menuPanel.add(printBtn);
  }

  public void clearPath() {
    for (int i = 0; i < griddy.grid.length; i++) {
      for (int j = 0; j < griddy.grid[0].length; j++) {
        if (griddy.grid[i][j] == 1) {
          labelArr[i][j].setBackground(Color.WHITE);
        }
        if (griddy.grid[i][j] == 0) {
          labelArr[i][j].setBackground(Color.DARK_GRAY);
        }
      }
    }
    labelArr[griddy.src.row][griddy.src.column].setBackground(Color.BLUE);
    labelArr[griddy.dest.row][griddy.dest.column].setBackground(Color.ORANGE);
  }

  private void createCombo() {
    String[] pathFindingOptions = { "A*", "Depth-First" };
    algoOptions = new JComboBox<>(pathFindingOptions);
    algoOptions.setSelectedIndex(0);
  }

  // delays the color change when drawing the path;
  public void drawPath(ArrayList<AStar.Pair> path) {
    clearPath();
    int delay = 100;
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
    createLayer();
  }

  // layer if clicked on stops the animation and resests the color
  public void createLayer() {
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
        timer.stop();
        clearPath();
        layerDialog.dispose();
      }
    });
    layerDialog.setVisible(true);
  }
}
