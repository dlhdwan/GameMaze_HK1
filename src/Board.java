import java.awt.*;
import java.util.Random;
import java.util.Stack;

public class Board {
    private int rows;
    private int cols;
    private int[][] maze;
    private Point start;
    private Point end;
    private Stack<Point> stack;
    private boolean isDone;
    private int [][] Weight;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        resetMaze();
    }
    public int getProgress() {
        int visitedCells = 0;
        int validCells = (rows - 2) * (cols - 2); // Loại bỏ các ô viền
        // Duyệt qua các ô trong phạm vi hợp lệ (không tính viền)
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (maze[i][j] == 1) {
                    visitedCells++;
                }
            }
        }
        // Tính phần trăm hoàn thành
        return (int) ((double) (visitedCells / (0.5*validCells)) * 100);
        // cho dai thành 0.5 valid cell cái ra aảo
    }


    public void resetMaze() {
        BoardSolver.clearMaze();
        maze = new int[rows][cols];
        Weight = new int[rows][cols];
        stack = new Stack<>();
        start = new Point(1, 1);  // Fixed start at the top-left corner
        end = new Point(rows - 3, cols - 3);  // Fixed end point at (rows-3, cols-3)

        // Initialize the maze with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = 0;  // Initially walls, will change to paths
                Weight[i][j] = 0;
            }
        }

        stack.push(start);
        maze[start.x][start.y] = 1;  // Mark start as path
        Weight[start.x][start.y] = 0;
        isDone = false;
    }

    // Step-by-step generation of the maze using DFS
    public boolean step() {
        if (stack.isEmpty()) {
            isDone = true;
            return false;  // Maze generation is complete
        }
        Point current = stack.peek();  // Use peek() to check the current point without popping
        int x = current.x;
        int y = current.y;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  // Down, Up, Right, Left
        shuffleArray(directions);  // Shuffle directions for randomness

        boolean foundNewPath = false;
        for (int[] dir : directions) {
            int newX = x + dir[0] * 2;      //this is a general rule to create maze as this will create walls naturally
            int newY = y + dir[1] * 2;
            if (newX > 0 && newX < rows - 1 && newY > 0 && newY < cols - 1 && maze[newX][newY] == 0) {
                maze[x + dir[0]][y + dir[1]] = 1;  // Mark path between current and new point
                maze[newX][newY] = 1;  // Mark new point as path
                //weight of the new point random
                Weight[newX][newY] = (int) (Math.random() * 10);
                stack.push(new Point(newX, newY));  // Add new point to the stack
                foundNewPath = true;
                break;  // Break after finding one path to slow down generation
            }
        }

        if (!foundNewPath) {
            stack.pop();  // Backtrack if no new path is found
        }

        return true;  // Return true to indicate the generation is still in progress
    }

    // Helper function to shuffle directions
    private void shuffleArray(int[][] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int[] temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public int[][] getMaze() {
        return maze;
    }
    public int[][] getWeight() {
        return Weight;
    }

    public Point getStart() {
        return start;
    }
    public void setStart(Point start) {
        if (start.x > 0 && start.x < rows - 1 && start.y > 0 && start.y < cols - 1 && maze[start.x][start.y] == 1) {
            this.start = start;
        }
        else throw new InvalidMove("Invalid Move");
    }

    public Point getEnd() {
        return end;
    }

    public boolean isDone() {
        return isDone;
    }
}
