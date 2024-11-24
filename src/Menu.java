import javax.swing.*;

public class Menu {
    JFrame frame;
    JButton PlayButton;
    JButton ReadMe;
    ImageIcon icon;
    Menu(){
        frame = new JFrame("Menu");
        PlayButton = new JButton("Play");
        ReadMe = new JButton("ReadMe");
        ImageIcon icon = new ImageIcon("src/logo.png");
        frame.setIconImage(icon.getImage());
        frame.setLocation(500, 200);
        PlayButton.setBounds(100, 100, 140, 40);
        PlayButton.addActionListener(e -> {
            Difficulty difficulty = new Difficulty();
            frame.dispose();
        });
        ReadMe.setBounds(100, 200, 140, 40);
        ReadMe.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "This is a maze game. You have to find the path from the start to the end point. " + '\n' +
                    "You can only move in four directions: up, down, left, and right. You can't move diagonally. The path is blocked by walls. You can't move through walls. Good luck!"+'\n'+
                    "This game is all handcoded by Mai Lam team , we just study in one week so the game isnt perfect at all. Hope you enjoy it"+'\n'+
                    "author : Dinh Xuan Huy 23110102 , DinhLeHoangDanh 23110082");
        });
        frame.add(PlayButton);
        frame.add(ReadMe);
        frame.setSize(400, 500);
        frame.setLayout(null);
        //frame center
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
