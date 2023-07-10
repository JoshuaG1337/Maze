import java.util.*;

class Prim extends Maze {
  Cell[][] maze = super.maze;
  
  public Prim(int x, int y) {
    super(x + 2, y + 2);
  }

  @Override public void show() {
    String str = "";
    for (Cell[] i : super.maze) {
      for (Cell x : i) {
        str += x;
      }
      str += "\n";
    }
    System.out.println(str);
  }
  
  private void markFrontiers(int x, int y, ArrayList<Integer[]> frontiers) {
    if (x > 1 && !maze[x - 2][y].isVisited() && !super.contain(frontiers, new Integer[] {x - 2, y})) frontiers.add(new Integer[] {x - 2, y});
    if (x < super.maze.length - 2 && !maze[x + 2][y].isVisited() && !super.contain(frontiers, new Integer[] {x + 2, y})) frontiers.add(new Integer[] {x + 2, y});
    if (y > 1 && !maze[x][y - 2].isVisited() && !super.contain(frontiers, new Integer[] {x, y - 2})) frontiers.add(new Integer[] {x, y - 2});
    if (y < super.maze[0].length - 2 && !maze[x][y + 2].isVisited() && !super.contain(frontiers, new Integer[] {x, y + 2})) frontiers.add(new Integer[] {x, y + 2});
  }

  public void carve(int x, int y, double seconds) {
    ArrayList<Integer[]> frontiers = new ArrayList<Integer[]>();
    Integer[] temp = new Integer[2];
    do {
      super.maze[x][y].setVisited(true);
      markFrontiers(x, y, frontiers);
      temp = frontiers.get((int) (Math.random() * frontiers.size()));
      frontiers.remove(temp);
      for (int i : super.randomDirections()) {
        if (i == 1 && temp[0] > 1 && super.maze[temp[0] - 2][temp[1]].isVisited()) {
          super.cutUp(temp[0], temp[1]);
          if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
          }
          break;
        } else if (i == 2 && temp[0] < super.maze.length - 2 && super.maze[temp[0] + 2][temp[1]].isVisited()) {
          super.cutDown(temp[0], temp[1]);
          if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
          }
          break;
        } else if (i == 3 && temp[1] > 1 && super.maze[temp[0]][temp[1] - 2].isVisited()) {
          super.cutLeft(temp[0], temp[1]);
          if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
          }
          break;
        } else if (i == 4 && temp[1] < super.maze[0].length - 2 && super.maze[temp[0]][temp[1] + 2].isVisited()) {
          super.cutRight(temp[0], temp[1]);
          if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
          }
          break;
        }
      }
      x = temp[0];
      y = temp[1];
    } while (frontiers.size() > 0);
  }
}
