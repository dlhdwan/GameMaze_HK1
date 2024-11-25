import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class Difficulty extends JFrame {
    JLabel label;
    JButton easy;
    JButton medium;
    JButton hard;
    JButton back;
    Clip buttonClip;
    Difficulty() {
        this.setTitle("Difficulty");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(500, 200);
        this.setIconImage(new ImageIcon("src/logo.png").getImage());

        // Tạo nền gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(135, 206, 250); // Light Sky Blue
                Color color2 = new Color(255, 182, 193); // Light Pink
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new FlowLayout());
        backgroundPanel.setPreferredSize(new Dimension(400, 500));
        this.setContentPane(backgroundPanel);

        // Tiêu đề
        label = new JLabel("Choose your difficulty and the game will start then ", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(34, 139, 34)); // Forest Green
        label.setBounds(50, 30, 300, 40);
        backgroundPanel.add(label);

        // Nút "Easy"
        easy = new JButton("Easy");
        decorateButton(easy, 100, 100);
        easy.setBackground(new Color(50, 205, 50)); // Lime Green
        easy.addActionListener(e -> {
            BoardUI boardUI = new BoardUI(10, 10);
            this.dispose();
            if (buttonClip != null && buttonClip.isRunning()) {
                buttonClip.stop(); // Dừng nếu đang chạy
            }
            buttonClip = SoundManager.playSound("back.wav", 1.0f);
        });

        backgroundPanel.add(easy);

        // Nút "Medium"
        medium = new JButton("Medium");
        decorateButton(medium, 150, 100);
        medium.setBackground(new Color(255, 215, 0)); // Gold
        medium.addActionListener(e -> {
            BoardUI boardUI = new BoardUI(16, 16);
            this.dispose();
            if (buttonClip != null && buttonClip.isRunning()) {
                buttonClip.stop(); // Dừng nếu đang chạy
            }
            buttonClip = SoundManager.playSound("back.wav", 1.0f);
        });
        backgroundPanel.add(medium);

        // Nút "Hard"
        hard = new JButton("Hard");
        decorateButton(hard, 200, 100);
        hard.setBackground(new Color(220, 20, 60)); // Crimson
        hard.addActionListener(e -> {
            BoardUI boardUI = new BoardUI(30, 30);
            this.dispose();
            if (buttonClip != null && buttonClip.isRunning()) {
                buttonClip.stop(); // Dừng nếu đang chạy
            }
            buttonClip = SoundManager.playSound("back.wav", 1.0f);
        });
        backgroundPanel.add(hard);

        // Nút "Back"
        back = new JButton("Back");
        decorateButton(back, 250, 100);
        back.addActionListener(e -> {
            Menu menu = new Menu();
            this.dispose();
            if (buttonClip != null && buttonClip.isRunning()) {
                buttonClip.stop(); // Dừng nếu đang chạy
            }
            buttonClip = SoundManager.playSound("back.wav", 1.0f);
        });
        backgroundPanel.add(back);

        // Cấu hình cửa sổ
        this.setSize(400, 500);
        this.setLayout(null);
        this.setVisible(true);
    }

    // Phương thức trang trí nút
    private void decorateButton(JButton button, int y, int xOffset) {
        button.setBounds(xOffset, y, 140, 40);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(240, 248, 255)); // Alice Blue
        button.setForeground(new Color(25, 25, 112)); // Midnight Blue
        button.setFocusPainted(false);
    }

    public static void main(String[] args) {
        new Difficulty();
    }
}
