import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage implements ActionListener{
    //Frame, buttons, fields, and labels
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("user ID: ");
    JLabel userPasswordLabel = new JLabel("Password: ");
    JLabel messagLabel = new JLabel();

    HashMap<String,String> loginInfo = new HashMap<String,String>();

    public LoginPage(HashMap<String,String> loginInfoOriginal) {
        loginInfo = loginInfoOriginal;

        //login labels location
        userIDLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);

        //error labels location
        messagLabel.setBounds(125,250,250,35);
        messagLabel.setFont(new Font(null, Font.ITALIC,15));

        //login fields location
        userIDField.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        //Buttons location and listener
        loginButton.setBounds(125,200,100,25);
        loginButton.addActionListener(this);
        resetButton.setBounds(225,200,100,25);
        resetButton.addActionListener(this);

        //frame addings
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(messagLabel);
        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == resetButton) {
            userIDField.setText("");
            userPasswordField.setText("");
        }
        
        if(e.getSource() == loginButton) {
            String userID = userIDField.getText();
            String  password = String.valueOf(userPasswordField.getPassword());

            if(loginInfo.containsKey(userID)) {
                if(loginInfo.get(userID).equals(password)) {
                    messagLabel.setForeground(Color.GREEN);
                    messagLabel.setText("LOGIN SUCCESSFUL");
                    frame.dispose();
                    new Welcome(userID);
                }
                else {
                    messagLabel.setForeground(Color.RED);
                    messagLabel.setText("INCORRECT PASSWORDS");
                }
            }
            else {
                messagLabel.setForeground(Color.RED);
                messagLabel.setText("USERNAME NOT FOUND");
            }

        }
    }
}
