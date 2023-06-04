public class Main {

  public static void main(String[] args) {
    int[][] grid = {
        { 1, 1, 0, 0, 1, 0, 0, 0 },
        { 1, 0, 0, 1, 1, 0, 1, 0 },
        { 1, 1, 0, 1, 0, 0, 1, 0 },
        { 1, 1, 0, 1, 1, 1, 1, 1 },
        { 1, 1, 0, 0, 0, 1, 1, 1 },
        { 0, 1, 1, 1, 0, 1, 1, 0 },
        { 1, 1, 0, 1, 1, 1, 1, 0 },
        { 0, 1, 1, 1, 1, 1, 1, 1 }
    };

    System.out.println("Hello World");
    Frame f = new Frame(1000, 1000, grid);

  }
}
