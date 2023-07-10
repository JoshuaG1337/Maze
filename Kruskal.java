import java.util.*;

class Kruskal extends Maze {
  Cell[][] maze = super.maze;
  
  public Kruskal(int x, int y) {
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
  
  private boolean inSameSet(ArrayList<ArrayList<Integer[]>> sets, Integer[] c1, Integer[] c2) {
    for (ArrayList<Integer[]> set : sets) {
      if (super.contain(set, c1) && super.contain(set, c2)) return true;
    }
    return false;
  }
  
  public void carve(double seconds) {
    ArrayList<Integer[]> passages = new ArrayList<Integer[]>();
    ArrayList<ArrayList<Integer[]>> sets = new ArrayList<ArrayList<Integer[]>>();
    for (int i = 1; i < super.maze.length - 1; i++) {
      for (int x = 1; x < super.maze[0].length - 2; x += 2) {
        if (i % 2 != 0) {
          passages.add(new Integer[] {i, x + 1});
          ArrayList<Integer[]> set = new ArrayList<Integer[]>();
          set.add(new Integer[] {i, x});
          sets.add(set);
        }
      }
    }
    for (int i = 2; i < super.maze.length - 2; i += 2) {
      for (int x = 1; x < super.maze[0].length - 2; x += 2) {
        passages.add(new Integer[] {i, x});
      }
    }
    Integer[] temp = new Integer[2], c1 = new Integer[2], c2 = new Integer[2];
    while (passages.size() > 0) {
      temp = passages.get((int) (Math.random() * passages.size()));
      passages.remove(temp);
      c1 = temp[0] % 2 != 0 ? new Integer[] {temp[0], temp[1] - 1} : new Integer[] {temp[0] - 1, temp[1]};
      c2 = temp[0] % 2 != 0 ? new Integer[] {temp[0], temp[1] + 1} : new Integer[] {temp[0] + 1, temp[1]};
      if (!inSameSet(sets, c1, c2)) {
        ArrayList<Integer[]> c2set = new ArrayList<Integer[]>();
        super.maze[temp[0]][temp[1]].setBlock("  ");
        if (seconds != 0) {
          System.out.print("\033[H\033[2J"); 
          show();
          super.wait(seconds);
        }
        for (ArrayList<Integer[]> set : sets) {
          if (super.contain(set, c2)) {
            c2set = set;
            break;
          }
        }
        for (ArrayList<Integer[]> set : sets) {
          if (contain(set, c1)) {
            for (Integer[] cell : c2set) {
              set.add(cell);
            }
            sets.remove(c2set);
            break;
          }
        }
      }
    }
  }
}
