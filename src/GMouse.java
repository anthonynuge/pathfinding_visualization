import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GMouse extends MouseAdapter {
  static boolean isHolding = false;
  static boolean holdingErase = false;
  private boolean special = false; // for cells that represent src or dest skip over when hold interacting
  private int rowIndex;
  private int colIndex;
  private Grid g;

  public GMouse(int rowIndex, int colIndex, Grid g) {
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
    this.g = g;

    if (g.src.row == rowIndex && g.src.column == colIndex) {
      this.special = true;
    }
    if (g.dest.row == rowIndex && g.dest.column == colIndex) {
      this.special = true;
    }
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
        if (special) {
          special = false;
        }
      }
      // if either src or dest does not exist
    } else if (!g.srcExist) {
      source.setBackground(Color.BLUE);
      special = true;
      g.changeCell(rowIndex, colIndex);
      // g.setSource(rowIndex, colIndex);
    } else if (!g.destExist) {
      special = true;
      source.setBackground(Color.ORANGE);
      g.changeCell(rowIndex, colIndex);
    }

    // System.out.println("Clicked: " + source.getText());
    System.out.println("Index: [" + rowIndex + ", " + colIndex + "]");
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      isHolding = true;
    }
    if (SwingUtilities.isRightMouseButton(e)) {
      holdingErase = true;
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      isHolding = false;
    }
    if (SwingUtilities.isRightMouseButton(e)) {
      holdingErase = false;
    }
  }

  @Override 
  public void mouseEntered(MouseEvent e) {
    JLabel source = (JLabel) e.getSource();
    if (isHolding && !special) {
      g.placeBlocked(rowIndex, colIndex);
      source.setBackground(Color.DARK_GRAY);
    }
    if (holdingErase && !special) {
      g.placeOpen(rowIndex, colIndex);
      source.setBackground(Color.WHITE);
    }
  }
}
