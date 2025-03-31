package view;

import dao.GradeDAO;
import model.Grade;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchPanel extends JPanel {
    // First search components
    private JTextField txtStudentName, txtTeacherSubject;
    private JButton btnStudentSearch;

    // Second search components
    private JTextField txtTeacherName, txtSubject;
    private JButton btnTeacherSearch;

    // Tables
    private JTable studentGradesTable, teacherGradesTable;
    private DefaultTableModel studentTableModel, teacherTableModel;
    private GradeDAO gradeDAO;

    public SearchPanel() {
        gradeDAO = new GradeDAO();
        setLayout(new GridLayout(2, 1, 10, 10)); // Two rows with spacing
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
    }

    private void initComponents() {
        // First Search Section (Student-based)
        JPanel studentSearchPanel = createSearchSection(
                "Име на ученик:",
                "Предмет:",
                "Търси оценки по ученик",
                this::searchStudentGrades
        );
        studentGradesTable = createTable(new String[]{"ID", "Студент", "Предмет", "Оценка", "Дата"});
        studentTableModel = (DefaultTableModel) studentGradesTable.getModel();
        studentSearchPanel.add(new JScrollPane(studentGradesTable), BorderLayout.CENTER);

        // Second Search Section (Teacher-based)
        JPanel teacherSearchPanel = createSearchSection(
                "Име на преподавател:",
                "Предмет:",
                "Търси оценки по преподавател",
                this::searchTeacherGrades
        );
        teacherGradesTable = createTable(new String[]{"ID", "Преподавател", "Предмет", "Оценка", "Дата"});
        teacherTableModel = (DefaultTableModel) teacherGradesTable.getModel();
        teacherSearchPanel.add(new JScrollPane(teacherGradesTable), BorderLayout.CENTER);

        add(studentSearchPanel);
        add(teacherSearchPanel);
    }

    private JPanel createSearchSection(String label1, String label2, String buttonText, Runnable searchAction) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JButton button = new JButton(buttonText);

        inputPanel.add(new JLabel(label1));
        inputPanel.add(field1);
        inputPanel.add(new JLabel(label2));
        inputPanel.add(field2);
        inputPanel.add(button);

        // Store references based on section
        if(buttonText.equals("Търси оценки по ученик")) {
            txtStudentName = field1;
            txtTeacherSubject = field2;
            btnStudentSearch = button;
        } else {
            txtTeacherName = field1;
            txtSubject = field2;
            btnTeacherSearch = button;
        }

        button.addActionListener(e -> searchAction.run());
        panel.add(inputPanel, BorderLayout.NORTH);
        return panel;
    }

    private JTable createTable(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return new JTable(model);
    }

    private void searchStudentGrades() {
        String student = txtStudentName.getText();
        String subject = txtTeacherSubject.getText();
        List<Grade> grades = gradeDAO.searchGrades(student, subject);
        updateTable(studentTableModel, grades, true);
    }

    private void searchTeacherGrades() {
        String teacher = txtTeacherName.getText();
        String subject = txtSubject.getText();
        List<Grade> grades = gradeDAO.searchGradesByTeacher(teacher, subject);
        updateTable(teacherTableModel, grades, false);
    }

    private void updateTable(DefaultTableModel model, List<Grade> grades, boolean studentTable) {
        model.setRowCount(0);
        for (Grade g : grades) {
            Object[] row = studentTable ?
                    new Object[]{g.getGradeId(), g.getStudentId(), g.getSubjectId(), g.getGradeValue(), g.getExamDate()} :
                    new Object[]{g.getGradeId(), g.getTeacherId(), g.getSubjectId(), g.getGradeValue(), g.getExamDate()};
            model.addRow(row);
        }
    }
}