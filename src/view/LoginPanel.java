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
        setLayout(new BorderLayout()); // Changed to BorderLayout
        setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20)); // Add padding

        // Create a panel for the login components
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Потребител:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtUsername = new JTextField(15);
        centerPanel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Парола:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtPassword = new JPasswordField(15);
        centerPanel.add(txtPassword, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        btnLogin = new JButton("Вход");
        centerPanel.add(btnLogin, gbc);

        add(centerPanel, BorderLayout.CENTER); // Add to center of main panel

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
