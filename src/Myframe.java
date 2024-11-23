import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Myframe extends JFrame {
    //10x10 matrix
    int[][] matrix = new int[][]{
            //harder
            {1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1},
            {1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1},
            {1, 0, 1, 1, 1}

    };
    int[][] visited = new int[5][5];
    Queue<Point> dfsQueue = new LinkedList<>();
    Timer timer;
    final int CELL_SIZE = 100;
    int xstart;
    int ystart;

    Myframe() {
        this.setTitle("Maze");
        JButton button = new JButton();
        JButton leftmove = new JButton("move left");
        JButton rightmove = new JButton("move right");
        JButton upmove = new JButton("move up");
        JButton downmove = new JButton("move down");
        leftmove.setBounds(500, 200, 140, 40);
        rightmove.setBounds(500, 300, 140, 40);
        upmove.setBounds(500, 400, 140, 40);
        downmove.setBounds(500, 500, 140, 40);
        leftmove.addActionListener(e -> moveleft());
        rightmove.addActionListener(e -> moveright());
        upmove.addActionListener(e -> moveup());
        downmove.addActionListener(e -> movedown());
        this.add(leftmove);
        this.add(rightmove);
        this.add(upmove);
        this.add(downmove);

        button.setText("Perform DFS");
        button.setBounds(500, 100, 140, 40);
        xstart = 0;
        ystart = 0;

        button.addActionListener(e -> {
            // Initialize DFS starting point and start the timer
            dfsQueue.add(new Point(xstart, ystart));
            startTimer();
        });

        this.setLayout(null);
        this.getContentPane().setBackground(Color.red);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);

        this.add(button);
        this.setVisible(true);
    }

    private void startTimer() {
        timer = new Timer(100, e -> {
            if (dfsQueue.isEmpty()) {
                timer.stop();
                return;
            }
            Point point = dfsQueue.poll();
            dfs(point.x, point.y);

            // Repaint only the affected cell
             repaint(point.y * CELL_SIZE, point.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        });
        timer.start();
    }

    private void dfs(int x, int y) {
        if (x < 0 || x >= 5 || y < 0 || y >= 5) {
            return;
        }
        if (matrix[x][y] == 0 || visited[x][y] == 1) {
            return;
        }

        visited[x][y] = 1;
        // check directipn before adding to queue
        if (x - 1 >= 0 && visited[x - 1][y] == 0) {
            dfsQueue.add(new Point(x - 1, y));
        }
        if (x + 1 < 5 && visited[x + 1][y] == 0) {
            dfsQueue.add(new Point(x + 1, y));
        }
        if (y - 1 >= 0 && visited[x][y - 1] == 0) {
            dfsQueue.add(new Point(x, y - 1));
        }
        if (y + 1 < 5 && visited[x][y + 1] == 0) {
            dfsQueue.add(new Point(x, y + 1));
        }

    }

    public void paint(Graphics g) {
        super.paint(g); // Ensure the default paint behavior
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i==xstart && j==ystart){
                    g.setColor(Color.blue);
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    continue;
                }
                if (matrix[i][j] == 1) {
                    g.setColor(Color.white);
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    g.setColor(Color.black);
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                if (visited[i][j] == 1) {
                    g.setColor(Color.green);
                    g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                g.setColor(Color.gray);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
    void moveleft() {
        if (ystart - 1 >= 0 && matrix[xstart][ystart - 1] == 1) {
            int prevY = ystart;
            ystart--;
            // Vẽ lại chỉ 2 ô bị ảnh hưởng
            repaint(prevY * CELL_SIZE, xstart * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ô cũ
            repaint(ystart * CELL_SIZE, xstart * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ô mới
        }
    }

    void moveright() {
        if (ystart + 1 < 5 && matrix[xstart][ystart + 1] == 1) {
            int prevY = ystart;
            ystart++;
            repaint(prevY * CELL_SIZE, xstart * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ô cũ
            repaint(ystart * CELL_SIZE, xstart * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ô mới
        }
    }

    void moveup() {
        if (xstart - 1 >= 0 && matrix[xstart - 1][ystart] == 1) {
            int prevX = xstart;
            xstart--;
            repaint(ystart * CELL_SIZE, prevX * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ô cũ
            repaint(ystart * CELL_SIZE, xstart * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ô mới
        }
    }

    void movedown() {
        if (xstart + 1 < 5 && matrix[xstart + 1][ystart] == 1) {
            int prevX = xstart;
            xstart++;
            repaint(ystart * CELL_SIZE, prevX * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ô cũ
            repaint(ystart * CELL_SIZE, xstart * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Ô mới
        }
    }


    public static void main(String[] args) {
        new Myframe();
    }
}
