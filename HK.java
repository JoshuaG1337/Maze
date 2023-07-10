import java.util.*;

class HK extends Maze {
  Cell[][] maze = super.maze;
  
  public HK(int x, int y) {
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
  
  private int getTotalCells() {
    return (super.maze.length / 2) * (super.maze[0].length / 2);
  }
  
  private int frontiersVisited(int x, int y) {
    int sum = 0;
    if (x > 1 && super.maze[x - 2][y].isVisited()) sum++;
    if (x < super.maze.length - 2 && super.maze[x + 2][y].isVisited()) sum++;
    if (y > 1 && super.maze[x][y - 2].isVisited()) sum++;
    if (y < super.maze[0].length - 2 && super.maze[x][y + 2].isVisited()) sum++;
    return sum;
  }

  public void carve(int x, int y, double seconds) {
    for (int n = 0; n < getTotalCells(); n++) {
      super.maze[x][y].setVisited(true);
      if (!super.deadEndV(x, y)) {
        for (int i : super.randomDirections()) {
          if (i == 1 && x > 1 && !maze[x - 2][y].isVisited()) {
            super.cutUp(x, y);
            if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
            }
            x -= 2;
            break;
          } else if (i == 2 && x < super.maze.length - 2 && !maze[x + 2][y].isVisited()) {
            super.cutDown(x, y);
            if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
            }
            x += 2;
            break;
          } else if (i == 3 && y > 1 && !maze[x][y - 2].isVisited()) {
            super.cutLeft(x, y);
            if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
            }
            y -= 2;
            break;
          } else if (i == 4 && y < super.maze[0].length - 2 && !maze[x][y + 2].isVisited()) {
            super.cutRight(x, y);
            if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
            }
            y += 2;
            break;
          }
        }
      } else {
        outer: 
        for (int r = 1; r < super.maze.length - 1; r += 2) {
          for (int c = 1; c < super.maze[0].length - 1; c += 2) {
            if (!maze[r][c].isVisited() && frontiersVisited(r, c) > 0) {
              for (int i : super.randomDirections()) {
                if (i == 1 && r > 1 && super.maze[r - 2][c].isVisited()) {
                  super.cutUp(r, c);
                  break;
                } else if (i == 2 && r < super.maze.length - 2 && super.maze[r + 2][c].isVisited()) {
                  super.cutDown(r, c);
                  break;
                } else if (i == 3 && c > 1 && super.maze[r][c - 2].isVisited()) {
                  super.cutLeft(r, c);
                  break;
                } else if (i == 4 && c < super.maze[0].length - 2 && super.maze[r][c + 2].isVisited()) {
                  super.cutRight(r, c);
                  break;
                }
              }
              x = r;
              y = c;
              break outer;
            }
          }
        }
      }
    }
  }
}
