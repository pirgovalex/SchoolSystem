package view;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private UserDAO userDAO;
    private MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        userDAO = new UserDAO();
        setLayout(new GridLayout(3, 2, 5, 5));

        add(new JLabel("Потребител:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Парола:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Вход");
        add(btnLogin);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        User user = userDAO.authenticateUser(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Вход успешен! Роля: " + user.getRole());
            mainFrame.showMainUI(user);
        } else {
            JOptionPane.showMessageDialog(this, "Невалидни данни!", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
