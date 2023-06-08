import java.awt.event.*;

public class Main {
  public static void main(String[] args) {
    AStar app = new AStar();
    Grid g = new Grid();
    MyFrame f = new MyFrame(g);
    f.runBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        AStar.Pair src = new AStar.Pair(g.src.row, g.src.column);
        AStar.Pair dest = new AStar.Pair(g.dest.row, g.dest.column);
        app.aStarSearch(g.grid, g.grid.length, g.grid[0].length, src, dest);
        f.drawPath(app.pathList);
      }
    });
  }
}
