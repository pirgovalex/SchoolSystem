package view;

import dao.StudentDAO;
import model.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentPanel extends JPanel {
    private StudentDAO studentDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtFirstName, txtLastName, txtClass;
    private JButton btnAdd, btnUpdate, btnDelete;

    public StudentPanel() {
        studentDAO = new StudentDAO();
        setLayout(new BorderLayout());
        initComponents();
        loadStudents();
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        inputPanel.add(new JLabel("Име:"));
        txtFirstName = new JTextField();
        inputPanel.add(txtFirstName);

        inputPanel.add(new JLabel("Фамилия:"));
        txtLastName = new JTextField();
        inputPanel.add(txtLastName);

        inputPanel.add(new JLabel("Клас:"));
        txtClass = new JTextField();
        inputPanel.add(txtClass);

        btnAdd = new JButton("Добави");
        inputPanel.add(btnAdd);
        btnUpdate = new JButton("Редактирай");
        inputPanel.add(btnUpdate);
        btnDelete = new JButton("Изтрий");
        inputPanel.add(btnDelete);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Име", "Фамилия", "Клас"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0){
                    txtFirstName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtLastName.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtClass.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getStudentId(), s.getFirstName(), s.getLastName(), s.getStudentClass()});
        }
    }

    private void addStudent() {
        Student student = new Student();
        student.setFirstName(txtFirstName.getText());
        student.setLastName(txtLastName.getText());
        student.setStudentClass(txtClass.getText());
        studentDAO.addStudent(student);
        loadStudents();
        clearFields();
    }

    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Student student = new Student();
            student.setStudentId((int) tableModel.getValueAt(selectedRow, 0));
            student.setFirstName(txtFirstName.getText());
            student.setLastName(txtLastName.getText());
            student.setStudentClass(txtClass.getText());
            studentDAO.updateStudent(student);
            loadStudents();
            clearFields();
        }
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            studentDAO.deleteStudent(studentId);
            loadStudents();
            clearFields();
        }
    }

    private void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtClass.setText("");
    }
}