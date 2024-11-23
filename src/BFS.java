import java.awt.*;
import java.util.List;

public class BFS {

    // Initialize BFS for step-by-step solving
    public static void initializeBFS(int[][] maze, Point start) {
        BoardSolver.clearMaze();
        BoardSolver.queue.add(start);  // Start BFS from the start point
        BoardSolver.visited.add(start);  // Mark start as visited
    }

    // Step-by-step BFS with visualization
    public static boolean stepBFS(int[][] maze, Point end) {
        if (BoardSolver.queue.isEmpty()) {
            return false;  // BFS is complete
        }

        Point current = BoardSolver.queue.poll();  // Dequeue the current point
        BoardSolver.path.add(current);  // Add current point to the exploration path

        if (current.equals(end)) {
            BoardSolver.reconstructPath(current);  // Reconstruct the correct path once the goal is reached
            return false;  // Maze is solved
        }

        List<Point> neighbors = BoardSolver.getNeighbors(maze, current);
        for (Point neighbor : neighbors) {
            if (!BoardSolver.visited.contains(neighbor)) {
                BoardSolver.queue.add(neighbor);
                BoardSolver.cameFrom.put(neighbor, current);  // Track the parent node for path reconstruction
                BoardSolver.visited.add(neighbor);  // Mark neighbor as visited
            }
        }
        return true;  // Return true to indicate BFS is still in progress
    }


}
