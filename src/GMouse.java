import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    // for removing source/dest or toggling
    if (g.srcExist && g.destExist) {
      g.changeCell(rowIndex, colIndex);
      int value = g.grid[rowIndex][colIndex];
      if (value == 0) {
        source.setBackground(Color.darkGray);
      } else {
        source.setBackground(Color.WHITE);
      }
    } else if (!g.srcExist) {
      source.setBackground(Color.BLUE);
      g.changeCell(rowIndex, colIndex);
      // g.setSource(rowIndex, colIndex);
    } else if (!g.destExist) {
      source.setBackground(Color.ORANGE);
      g.changeCell(rowIndex, colIndex);
    }

    System.out.println("Clicked: " + source.getText());
    System.out.println("Index: [" + rowIndex + ", " + colIndex + "]");
  }

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
