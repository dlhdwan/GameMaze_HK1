import java.awt.*;
import java.util.*;
import java.util.List;

public class Dijkstra {

    private static PriorityQueue<Point> pq;
    private static int[][] dist;
    private static Point end;

    public static void initializeDijkstra(int[][] maze, int[][] weights, Point start) {
        int rows = maze.length;
        int cols = maze[0].length;
        end = new Point(rows - 3, cols - 3);
        dist = new int[rows][cols];
        pq = new PriorityQueue<>(Comparator.comparingInt(p -> dist[p.x][p.y]));
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[start.x][start.y] = 0;
        pq.add(start);
        BoardSolver.clearMaze();
    }

    public static boolean stepDijkstra(int[][] maze, int[][] weights, Point end) {
        if (pq.isEmpty()) {
            return false;  // Dijkstra's algorithm is complete
        }


        Point current = pq.poll();
        BoardSolver.path.add(current);  // Add the current point to the visualization path

        if (current.equals(end)) {
            BoardSolver.reconstructPath(current);  // Reconstruct the correct path once the goal is reached
            return false;  // Maze is solved
        }

        for (Point neighbor : getNeighbors(maze, current)) {
            int newDist = dist[current.x][current.y] + weights[neighbor.x][neighbor.y];
            if (newDist < dist[neighbor.x][neighbor.y]) {
                dist[neighbor.x][neighbor.y] = newDist;
                BoardSolver.cameFrom.put(neighbor, current);  // Track the parent node for path reconstruction
                pq.add(neighbor);
            }
        }

        return true;  // Return true to indicate Dijkstra's algorithm is still in progress
    }

    private static List<Point> getNeighbors(int[][] maze, Point p) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int newX = p.x + dir[0];
            int newY = p.y + dir[1];
            if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length && maze[newX][newY] == 1) {
                neighbors.add(new Point(newX, newY));
            }
        }
        return neighbors;
    }
    public static int getResults(){
        return dist[end.x][end.y];
    }
}