package view;

import dao.UserDAO;
import model.User;
import util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Примерен панел за администратор, който демонстрира управление на потребители.
// Този панел използва локална колекция за демонстрация, но може да бъде разширен да работи с DAO.
public class AdminPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtUsername, txtPassword, txtRole;
    private JButton btnAdd;
    private List<User> users;

    public AdminPanel() {
        users = new ArrayList<>();
        setLayout(new BorderLayout());
        initComponents();
        loadUsers();
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        inputPanel.add(new JLabel("Потребител:"));
        txtUsername = new JTextField();
        inputPanel.add(txtUsername);

        inputPanel.add(new JLabel("Парола:"));
        txtPassword = new JTextField();
        inputPanel.add(txtPassword);

        inputPanel.add(new JLabel("Роля (ADMIN/TEACHER/STUDENT):"));
        txtRole = new JTextField();
        inputPanel.add(txtRole);

        btnAdd = new JButton("Добави потребител");
        inputPanel.add(btnAdd);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Потребител", "Роля"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addUser());
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        for (User u : users) {
            tableModel.addRow(new Object[]{u.getUserId(), u.getUsername(), u.getRole()});
        }
    }

    private void addUser() {
        String catchID = "SELECT MAX(ID) AS maximum FROM USERS";
        int maxId = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(catchID);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
            maxId = rs.getInt("maximum");
            }
            maxId++;



        } catch (SQLException e) {
            e.printStackTrace();
        }
        int newId = users.size() + 1;
        User user = new User(maxId, txtUsername.getText(), txtPassword.getText(), txtRole.getText().toUpperCase());
        String insert = "INSERT INTO USERS VALUES (?, ?, ?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert)) {
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        users.add(user);
        loadUsers();
        clearFields();
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtRole.setText("");
    }
}
