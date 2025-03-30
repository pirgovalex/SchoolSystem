package view;

import model.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private AdminPanel adminPanel;
    private TeacherPanel teacherPanel;
    private StudentPanel studentPanel;
    private GradePanel gradePanel;
    private SearchPanel searchPanel;
    private LoginPanel loginPanel;

    public MainFrame() {
        setTitle("Система за оценки");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Първоначално се показва само LoginPanel
        loginPanel = new LoginPanel(this);
        add(loginPanel, BorderLayout.CENTER);
    }

    // Показва основния потребителски интерфейс според ролята на влезлия потребител
    public void showMainUI(User user) {
        getContentPane().removeAll();
        tabbedPane = new JTabbedPane();

        if (user.getRole().equals("ADMIN")) {
            adminPanel = new AdminPanel();
            teacherPanel = new TeacherPanel();
            studentPanel = new StudentPanel();
            tabbedPane.addTab("Admin", adminPanel);
            tabbedPane.addTab("Преподаватели", teacherPanel);
            tabbedPane.addTab("Ученици", studentPanel);
        }

        if (user.getRole().equals("TEACHER") || user.getRole().equals("ADMIN")) {
            gradePanel = new GradePanel();
            tabbedPane.addTab("Оценки", gradePanel);
        }

        // Всички потребители (включително ученици) имат достъп до търсене на оценки
        searchPanel = new SearchPanel();
        tabbedPane.addTab("Търсене", searchPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Изход");
        btnLogout.addActionListener(e -> logout());
        logoutPanel.add(btnLogout);

        add(tabbedPane, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
    private void logout() {
        getContentPane().removeAll();
        loginPanel = new LoginPanel(this);
        add(loginPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
