package mazeGeneration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * RDMaze
 */
public class RDMaze {
  int width;
  int height;
  int[][] maze;
  Random rand = new Random();
  int srcRow = 1;
  int srcCol = 1;
  int destRow = 23;
  int destCol = 42;
  ArrayList<Passage> passageList;

  public static class Passage {
    int row;
    int col;
    int orientation;

    public Passage(int row, int col, int orientation) {
      this.row = row;
      this.col = col;
      this.orientation = orientation;
    }
  }

  public RDMaze(int width, int height) {
    this.width = width; // 10
    this.height = height; // 10
    this.maze = new int[height][width];
    passageList = new ArrayList<Passage>();
  }

  // main function for creating the maze calls all the other functions
  public int[][] generateMaze() {
    for (int[] row : maze) {
      Arrays.fill(row, 1);
    }
    Arrays.fill(maze[0], 0);
    Arrays.fill(maze[height - 1], 0);
    for (int i = 0; i < height; i++) {
      maze[i][0] = 0;
      maze[i][width - 1] = 0;
    }

    divide(height - 2, 1, width - 2, 1); // (8, 1, 8, 1)
    openPassages();

    return maze;
  }

  public void divide(int rowMax, int rowMin, int colMax, int colMin) {
    if ((rowMax - rowMin) < 2 || (colMax - colMin) < 2) {
      return;
    }

    int orientation = getOrientation(rowMax, rowMin, colMax, colMin);
    int wallIndex;
    int passageIndex;
    switch (orientation) {
      case 0: // vertical wall
        // wallIndex = rand.nextInt(colMin + 1, colMax);
        wallIndex = getRandomRange(colMin + 1, colMax - 1);
        for (int i = rowMin; i <= rowMax; i++) {
          maze[i][wallIndex] = 0;
        }
        passageIndex = rand.nextInt(rowMin, rowMax);
        maze[passageIndex][wallIndex] = 1;
        addPassage(passageIndex, wallIndex, orientation);

        // left side
        divide(rowMax, rowMin, wallIndex - 1, colMin);
        divide(rowMax, rowMin, colMax, wallIndex + 1);

        break;

      case 1: // horizontal wall
        // wallIndex = rand.nextInt(rowMin + 1, rowMax);
        wallIndex = getRandomRange(rowMin + 1, rowMax - 1);
        for (int i = colMin; i <= colMax; i++) {
          maze[wallIndex][i] = 0;
        }
        passageIndex = rand.nextInt(colMin, colMax);
        maze[wallIndex][passageIndex] = 1;
        addPassage(wallIndex, passageIndex, orientation);
        divide(wallIndex - 1, rowMin, colMax, colMin);
        divide(rowMax, wallIndex + 1, colMax, colMin);
        break;
    }
  }

  // function for determine which orientation to generate the walls divides up the
  // longer side for a more even grid
  public int getOrientation(int rowMax, int rowMin, int colMax, int colMin) {
    if ((rowMax - rowMin) < (colMax - colMin)) {
      return 0; // vertical
    } else if ((colMax - colMin) < (rowMax - rowMin)) {
      return 1; // horizontal
    } else {
      return rand.nextInt(2);
    }
  }

  // random interfer within a range inclusive on both ends
  private int getRandomRange(int minInlcusive, int maxInclusive) {
    if (minInlcusive >= maxInclusive) {
      System.out.println("min: " + minInlcusive + " max: " + maxInclusive);
    }
    return rand.nextInt((maxInclusive - minInlcusive) + 1) + minInlcusive;
  }

  // debugging helper
  public void printMaze() {
    int counter = 0;
    System.out.print(" ");
    for (int c = 0; c < width; c++) {
      System.out.print(" " + c + " ");
    }
    System.out.println();

    for (int i = 0; i < height; i++) {
      System.out.print(counter);
      for (int j = 0; j < width; j++) {
        if (maze[i][j] == 1) {
          if (i == srcRow && j == srcCol) {
            System.out.print(" S ");
          } else if (i == destRow && j == destCol) {
            System.out.print(" D ");
          } else {
            System.out.print("   ");
          }
        } else {
          System.out.print(" # ");
        }
      }

      System.out.println();
      counter++;
    }
    System.out.println("---------------------------------------------------");
  }

  // create and stores cordinates of the passages for later clearing
  public void addPassage(int row, int col, int orientation) {
    Passage p = new Passage(row, col, orientation);
    passageList.add(p);
  }

  // after generating the maze goes back and makes sures the passages are not
  // blocked opening it up if it is
  public void openPassages() {
    for (Passage p : passageList) {
      switch (p.orientation) {
        case (0):
          maze[p.row][p.col - 1] = 1;
          maze[p.row][p.col + 1] = 1;
          break;
        case (1):
          maze[p.row + 1][p.col] = 1;
          maze[p.row - 1][p.col] = 1;
          break;
      }
    }
  }
}
