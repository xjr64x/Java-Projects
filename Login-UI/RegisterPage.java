import javax.swing.*;
import java.awt.event.*;

public class RegisterPage implements ActionListener{
    JFrame frame = new JFrame();
    JButton registerButton = new JButton("Register");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel IDLabel = new JLabel("User ID: ");
    JLabel PasswordLabel = new JLabel("Password: ");
    JLabel messageLabel = new JLabel();
    IDandPasswords idandPasswords;

    public RegisterPage(IDandPasswords idandPasswords) {
        this.idandPasswords = idandPasswords;

        // label bounds
        IDLabel.setBounds(50, 100, 75, 25);
        PasswordLabel.setBounds(50, 150, 75, 25);
        messageLabel.setBounds(125, 250, 250, 35);

        //field bounds
        userIDField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);

        //button settings
        registerButton.setBounds(175, 200, 100, 25);
        registerButton.addActionListener(this);

        //frame ad ons
        frame.add(IDLabel);
        frame.add(PasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(registerButton);

        //frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton) {
            String userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            if(userID.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Fields cannot be empty");
            }
            else if(idandPasswords.getLonginInfo().containsKey(userID)){
                messageLabel.setText("User already exists");
            }
            else{
                idandPasswords.addUser(userID, password);
                messageLabel.setText("User registered successfully");
                switchToReturnButton();
            }
        }
    }

    private void switchToReturnButton() {
        registerButton.setText("Return");
        for(ActionListener al : registerButton.getActionListeners()) {
            registerButton.removeActionListener(al);
        }
        registerButton.addActionListener(e -> {
            frame.dispose();
            new WelcomePage(idandPasswords);
        });
    }
    
}
