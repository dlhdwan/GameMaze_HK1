import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
    public class DFS {
        public static void initializeDFS(int[][] maze, Point start) {
        BoardSolver.clearMaze();
        BoardSolver.stack.push(start);  // Start DFS from the start point
    }

    public static boolean stepDFS(int[][] maze, Point end) {
        if (BoardSolver.stack.isEmpty()) {
            return false;  // DFS is complete
        }

        Point current = BoardSolver.stack.peek();
        BoardSolver.path.add(current);  // Add the current point to the visualization path

        if (current.equals(end)) {
            BoardSolver.reconstructPath(current);  // Reconstruct the correct path once the goal is reached
            return false;  // Maze is solved
        }

        BoardSolver.visited.add(current);
        List<Point> neighbors = BoardSolver.getNeighbors(maze, current);

        // Shuffle the neighbors to add randomness in direction selection
        Collections.shuffle(neighbors);

        boolean moved = false;
        for (Point neighbor : neighbors) {
            if (!BoardSolver.visited.contains(neighbor)) {
                BoardSolver.stack.push(neighbor);
                moved = true;
                BoardSolver.cameFrom.put(neighbor, current);  // Track the parent node for path reconstruction
                break;  // Move to the first unvisited neighbor (randomized)
            }
        }

        if (!moved) {
            BoardSolver.stack.pop();  // Backtrack if no unvisited neighbors
        }

        return true;  // Return true to indicate DFS is still in progress
    }
}
