import javax.swing.JFrame;

public class SnakeFrame extends JFrame{
    public static void main(String[] args) {
        new SnakeFrame();
    }
    public SnakeFrame() {
        this.add(new SnakePanel());
        this.setTitle("Victor's Snake Game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}