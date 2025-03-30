package view;

import dao.TeacherDAO;
import model.Teacher;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TeacherPanel extends JPanel {
    private TeacherDAO teacherDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtFirstName, txtLastName, txtSubject;
    private JButton btnAdd, btnUpdate, btnDelete;

    public TeacherPanel() {
        teacherDAO = new TeacherDAO();
        setLayout(new BorderLayout());
        initComponents();
        loadTeachers();
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        inputPanel.add(new JLabel("Име:"));
        txtFirstName = new JTextField();
        inputPanel.add(txtFirstName);

        inputPanel.add(new JLabel("Фамилия:"));
        txtLastName = new JTextField();
        inputPanel.add(txtLastName);

        inputPanel.add(new JLabel("Предмет:"));
        txtSubject = new JTextField();
        inputPanel.add(txtSubject);

        btnAdd = new JButton("Добави");
        inputPanel.add(btnAdd);
        btnUpdate = new JButton("Редактирай");
        inputPanel.add(btnUpdate);
        btnDelete = new JButton("Изтрий");
        inputPanel.add(btnDelete);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Име", "Фамилия", "Предмет"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addTeacher());
        btnUpdate.addActionListener(e -> updateTeacher());
        btnDelete.addActionListener(e -> deleteTeacher());
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0){
                    txtFirstName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtLastName.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtSubject.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });
    }

    private void loadTeachers() {
        tableModel.setRowCount(0);
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        for (Teacher t : teachers) {
            tableModel.addRow(new Object[]{t.getTeacherId(), t.getFirstName(), t.getLastName(), t.getSubject()});
        }
    }

    private void addTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName(txtFirstName.getText());
        teacher.setLastName(txtLastName.getText());
        teacher.setSubject(txtSubject.getText());
        teacherDAO.addTeacher(teacher);
        loadTeachers();
        clearFields();
    }

    private void updateTeacher() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Teacher teacher = new Teacher();
            teacher.setTeacherId((int) tableModel.getValueAt(selectedRow, 0));
            teacher.setFirstName(txtFirstName.getText());
            teacher.setLastName(txtLastName.getText());
            teacher.setSubject(txtSubject.getText());
            teacherDAO.updateTeacher(teacher);
            loadTeachers();
            clearFields();
        }
    }

    private void deleteTeacher() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int teacherId = (int) tableModel.getValueAt(selectedRow, 0);
            teacherDAO.deleteTeacher(teacherId);
            loadTeachers();
            clearFields();
        }
    }

    private void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtSubject.setText("");
    }
}
