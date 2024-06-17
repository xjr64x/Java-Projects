import java.awt.Color;

import javax.swing.JFrame;

public class PingPongFrame extends JFrame{
    public static void main(String[] args) {
        new PingPongFrame();
    }
    public PingPongFrame() {
        this.add(new PingPongPanel());
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}