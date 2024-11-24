import javax.swing.*;

public class WinningFrame extends JFrame {
    JLabel label;
    JButton back;
    WinningFrame(){
        this.setTitle("You Won!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label = new JLabel("Congratulations! You won the game!");
        label.setBounds(100, 100, 400, 40);
        back = new JButton("Back");
        back.setBounds(100, 200, 140, 40);
        back.addActionListener(e -> {
            Menu menu = new Menu();
            this.dispose();
        });
        this.add(label);
        this.add(back);
        this.setLayout(null);
        this.setSize(400, 300);

        this.setVisible(true);
    }

}
