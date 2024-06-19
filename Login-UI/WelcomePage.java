import javax.swing.*;
import java.awt.event.*;

public class WelcomePage implements ActionListener{
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    IDandPasswords idandPasswords;

    public WelcomePage(IDandPasswords idandPasswords) {
        this.idandPasswords = idandPasswords;

        loginButton.setBounds(100, 100, 200, 50);
        registerButton.setBounds(100, 200, 200, 50);
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        frame.add(loginButton);
        frame.add(registerButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            frame.dispose();
            new LoginPage(idandPasswords.getLonginInfo());
        }
        else if(e.getSource() == registerButton) {
            frame.dispose();
            new RegisterPage(idandPasswords);
        }
    }
    
}
