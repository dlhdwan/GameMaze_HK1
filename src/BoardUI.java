    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import javax.sound.sampled.*;

    public class BoardUI extends JFrame {
        private int rows ;
        private int cols ;
        private int cellSize = 25;
        private BoardPanel boardPanel;
        private Board board;
        private Panel panel;
        private Timer generationTimer;
        private Timer solveTimerDFS;
        private Timer solveTimerBFS;
        private JPanel controlPanel;
        JButton MoveRight ;
        JButton MoveLeft ;
        JButton MoveUp ;
        JButton MoveDown ;
        JButton back;
        JButton AddWall;
        JButton AddWeight;
        JLabel StatusLable;
        Clip buttonClip;
        JProgressBar ProgressBar;

        public BoardUI(int rows ,int cols) {

            this.setTitle("Board");
            this.setIconImage(new ImageIcon("src/logo.png").getImage());
            this.setLocation(500, 200);
            this.setSize(cols * cellSize + 200, rows * cellSize + 50);
            //set location to center screen
            this.setLocationRelativeTo(null);
            this.rows = rows;
            this.cols = cols;
            this.board = new Board(rows, cols);
            this.boardPanel = new BoardPanel(rows, cols, cellSize, board);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.controlPanel = new JPanel();
            this.controlPanel.setLayout(new FlowLayout());
            this.controlPanel.setPreferredSize(new Dimension(200, rows * cellSize+200));
            StatusLable = new JLabel("Please Generate Maze", JLabel.CENTER);
            StatusLable.setFont(new Font("Arial", Font.BOLD, 13));
            StatusLable.setForeground(new Color(34, 139, 34)); // Forest Green
            // set text to always fit
            AddWeight = new JButton("DisplayWeight");
            StatusLable.setSize(150, 50);
            StatusLable.setPreferredSize(new Dimension(150, 50));
            JButton FinalPath = new JButton("Final Path");
            FinalPath.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BoardSolver.showPath();
                    boardPanel.repaint();
                    if (buttonClip != null && buttonClip.isRunning()) {
                        buttonClip.stop(); // Dừng nếu đang chạy
                    }
                    buttonClip = SoundManager.playSound("bfsdfs.wav", 1.0f);
                }
            });

            // four moving buttons
            MoveRight = new JButton(">");
            MoveRight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                    board.setStart(new Point(board.getStart().x, board.getStart().y + 1));
                    boardPanel.repaint();
                    if (buttonClip != null && buttonClip.isRunning()) {
                        buttonClip.stop(); // Dừng nếu đang chạy
                    }
                    buttonClip = SoundManager.playSound("move.wav", 1.0f);
                    }
                    catch (Exception ex){
                        StatusLable.setText("Invalid move");
                        ResetStatusLableDelay(StatusLable,"Please make a move",2000);


                    }
                    if (board.getStart().equals(board.getEnd())) {
                        WinningFrame winningFrame = new WinningFrame();
                        dispose();

                    }
                }
            });
            MoveRight.setPreferredSize(new Dimension(50, 25));

            MoveLeft = new JButton("<");
            MoveLeft.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        board.setStart(new Point(board.getStart().x, board.getStart().y - 1));
                        boardPanel.repaint();
                        if (buttonClip != null && buttonClip.isRunning()) {
                            buttonClip.stop(); // Dừng nếu đang chạy
                        }
                        buttonClip = SoundManager.playSound("move.wav", 1.0f);
                    }
                    catch (Exception ex){
                        StatusLable.setText("Invalid move");
                        ResetStatusLableDelay(StatusLable,"Please make a move",2000);
                    }
                    if (board.getStart().equals(board.getEnd())) {
                        WinningFrame winningFrame = new WinningFrame();
                        dispose();
                    }
                }
            });
            MoveLeft.setPreferredSize(new Dimension(50, 25));
            MoveUp = new JButton("^");
            MoveUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        board.setStart(new Point(board.getStart().x - 1, board.getStart().y));
                        boardPanel.repaint();
                        if (buttonClip != null && buttonClip.isRunning()) {
                            buttonClip.stop(); // Dừng nếu đang chạy
                        }
                        buttonClip = SoundManager.playSound("move.wav", 1.0f);
                    }
                    catch (Exception ex){
                        StatusLable.setText("Invalid move");
                        ResetStatusLableDelay(StatusLable,"Please make a move",2000);
                    }
                    if (board.getStart().equals(board.getEnd())) {
                        WinningFrame winningFrame = new WinningFrame();
                        dispose();
                    }
                }
            });
            MoveUp.setPreferredSize(new Dimension(150, 25));
            MoveDown = new JButton("v");
            MoveDown.setPreferredSize(new Dimension(50, 25));
            MoveDown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        board.setStart(new Point(board.getStart().x + 1, board.getStart().y));
                        boardPanel.repaint();
                        if (buttonClip != null && buttonClip.isRunning()) {
                            buttonClip.stop(); // Dừng nếu đang chạy
                        }
                        buttonClip = SoundManager.playSound("move.wav", 1.0f);
                    }
                    catch (Exception ex){
                        StatusLable.setText("Invalid move");
                        ResetStatusLableDelay(StatusLable,"Please make a move",2000);

                    }
                    if (board.getStart().equals(board.getEnd())) {
                        WinningFrame winningFrame = new WinningFrame();
                        dispose();
                    }
                }
            });
            // creating maze button
            ProgressBar = new JProgressBar(0,100);
            ProgressBar.setStringPainted(true);
            ProgressBar.setForeground(new Color(Color.GREEN.getRGB()));
            ProgressBar.setValue(0);

            JButton regenerateButton = new JButton("Generate Board");
            regenerateButton.setPreferredSize(new Dimension(150, 50));
            regenerateButton.addActionListener(e -> {
                if (buttonClip != null && buttonClip.isRunning()) {
                    buttonClip.stop(); // Dừng nếu đang chạy
                }
                buttonClip = SoundManager.playSound("geneboard.wav", 1.0f);
                board.resetMaze();
                boardPanel.repaint();
                for (Component c : controlPanel.getComponents()) {
                    if(c instanceof JButton){
                        c.setEnabled(false);
                    }
                }
                StatusLable.setText("Generating Maze");
                ProgressBar.setValue(0);
                ProgressBar.setVisible(true);
                generationTimer.start();

            });
            JButton solveDFSButton = new JButton("Solve with DFS");

            solveDFSButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DFS.initializeDFS(board.getMaze(), board.getStart());
                    solveTimerDFS.start();
                    if (buttonClip != null && buttonClip.isRunning()) {
                        buttonClip.stop(); // Dừng nếu đang chạy
                    }
                    buttonClip = SoundManager.playSound("bfsdfs.wav", 1.0f);
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

            JButton solveBFSButton = new JButton("Solve with BFS");
            solveBFSButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BFS.initializeBFS(board.getMaze(), board.getStart());
                    solveTimerBFS.start();
                    if (buttonClip != null && buttonClip.isRunning()) {
                        buttonClip.stop(); // Dừng nếu đang chạy
                    }
                    buttonClip = SoundManager.playSound("bfsdfs.wav", 1.0f);
                }
            });
            solveTimerBFS = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!BFS.stepBFS(board.getMaze(), board.getEnd())) {
                        solveTimerBFS.stop();
                        boardPanel.repaint();
                    } else {
                        boardPanel.repaint();
                    }
                }
            });
            back = new JButton("Back");
            back.addActionListener(e -> {
                Menu menu = new Menu();
                this.dispose();
                if (buttonClip != null && buttonClip.isRunning()) {
                    buttonClip.stop(); // Dừng nếu đang chạy
                }
                buttonClip = SoundManager.playSound("back.wav", 1.0f);
            });
            AddWall = new JButton("Add Wall");
            AddWall.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boardPanel.clickable = !boardPanel.clickable;
                    if (boardPanel.clickable) {
                        AddWall.setText("Stop Adding Wall");
                        StatusLable.setText("Click the cell");
                    } else {
                        AddWall.setText("Adding Wall");
                        StatusLable.setText("Please make a move");
                    }
                }
            });
            AddWeight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boardPanel.WeightDisplay = !boardPanel.WeightDisplay;
                    if (boardPanel.WeightDisplay) {
                        AddWeight.setText("Hide Weight");
                        StatusLable.setText("Weight Displayed");
                    } else {
                        AddWeight.setText("Display Weight");
                        StatusLable.setText("Please make a move");
                    }
                    repaint();
                }
            });
            controlPanel.add(back);
            controlPanel.add(AddWall);
            controlPanel.add(AddWeight);

            // adding comppoents to the frame
            this.setLayout(new BorderLayout());
            this.add(boardPanel, BorderLayout.CENTER);
            AddingKeyBinding();
            // Add the control panel to the frame
            controlPanel.add(solveDFSButton);
            controlPanel.add(solveBFSButton);
            controlPanel.add(regenerateButton);

            controlPanel.add(StatusLable);
            controlPanel.add(FinalPath);
            controlPanel.add(MoveUp);
            controlPanel.add(MoveLeft);
            controlPanel.add(MoveDown);
            controlPanel.add(MoveRight);
            controlPanel.add(ProgressBar);

            for(Component c : controlPanel.getComponents()){
                if(c instanceof JButton){
                    c.setEnabled(false);
                }
            }
            back.setEnabled(true);
            regenerateButton.setEnabled(true);


            // add the control panel to the frame
            this.add(controlPanel, BorderLayout.EAST);

            controlPanel.setBackground(new Color(142, 207, 230));

            generationTimer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (board.isDone()) {
                        ProgressBar.setVisible(false);
                        generationTimer.stop();
                        for (Component c : controlPanel.getComponents()) {
                            if(c instanceof JButton){
                                c.setEnabled(true);
                            }
                        }
                        StatusLable.setText("Maze Generated");

                    }
                    else{
                        board.step();
                        ProgressBar.setValue(board.getProgress());
                        boardPanel.repaint();
                    }
                }
            });

            this.setVisible(true);
        }
        void ResetStatusLableDelay(JLabel lb , String text , int delay){
            Timer timer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    lb.setText(text);
                }
            });
            timer.setRepeats(false);
            timer.start();

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
        public static void main(String[] args) {
            new BoardUI(10, 10);
        }


    }
