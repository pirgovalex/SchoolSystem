package view;

import dao.GradeDAO;
import model.Grade;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

public class GradePanel extends JPanel {
    private GradeDAO gradeDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtStudentId, txtSubjectId, txtTeacherId, txtGradeValue, txtExamDate, txtRemarks;
    private JButton btnAdd, btnUpdate, btnDelete;

    public GradePanel() {
        gradeDAO = new GradeDAO();
        setLayout(new BorderLayout());
        initComponents();
        loadGrades();
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        inputPanel.add(new JLabel("Студент ID:"));
        txtStudentId = new JTextField();
        inputPanel.add(txtStudentId);

        inputPanel.add(new JLabel("Преподавател ID:"));
        txtTeacherId = new JTextField();
        inputPanel.add(txtTeacherId);

        inputPanel.add(new JLabel("Предмет ID:"));
        txtSubjectId = new JTextField();
        inputPanel.add(txtSubjectId);

        inputPanel.add(new JLabel("Оценка:"));
        txtGradeValue = new JTextField();
        inputPanel.add(txtGradeValue);

        inputPanel.add(new JLabel("Дата (yyyy-mm-dd):"));
        txtExamDate = new JTextField();
        inputPanel.add(txtExamDate);

        inputPanel.add(new JLabel("Бележки:"));
        txtRemarks = new JTextField();
        inputPanel.add(txtRemarks);

        btnAdd = new JButton("Добави");
        inputPanel.add(btnAdd);
        btnUpdate = new JButton("Редактирай");
        inputPanel.add(btnUpdate);
        btnDelete = new JButton("Изтрий");
        inputPanel.add(btnDelete);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Student ID", "Teacher ID", "Subject ID", "Grade", "Date", "Remarks"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addGrade());
        btnUpdate.addActionListener(e -> updateGrade());
        btnDelete.addActionListener(e -> deleteGrade());
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtSubjectId.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtStudentId.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtTeacherId.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtGradeValue.setText(tableModel.getValueAt(selectedRow, 4).toString()); // Grade (column 4)
                    txtExamDate.setText(tableModel.getValueAt(selectedRow, 5).toString());    // Date (column 5)
                    txtRemarks.setText(tableModel.getValueAt(selectedRow, 6).toString());     // Remarks (column 6)
                }
            }
        });
    }

    private void loadGrades() {
        tableModel.setRowCount(0);
        List<Grade> grades = gradeDAO.getAllGrades();
        for (Grade g : grades) {
            tableModel.addRow(new Object[]{
                    g.getGradeId(),
                    g.getStudentId(),
                    g.getTeacherId(),
                    g.getSubjectId(),
                    g.getGradeValue(),
                    g.getExamDate(),
                    g.getRemarks()
            });
        }
    }

    private void addGrade() {
        Grade grade = new Grade();
        grade.setSubjectId(Integer.parseInt(txtSubjectId.getText()));
        grade.setStudentId(Integer.parseInt(txtStudentId.getText()));
        grade.setTeacherId(Integer.parseInt(txtTeacherId.getText()));
        grade.setGradeValue(Double.parseDouble(txtGradeValue.getText()));
        grade.setExamDate(Date.valueOf(txtExamDate.getText()));
        grade.setRemarks(txtRemarks.getText());
        gradeDAO.addGrade(grade);
        loadGrades();
        clearFields();
    }

    private void updateGrade() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Grade grade = new Grade();
            grade.setGradeId((int) tableModel.getValueAt(selectedRow, 0));
            grade.setSubjectId(Integer.parseInt(txtSubjectId.getText()));
            grade.setStudentId(Integer.parseInt(txtStudentId.getText()));
            grade.setTeacherId(Integer.parseInt(txtTeacherId.getText()));
            grade.setGradeValue(Double.parseDouble(txtGradeValue.getText()));
            grade.setExamDate(Date.valueOf(txtExamDate.getText()));
            grade.setRemarks(txtRemarks.getText());
            gradeDAO.updateGrade(grade);
            loadGrades();
            clearFields();
        }
    }

    private void deleteGrade() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int gradeId = (int) tableModel.getValueAt(selectedRow, 0);
            gradeDAO.deleteGrade(gradeId);
            loadGrades();
            clearFields();
        }
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtTeacherId.setText("");
        txtSubjectId.setText("");
        txtGradeValue.setText("");
        txtExamDate.setText("");
        txtRemarks.setText("");
    }
}