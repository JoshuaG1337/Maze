import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    // ??, ?? for chromebook screen size
    // 18, 10 for 13-inch screen size
    while (true) {
      System.out.println(">>>");
      System.out.println("Welcome to Maze, enter generate or gen or g to start");
      System.out.print(": ");
      input = new Scanner(System.in);
      String command = input.nextLine();
      if (command.equals("generate") || command.equals("gen") || command.equals("g")) {
        System.out.println(">>>");
        System.out.print("Width: ");
        int width = input.nextInt() * 4 - 1;
        System.out.print("Height: ");
        int height = input.nextInt() * 4 - 1;
        System.out.println(">>>");
        System.out.println("Please select a generation algorithm");
        System.out.println("(DFS, Prim's, Hunt & Kill, Kruskal's)");
        System.out.print(": ");
        input = new Scanner(System.in);
        String algorithm = input.nextLine();
        if (algorithm.equals("DFS")) {
          DFS maze = new DFS(height, width);
          maze.addBorder();
          maze.addExits();
          maze.addVertices();
          maze.addWalls();
          System.out.print("seconds / frame: ");
          double seconds1 = input.nextDouble();
          maze.carve(height / 2, width / 2, seconds1);
          maze.show();
          System.out.println("Enter solve for solution");
          System.out.print(": ");
          input = new Scanner(System.in);
          String solve = input.nextLine();
          if (solve.equals("solve")) {
            System.out.print("seconds / frame: ");
            input = new Scanner(System.in);
            double seconds2 = input.nextDouble();
            maze.solve(seconds2);
            maze.show();
          }
        } else if (algorithm.equals("Prim's")) {
          Prim maze = new Prim(height, width);
          maze.addBorder();
          maze.addExits();
          maze.addVertices();
          maze.addWalls();
          System.out.print("seconds / frame: ");
          double seconds1 = input.nextDouble();
          maze.carve(height / 2, width / 2, seconds1);
          maze.show();
          System.out.println("Enter solve for solution");
          System.out.print(": ");
          input = new Scanner(System.in);
          String solve = input.nextLine();
          if (solve.equals("solve")) {
            System.out.print("seconds / frame: ");
            input = new Scanner(System.in);
            double seconds2 = input.nextDouble();
            maze.solve(seconds2);
            maze.show();
          }
        } else if (algorithm.equals("Hunt & Kill")) {
          HK maze = new HK(height, width);
          maze.addBorder();
          maze.addExits();
          maze.addVertices();
          maze.addWalls();
          System.out.print("seconds / frame: ");
          double seconds1 = input.nextDouble();
          maze.carve(height / 2, width / 2, seconds1);
          maze.show();
          System.out.println("Enter solve for solution");
          System.out.print(": ");
          input = new Scanner(System.in);
          String solve = input.nextLine();
          if (solve.equals("solve")) {
            System.out.print("seconds / frame: ");
            input = new Scanner(System.in);
            double seconds2 = input.nextDouble();
            maze.solve(seconds2);
            maze.show();
          }
        } else if (algorithm.equals("Kruskal's")) {
          Kruskal maze = new Kruskal(height, width);
          maze.addBorder();
          maze.addExits();
          maze.addVertices();
          maze.addWalls();
          System.out.print("seconds / frame: ");
          double seconds1 = input.nextDouble();
          maze.carve(seconds1);
          maze.show();
          System.out.println("Enter solve for solution");
          System.out.print(": ");
          input = new Scanner(System.in);
          String solve = input.nextLine();
          if (solve.equals("solve")) {
            System.out.print("seconds / frame: ");
            input = new Scanner(System.in);
            double seconds2 = input.nextDouble();
            maze.solve(seconds2);
            maze.show();
          }
        } else {
          Maze maze = new Maze(height, width);
          maze.addBorder();
          maze.addExits();
          maze.addVertices();
          maze.addWalls();
          maze.show();
        } 
      } 
    }
  }
}
