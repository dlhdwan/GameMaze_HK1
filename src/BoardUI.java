import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardUI extends JFrame {
    private int rows = 20;
    private int cols = 20;
    private int cellSize = 25;
    private BoardPanel boardPanel;
    private Board board;
    private Panel panel;
    private Timer generationTimer;
    private Timer solveTimerDFS;
    private JPanel controlPanel;
    JButton MoveRight ;
    JButton MoveLeft ;
    JButton MoveUp ;
    JButton MoveDown ;
    public BoardUI() {
        this.setTitle("Board");
        this.setSize(cols * cellSize + 500, rows * cellSize + 100);
        this.board = new Board(rows, cols);
        this.boardPanel = new BoardPanel(rows, cols, cellSize, board);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.controlPanel = new JPanel();
        this.controlPanel.setLayout(new FlowLayout());
        this.controlPanel.setPreferredSize(new Dimension(200, 100));
        // four moving buttons
        MoveRight = new JButton("Move Right");
        MoveRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setStart(new Point(board.getStart().x, board.getStart().y + 1));
                boardPanel.repaint();
            }
        });
        MoveLeft = new JButton("Move Left");
        MoveLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setStart(new Point(board.getStart().x, board.getStart().y - 1));
                boardPanel.repaint();
            }
        });
        MoveUp = new JButton("Move Up");
        MoveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setStart(new Point(board.getStart().x - 1, board.getStart().y));
                boardPanel.repaint();
            }
        });
        MoveDown = new JButton("Move Down");
        MoveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setStart(new Point(board.getStart().x + 1, board.getStart().y));
                boardPanel.repaint();
            }
        });
        // creating maze button
        JButton regenerateButton = new JButton("Generate Board");
        regenerateButton.addActionListener(e -> {
            board.resetMaze();
            boardPanel.repaint();
            for (Component c : controlPanel.getComponents()) {
                if(c instanceof JButton){
                    c.setEnabled(false);
                }
            }
            generationTimer.start();

        });
        JButton solveDFSButton = new JButton("Solve with DFS");

        solveDFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DFS.initializeDFS(board.getMaze(), board.getStart());
                solveTimerDFS.start();
            }
        });

        solveTimerDFS = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!DFS.stepDFS(board.getMaze(), board.getEnd())) {
                    solveTimerDFS.stop();
                    boardPanel.repaint();
                } else {
                    boardPanel.repaint();
                }
            }
        });


        // adding comppoents to the frame
        this.setLayout(new BorderLayout());
        this.add(boardPanel, BorderLayout.CENTER);
        AddingKeyBinding();
        // Add the control panel to the frame
        controlPanel.add(solveDFSButton);
        controlPanel.add(MoveRight);
        controlPanel.add(MoveLeft);
        controlPanel.add(MoveUp);
        controlPanel.add(MoveDown);
        controlPanel.add(regenerateButton);
        // add the control panel to the frame
        this.add(controlPanel, BorderLayout.EAST);



        generationTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.isDone()) {
                    generationTimer.stop();
                    for (Component c : controlPanel.getComponents()) {
                        if(c instanceof JButton){
                            c.setEnabled(true);
                        }
                    }

                }
                else{
                    board.step();
                    boardPanel.repaint();
                }
            }
        });




        this.setVisible(true);
    }
    void AddingKeyBinding (){
        // adding key binding to the frame that will do the button action
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "Move Right");
        this.getRootPane().getActionMap().put("Move Right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveRight.doClick();
            }
        });
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "Move Left");
        this.getRootPane().getActionMap().put("Move Left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveLeft.doClick();
            }
        });
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "Move Up");
        this.getRootPane().getActionMap().put("Move Up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveUp.doClick();
            }
        });
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "Move Down");
        this.getRootPane().getActionMap().put("Move Down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveDown.doClick();
            }
        });

    }


}