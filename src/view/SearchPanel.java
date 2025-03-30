package view;

import dao.GradeDAO;
import model.Grade;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SearchPanel extends JPanel {
    private JTextField txtStudentName, txtTeacherSubject;
    private JButton btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private GradeDAO gradeDAO;

    public SearchPanel() {
        gradeDAO = new GradeDAO();
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel searchPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        searchPanel.add(new JLabel("Име на ученик:"));
        txtStudentName = new JTextField();
        searchPanel.add(txtStudentName);

        searchPanel.add(new JLabel("Предмет:"));
        txtTeacherSubject = new JTextField();
        searchPanel.add(txtTeacherSubject);

        btnSearch = new JButton("Търси");
        searchPanel.add(btnSearch);
        add(searchPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Студент ID", "Преподавател ID", "Оценка", "Дата", "Бележки"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchGrades());
    }

    private void searchGrades() {
        String studentName = txtStudentName.getText();
        String teacherSubject = txtTeacherSubject.getText();
        List<Grade> grades = gradeDAO.searchGrades(studentName, teacherSubject);
        tableModel.setRowCount(0);
        for (Grade g : grades) {
            tableModel.addRow(new Object[]{g.getGradeId(), g.getStudentId(), g.getTeacherId(), g.getGradeValue(), g.getExamDate(), g.getRemarks()});
        }
    }
}
