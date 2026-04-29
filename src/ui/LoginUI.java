package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginUI() {

        setTitle("Login");
        setSize(420, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235,240,245));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBounds(40, 30, 320, 210);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        add(panel);

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(120, 10, 100, 30);
        panel.add(title);

        usernameField = new JTextField();
        usernameField.setBounds(50, 60, 220, 30);
        panel.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 100, 220, 30);
        panel.add(passwordField);

        JButton loginBtn = createButton("Login", new Color(33,150,243));
        loginBtn.setBounds(50, 145, 220, 40);
        panel.add(loginBtn);

        loginBtn.addActionListener(e -> {

            User user = new UserDAO().login(
                    usernameField.getText(),
                    new String(passwordField.getPassword())
            );

            if (user != null) {
                if (user.getRole().equals("ADMIN")) new AdminUI();
                else if (user.getRole().equals("USER")) new UserUI();
                else new TeamUI(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Credentials");
            }
        });

        setVisible(true);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        new LoginUI();
    }
}