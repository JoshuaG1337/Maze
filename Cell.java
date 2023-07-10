class Cell {
  private String block = "  ";
  private boolean visited = false;

  public Cell(String block) {
    this.block = block;
    visited = false;
  } 

  public void setBlock(String block) {
    this.block = block;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public String toString() {
    return block;
  }
}
