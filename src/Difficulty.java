import javax.swing.*;
import java.awt.*;

public class Difficulty extends JFrame {
    JLabel label;
    JButton easy;
    JButton medium;
    JButton hard;
    JButton back;
    Difficulty(){
        this.setTitle("Difficulty");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(500, 200);
        this.setIconImage(new ImageIcon("src/logo.png").getImage());

        label = new JLabel("Choose your difficulty and the game will start then ");
        label.setBounds(100, 100, 400, 40);
        easy = new JButton("Easy");
        easy.setBounds(100, 200, 140, 40);
        easy.addActionListener(e -> {
            BoardUI boardUI = new BoardUI(10,10);
            this.dispose();
        });
        medium = new JButton("Medium");
        medium.setBounds(100, 300, 140, 40);
        medium.addActionListener(e -> {
            BoardUI boardUI = new BoardUI(15,15);
            // disable the current frame but not close it
            this.dispose();
        });
        hard = new JButton("Hard");
        hard.setBounds(100, 400, 140, 40);
        hard.addActionListener(e -> {
            BoardUI boardUI = new BoardUI(20,20);
            this.dispose();
        });
        back = new JButton("Back");
        back.setBounds(100, 500, 140, 40);
        back.addActionListener(e -> {
            Menu menu = new Menu();
            this.dispose();
        });
        this.add(label);
        this.add(easy);
        this.add(medium);
        this.add(hard);
        this.add(back);
        this.setLayout(null);
        this.setSize(400, 600);

        this.setVisible(true);
    }


}
