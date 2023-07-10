class DFS extends Maze {
  Cell[][] maze = super.maze;
  int height;
  int width;
  
  public DFS(int x, int y) {
    super(x + 2, y + 2);
    height = x;
    width = y;
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

  public double difficulty(int x, int y) {
    return Math.round((double) x * (double) y / 1);
  }
  
  public void carve(int x, int y, double seconds) {
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
          carve(x - 2, y, seconds);
        } else if (i == 2 && x < maze.length - 2 && !maze[x + 2][y].isVisited()) {
          super.cutDown(x, y);
          if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
          }
          carve(x + 2, y, seconds);
        } else if (i == 3 && y > 1 && !maze[x][y - 2].isVisited()) {
          super.cutLeft(x, y);
          if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
          }
          carve(x, y - 2, seconds);
        } else if (i == 4 && y < maze[0].length - 2 && !maze[x][y + 2].isVisited()) {
          super.cutRight(x, y);
          if (seconds != 0) {
            System.out.print("\033[H\033[2J"); 
            show();
            super.wait(seconds);
          }
          carve(x, y + 2, seconds);
        }
      }
    }
  }
}
