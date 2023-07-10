import java.util.*;

class Maze {
  public Cell[][] maze;

  public Maze(int h, int w) {
    maze = new Cell[h][w];
    for (int i = 0; i < h; i++) {
      for (int x = 0; x < w; x++) {
        maze[i][x] = new Cell("  ");
      }
    }
  }

  public void addBorder() {
    for (int i = 0; i < maze[0].length; i++) {
      maze[0][i].setBlock("[]");
      maze[maze.length - 1][i].setBlock("[]");
    }
    for (int i = 0; i < maze.length; i++) {
      maze[i][0].setBlock("[]");
      maze[i][maze[0].length - 1].setBlock("[]");
    }
  }

  public void addExits() {
    maze[0][1].setBlock("  ");
    maze[maze.length - 1][maze[0].length - 2].setBlock("  ");
  }

  public void addVertices() {
    for (int i = 2; i < maze.length; i += 2) {
      for (int x = 2; x < maze[0].length; x += 2) {
        maze[i][x].setBlock("[]");
      }
    }
  }

  public void addWalls() {
    for (int i = 1; i < maze.length - 1; i++) {
      if (i % 2 != 0) {
        for (int x = 2; x < maze[0].length; x += 2) {
          maze[i][x].setBlock("[]");
        }
      } else {
        for (int x = 1; x < maze[0].length; x += 2) {
          maze[i][x].setBlock("[]");
        }
      }
    }
  }

  protected void wait(double seconds) {
    try {
      Thread.sleep((int) (seconds * 1000));
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  protected void show() {
    String str = "";
    for (Cell[] i : maze) {
      for (Cell x : i) {
        str += x;
      }
      str += "\n";
    }
    System.out.println(str);
    System.out.println("*Cannot be solved");
  }

  protected void cutUp(int x, int y) {
    maze[x - 1][y].setBlock("  ");
  }

  protected void cutDown(int x, int y) {
    maze[x + 1][y].setBlock("  ");
  }

  protected void cutLeft(int x, int y) {
    maze[x][y - 1].setBlock("  ");
  }

  protected void cutRight(int x, int y) {
    maze[x][y + 1].setBlock("  ");
  }

  protected boolean deadEndV(int x, int y) {
    return ((x > 1 && maze[x - 2][y].isVisited()) || x <= 1) && ((x < maze.length - 2 && maze[x + 2][y].isVisited()) || x >= maze.length - 2) && ((y > 1 && maze[x][y - 2].isVisited()) || y <= 1) && ((y < maze[0].length - 2 && maze[x][y + 2].isVisited()) || y >= maze[0].length - 2);
  }

  protected boolean allVisited() {
    for (int i = 1; i < maze.length - 2; i += 2) {
      for (int x = 1; x < maze[0].length - 2; x += 2) {
        if (!maze[i][x].isVisited()) return false;
      }
    }
    return true;
  }

  public void unvisitAll() {
    for (Cell[] i : maze) {
      for (Cell x : i) {
        x.setVisited(false);
      }
    }
  }

  protected ArrayList<Integer> randomDirections() {
    ArrayList<Integer> list = new ArrayList<Integer>();
    int random;
    while (list.size() < 4) {
      random = (int) (Math.random() * 4 + 1);
      if (!list.contains(random)) list.add(random);
    }
    return list;
  }

  protected boolean contain(ArrayList<Integer[]> frontiers, Integer[] cell) {
    for (Integer[] i : frontiers) {
      if (i[0].equals(cell[0]) && i[1].equals(cell[1])) return true;
    }
    return false;
  }
  
  private int numWalls(int x, int y) {
    int sum = 0;
    if (maze[x - 1][y].toString().equals("[]")) sum++;
    if (maze[x + 1][y].toString().equals("[]")) sum++;
    if (maze[x][y - 1].toString().equals("[]")) sum++;
    if (maze[x][y + 1].toString().equals("[]")) sum++;
    return sum;
  }

  protected boolean deadEndW(int x, int y) {
    return (x > 0 && (maze[x - 1][y].toString().equals("[]") 
                      || maze[x - 1][y].isVisited()))
      
      && (x < maze.length - 1 && (maze[x + 1][y].toString().equals("[]") 
                                  || maze[x + 1][y].isVisited())) 
      
      && (y > 0 && (maze[x][y - 1].toString().equals("[]") 
                    || maze[x][y - 1].isVisited())) 
      
      && (y < maze[0].length - 1 && (maze[x][y + 1].toString().equals("[]") 
                                     || maze[x][y + 1].isVisited()));
  }

  private void mark(int x, int y) {
    maze[x][y].setBlock("o ");
  }

  private void markUp(int x, int y) {
    maze[x - 1][y].setBlock("o ");
  }

  private void markDown(int x, int y) {
    maze[x + 1][y].setBlock("o ");
  }

  private void markLeft(int x, int y) {
    maze[x][y - 1].setBlock("o ");
  }

  private void markRight(int x, int y) {
    maze[x][y + 1].setBlock("o ");
  }

  protected void solve(double seconds) {
    unvisitAll();
    solveFill();
    outer: while (true) {
      for (int i = 1; i < maze.length; i += 2) {
        for (int x = 1; x < maze[0].length; x += 2) {
          if (!(i == 1 && x == 1) && !(i == maze.length - 2 && x == maze[0].length - 2) && isBranch(i, x)) {
            backtrack(i, x, seconds);
            continue outer;
          }
        }
      }
      break;
    }
  }

  private boolean isBranch(int x, int y) {
    int sum = 0;
    if (maze[x - 1][y].toString().equals("o ")) sum++;
    if (maze[x + 1][y].toString().equals("o ")) sum++;
    if (maze[x][y - 1].toString().equals("o ")) sum++;
    if (maze[x][y + 1].toString().equals("o ")) sum++;
    return sum == 1;
  }

  private void backtrack(int x, int y, double seconds) {
    do {
      maze[x][y].setBlock("  ");
      if (maze[x - 1][y].toString().equals("o ")) {
        cutUp(x, y);
        x -= 2;
      } else if (maze[x + 1][y].toString().equals("o ")) {
        cutDown(x, y);
        x += 2;
      } else if (maze[x][y - 1].toString().equals("o ")) {
        cutLeft(x, y);
        y -= 2;
      } else if (maze[x][y + 1].toString().equals("o ")) {
        cutRight(x, y);
        y += 2;
      }
      if (seconds != 0) {
        System.out.print("\033[H\033[2J"); 
        show();
        wait(seconds);
      }
    } while (numWalls(x, y) > 1);
  }

  private void solveFill() {
    for (int x = 1; x < maze.length - 1; x++) {
      for (int y = 1; y < maze[0].length - 1; y++) {
        if (maze[x][y].toString().equals("  ")) mark(x, y);
      }
    }
  }

  private void solveRecurse(int x, int y, double seconds) {
    maze[x][y].setVisited(true);
    if (!(x == maze.length - 2 && y == maze.length - 2)) {
      if (!deadEndW(x, y)) {
        for (int i : new Integer[] {2, 4, 3, 1}) {
          if (i == 1 && x > 1 && !maze[x - 2][y].isVisited() && !maze[x - 1][y].toString().equals("[]")) {
            mark(x, y);
            markUp(x, y);
            if (seconds != 0) {
              System.out.print("\033[H\033[2J"); 
              show();
              wait(seconds);
            }
            maze[x - 1][y].setVisited(true);
            solveRecurse(x - 2, y, seconds);
          } else if (i == 2 && x < maze.length - 2 && !maze[x + 2][y].isVisited() && !maze[x + 1][y].toString().equals("[]")) {
            mark(x, y);
            markDown(x, y);
            if (seconds != 0) {
              System.out.print("\033[H\033[2J"); 
              show();
              wait(seconds);
            }
            maze[x + 1][y].setVisited(true);
            solveRecurse(x + 2, y, seconds);
          } else if (i == 3 && y > 1 && !maze[x][y - 2].isVisited() && !maze[x][y - 1].toString().equals("[]")) {
            mark(x, y);
            markLeft(x, y);
            if (seconds != 0) {
              System.out.print("\033[H\033[2J"); 
              show();
              wait(seconds);
            }
            maze[x][y - 1].setVisited(true);
            solveRecurse(x, y - 2, seconds);
          } else if (i == 4 && y < maze[0].length - 2 && !maze[x][y + 2].isVisited() && !maze[x][y + 1].toString().equals("[]")) {
            mark(x, y);
            markRight(x, y);
            if (seconds != 0) {
              System.out.print("\033[H\033[2J"); 
              show();
              wait(seconds);
            }
            maze[x][y + 1].setVisited(true);
            solveRecurse(x, y + 2, seconds);
          }
        }
      } else {
        mark(x, y);
        if (seconds != 0) {
          System.out.print("\033[H\033[2J"); 
          show();
          wait(seconds);
        }
        backtrack(x, y, seconds);
      }
    } else {
      mark(x, y);
      if (seconds != 0) {
        System.out.print("\033[H\033[2J"); 
        show(); 
        wait(seconds);
      }
    }
  }

  private void solveIterate(double seconds) {
    int x = 1, y = 1;
    while (!(x == maze.length - 2 && y == maze.length - 2)) {
      while (!deadEndW(x, y)) {
        for (int i : new Integer[] {2, 4, 3, 1}) {
          if (i == 1 && x > 1 && !maze[x - 2][y].isVisited() && !maze[x - 1][y].toString().equals("[]")) {
            mark(x, y);
            markUp(x, y);
            if (seconds != 0) {
              System.out.print("\033[H\033[2J"); 
              show();
              wait(seconds);
            }
            maze[x - 1][y].setVisited(true);
            x -= 2;
            break;
          } else if (i == 2 && x < maze.length - 2 && !maze[x + 2][y].isVisited() && !maze[x + 1][y].toString().equals("[]")) {
            mark(x, y);
            markDown(x, y);
            if (seconds != 0) {
              System.out.print("\033[H\033[2J"); 
              show();
              wait(seconds);
            }
            maze[x + 1][y].setVisited(true);
            x += 2;
          } else if (i == 3 && y > 1 && !maze[x][y - 2].isVisited() && !maze[x][y - 1].toString().equals("[]")) {
            mark(x, y);
            markLeft(x, y);
            if (seconds != 0) {
              System.out.print("\033[H\033[2J"); 
              show();
              wait(seconds);
            }
            maze[x][y - 1].setVisited(true);
            y -= 2;
          } else if (i == 4 && y < maze[0].length - 2 && !maze[x][y + 2].isVisited() && !maze[x][y + 1].toString().equals("[]")) {
            mark(x, y);
            markRight(x, y);
            if (seconds != 0) {
              System.out.print("\033[H\033[2J"); 
              show();
              wait(seconds);
            }
            maze[x][y + 1].setVisited(true);
            y += 2;
          }
        }
      }
    }
  }
}
