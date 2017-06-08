package sr.unasat.bewakingsbedrijf.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;

/**
 * Created by Jonathan on 6/7/2017.
 */
public class Login extends JFrame{
    public Login() {
        super("Login Screen");

        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        usernameLabel.setBounds(100, 80, 120, 20);
        passwordLabel.setBounds(100, 100, 120, 20);
        usernameField.setBounds(180, 80, 120, 20);
        passwordField.setBounds(180, 100, 120, 20);
        loginBtn.setBounds(120, 125, 80, 20);

        JFrame login = new JFrame();
        login.setBounds(100, 100, 450, 300);
        login.getContentPane().setLayout(null);
        login.setVisible(true);

        login.getContentPane().add(usernameLabel);
        login.getContentPane().add(passwordLabel);
        login.getContentPane().add(usernameField);
        login.getContentPane().add(passwordField);
        login.getContentPane().add(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Login gegevens ophalen
                String name = usernameField.getText();
                String passw = passwordField.getText();

                if(name.equalsIgnoreCase("admin") && passw.equals("admin")){
                    JOptionPane.showMessageDialog(login, "Login succesful");

                    //login succesvol login screen verdwijnen en nieuwe window open
                    WindowEvent winClosingEvent = new WindowEvent(login,WindowEvent.WINDOW_CLOSING);
                    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(login, "Login unsuccesful");
                }
            }
        });
    }
}
