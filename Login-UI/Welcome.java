import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Welcome {
    JFrame frame = new JFrame();
    JLabel welcomLabel = new JLabel("Hello");

    Welcome(String userID) {
        //welcome label
        welcomLabel.setBounds(0,0,200,35);
        welcomLabel.setFont(new Font(null, Font.PLAIN, 25));
        welcomLabel.setText("Hello " + userID);

        frame.add(welcomLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}