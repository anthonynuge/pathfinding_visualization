
/**
 * Grid
 * values of 0=blocked; 1=open; 2=src; 3= dest;
 */
import java.util.Arrays;

public class Grid {
  boolean srcExist = true;
  boolean destExist = true;
  GridObj src;
  GridObj dest;

  public Grid() {
    src = new GridObj(0, 0, 2);
    dest = new GridObj(6, 6, 3);
  }

  int[][] grid = { // default grid
      { 2, 1, 0, 0, 1, 0, 0, 0 },
      { 1, 0, 0, 1, 1, 0, 1, 0 },
      { 1, 1, 0, 1, 0, 0, 1, 0 },
      { 1, 1, 0, 1, 1, 1, 1, 1 },
      { 1, 1, 0, 0, 0, 1, 1, 1 },
      { 0, 1, 1, 1, 0, 1, 1, 0 },
      { 1, 1, 0, 1, 1, 1, 3, 0 },
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
    switch (value) {
      case 0:
        grid[row][column] = 1;
        break;
      case 1:
        grid[row][column] = 0;
        break;
      case 2:
        grid[row][column] = 1;
        srcExist = false;
        break;
      case 3:
        grid[row][column] = 1;
        destExist = false;
        break;
    }
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
    int value;

    GridObj(int row, int column, int value) {
      this.row = row;
      this.column = column;
      this.value = value;
    }

    public void setPosition(int newRow, int newColumn) {
      row = newRow;
      column = newColumn;
    }

    public void printCord() {
      System.out.println("(" + row + ", " + column + ")");
    }
  }

  public static void main(String[] args) {
    Grid g = new Grid();
    g.printGrid();

    Grid.GridObj src = g.new GridObj(0, 0, 2);
    System.out.println(src.row);

    src.setPosition(1, 3);
    src.printCord();
  }
}
