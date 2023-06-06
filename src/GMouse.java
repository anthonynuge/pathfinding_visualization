import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GMouse extends MouseAdapter {
  private boolean isHolding = false;
  private int rowIndex;
  private int colIndex;
  private Grid g;

  public GMouse(int rowIndex, int colIndex, Grid g) {
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
    this.g = g;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    JLabel source = (JLabel) e.getSource();
    int value = g.grid[rowIndex][colIndex];

    if (g.srcExist && g.destExist) {
      switch (value) {
        case 0:
          source.setBackground(Color.WHITE);
          g.changeCell(rowIndex, colIndex);
          break;
        case 1:
          source.setBackground(Color.darkGray);
          g.changeCell(rowIndex, colIndex);
          break;
        case 2:
          source.setBackground(Color.WHITE);
          g.changeCell(rowIndex, colIndex);
          break;
        case 3:
          source.setBackground(Color.WHITE);
          g.changeCell(rowIndex, colIndex);
          break;
      }

    } else if (!g.srcExist) {
      source.setBackground(Color.BLUE);
      g.setSource(rowIndex, colIndex);
    } else if (!g.destExist) {
      source.setBackground(Color.ORANGE);
      g.setDest(rowIndex, colIndex);
    }

    // if (value == 0) {
    // source.setBackground(Color.WHITE);
    // g.changeCell(rowIndex, colIndex);
    // }
    // if (value == 1) {
    // source.setBackground(Color.darkGray);
    // g.changeCell(rowIndex, colIndex);
    // }
    // if (value == 2) {
    // source.setBackground(Color.WHITE);
    // g.changeCell(rowIndex, colIndex);
    // }
    // if (value == 3) {
    // source.setBackground(Color.WHITE);
    // g.changeCell(rowIndex, colIndex);
    // }
    System.out.println("Clicked: " + source.getText());
    System.out.println("Index: [" + rowIndex + ", " + colIndex + "]");
  }

  // @Override
  // public void mouseEntered(MouseEvent e) {
  // JLabel source = (JLabel) e.getSource();
  // System.out.println("" + rowIndex + "," + colIndex);
  // //source.setBackground(Color.YELLOW);

  // if (isHolding == true) {
  // source.setBackground(Color.white);
  // value = 1;
  // System.out.println("" + rowIndex + ", " + colIndex + " value =" + value);
  // }
  // }

  // @Override
  // public void mouseExited(MouseEvent e) {
  // JLabel source = (JLabel) e.getSource();
  // source.setBackground(Color.WHITE);
  // }

  @Override
  public void mousePressed(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      isHolding = true;
      System.out.println(isHolding);
    }

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      isHolding = false;
      System.out.println(isHolding);
    }
  }

}
