package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent; // represents an event like button clicked
import java.awt.event.ActionListener; // used for handling events => run some code when a buttonr is pressed

public class LoginUI extends JFrame { // for window

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginUI() {
// setting up the window -> title, sizee, layout 
        setTitle("Login");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //username ui
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);
        // adds input box
        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 150, 25);
        add(usernameField);
        
        // password ui
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(20, 60, 80, 25);
        add(passLabel);

        //adds password box
        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 150, 25);
        add(passwordField);

        // login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 100, 100, 30);
        add(loginBtn);

        // logic when login button is clicked
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText(); // gets user input
                String password = new String(passwordField.getPassword()); // gets user input

                //calls database
                UserDAO dao = new UserDAO();
                User user = dao.login(username, password); // sends input to database if the input is valid
                // it gets a user object, otherwise if invalid gets null

                if (user != null) {

                    JOptionPane.showMessageDialog(null, "Login Successful!");

               
                    if (user.getRole().equals("ADMIN")) {
                        new AdminUI();
                    } else if (user.getRole().equals("USER")) {
                        new UserUI();
                    } else {
                        new TeamUI(user);
                    }

                    dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}