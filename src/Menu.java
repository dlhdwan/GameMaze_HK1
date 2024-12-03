import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.*;
import java.io.IOException;

public class Menu {
    JFrame frame;
    JButton PlayButton;
    JButton ReadMe;
    Clip buttonClip;
    Menu() {

        frame = new JFrame("Menu");

        // Thêm nền gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(135, 206, 235); // Sky blue
                Color color2 = new Color(255, 182, 193); // Light pink
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        // Thêm biểu tượng
        ImageIcon icon = new ImageIcon("src/logo.png");
        frame.setIconImage(icon.getImage());

        // Tiêu đề trang trí
        JLabel title = new JLabel("Maze Game Menu", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(34, 139, 34)); // Forest Green
        title.setBounds(50, 20, 300, 50);
        backgroundPanel.add(title);

        // Nút "Play"
        PlayButton = new JButton("Play");
        PlayButton.setBounds(130, 120, 140, 50);
        PlayButton.setFont(new Font("Arial", Font.BOLD, 16));
        PlayButton.setBackground(new Color(50, 205, 50)); // Lime Green
        PlayButton.setForeground(Color.WHITE);
        PlayButton.setFocusPainted(false);
        PlayButton.addActionListener(e -> {
            // Phát âm thanh khi nhấn nút
            if (buttonClip != null && buttonClip.isRunning()) {
                buttonClip.stop(); // Dừng nếu đang chạy
            }
            buttonClip = SoundManager.playSound("chonlv.wav", 1.0f);
            Difficulty difficulty = new Difficulty();
            frame.dispose();
        });
        backgroundPanel.add(PlayButton);

        // Nút "ReadMe"
        ReadMe = new JButton("ReadMe");
        ReadMe.setBounds(130, 200, 140, 50);
        ReadMe.setFont(new Font("Arial", Font.BOLD, 16));
        ReadMe.setBackground(new Color(30, 144, 255)); // Dodger Blue
        ReadMe.setForeground(Color.WHITE);
        ReadMe.setFocusPainted(false);
        ReadMe.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, """
                This is a maze game. You have to find the path from the start to the end point.
                You can only move in four directions: up, down, left, and right. You can't move diagonally. 
                The path is blocked by walls. You can't move through walls. Good luck!
                
                This game is all hand-coded by the Mai Lam team. We just studied in one week, 
                so the game isn't perfect at all. Hope you enjoy it.
                
                Authors: 
                - Dinh Xuan Huy (23110102)
                - Dinh Le Hoang Danh (23110082)
                """);
        });
        backgroundPanel.add(ReadMe);

        // Cấu hình cửa sổ
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null); // Canh giữa màn hình
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SoundManager.playBackgroundMusic("bg2.wav", 0.8f);

    }


    public static void main(String[] args) {
        new Menu();
    }
}
