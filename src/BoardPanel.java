import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private int rows;
    private int cols;
    private int cellSize;
    private Board board;
    BoardPanel(int rows, int cols, int cellSize, Board board) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.board = board;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] boardData = board.getMaze();
        Point start = board.getStart();
        Point end = board.getEnd();

        // Draw the board (walls and paths)
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                if (boardData[i][j] == 1) {
                    g.setColor(Color.WHITE);  // Path
                } else {
                    g.setColor(Color.BLACK);  // Wall
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
        // Draw DFS/BFS exploration path
        g.setColor(Color.GRAY);  // Wrong paths (explored but not solution)
        for (Point p : BoardSolver.getPath()) {
            g.fillRect(p.y * cellSize, p.x * cellSize, cellSize, cellSize);
        }


        // Draw the final correct solution path in blue
        g.setColor(new Color(41, 237, 201, 255));
        for (Point p : BoardSolver.getSolutionPath()) {
            g.fillRect(p.y * cellSize, p.x * cellSize, cellSize, cellSize);
        }

        // Draw the start and end points
        g.setColor(Color.GREEN);  // Start point
        g.fillRect(start.y * cellSize, start.x * cellSize, cellSize, cellSize);

        g.setColor(Color.YELLOW);  // End point
        g.fillRect(end.y * cellSize, end.x * cellSize, cellSize, cellSize);
    }


}