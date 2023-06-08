
/**
 * Grid
 * values of 0=blocked; 1=open;
 */
import java.util.Arrays;

public class Grid {
  boolean srcExist = true;
  boolean destExist = true;
  GridObj src;
  GridObj dest;

  public Grid() {
    src = new GridObj(0, 0);
    dest = new GridObj(6, 6);
  }

  int[][] grid = { // default grid
      { 1, 1, 0, 0, 1, 0, 0, 0 },
      { 1, 0, 0, 1, 1, 0, 1, 0 },
      { 1, 1, 0, 1, 0, 0, 1, 0 },
      { 1, 1, 0, 1, 1, 1, 1, 1 },
      { 1, 1, 0, 0, 0, 1, 1, 1 },
      { 0, 1, 1, 1, 0, 1, 1, 0 },
      { 1, 1, 0, 1, 1, 1, 1, 0 },
      { 0, 1, 1, 1, 1, 1, 1, 1 }
  };

  public void placeBlocked(int row, int column) {
    grid[row][column] = 0;
  }

  public void placeOpen(int row, int column) {
    grid[row][column] = 1;
  }

  // for changing one cell;
  public void changeCell(int row, int column) {
    int value = grid[row][column];
    if (!srcExist) {
      setSource(row, column);
    } else if (!destExist) {
      setDest(row, column);
    } else if (src.row == row && src.column == column) {
      srcExist = false;
    } else if (dest.row == row && dest.column == column) {
      destExist = false;
    } else if (value == 1) {
      grid[row][column] = 0;
    } else if (value == 0) {
      grid[row][column] = 1;
    }
  }

  public void setSource(int row, int column) {
    src.setPosition(row, column);
    grid[row][column] = 1;
    srcExist = true;
  }

  public void setDest(int row, int column) {
    dest.setPosition(row, column);
    grid[row][column] = 1;
    destExist = true;
  }

  // clears the array sets all values to 1 / open
  public void clear() {
    for (int[] row : grid) {
      Arrays.fill(row, 1);
      srcExist = false;
      destExist = false;
    }
    // debug
    System.out.println("cleared\n");
  }

  // prints the array to terminal for debugging
  public void printGrid() {
    for (int[] row : grid) {
      System.out.println(Arrays.toString(row));
    }
    // debug
    System.out.println();
  }

  class GridObj {
    int row;
    int column;

    GridObj(int row, int column) {
      this.row = row;
      this.column = column;
    }

    public void setPosition(int newRow, int newColumn) {
      row = newRow;
      column = newColumn;
    }

    public void printCord() {
      System.out.println("(" + row + ", " + column + ")");
    }
  }
}
