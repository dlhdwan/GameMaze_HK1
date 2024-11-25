import javax.swing.*;
import java.awt.*;

public class WinningFrame extends JFrame {
    JLabel label;
    JButton back;

    WinningFrame() {
        this.setTitle("You Won!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        backgroundPanel.setLayout(new BorderLayout());
        this.setContentPane(backgroundPanel);

        // Label chúc mừng
        label = new JLabel("🎉 Congratulations! You won the game! 🎉", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(new Color(34, 139, 34)); // Forest Green

        // Nút quay lại
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.BOLD, 14));
        back.setBackground(new Color(255, 228, 181)); // Moccasin
        back.setFocusPainted(false);
        back.addActionListener(e -> {
            this.dispose();
            new Menu();
        });

        // Thêm thành phần vào đúng vị trí
        backgroundPanel.add(label, BorderLayout.CENTER); // Thêm label vào trung tâm
        // only bellow the label
        backgroundPanel.add(back, BorderLayout.SOUTH); // Thêm nút vào dưới label

        // Cài đặt kích thước frame
        this.setSize(500, 300);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new WinningFrame();
    }
}
