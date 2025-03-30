package dao;

import model.Student;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public void addStudent(Student student) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Create user first
            int userId = createStudentUser(conn, student);
            if (userId == -1) {
                conn.rollback();
                return;
            }

            // 2. Create student record
            String sql = "INSERT INTO students (user_id, first_name, last_name, class_level) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setString(2, student.getFirstName());
                stmt.setString(3, student.getLastName());
                stmt.setInt(4, Integer.parseInt(student.getStudentClass()));
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int createStudentUser(Connection conn, Student student) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Generate username from name
            String username = student.getFirstName().toLowerCase() + "." + student.getLastName().toLowerCase();

            stmt.setString(1, username);
            stmt.setString(2, "temp_password"); // Should be hashed in real application
            stmt.setString(3, "STUDENT");
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    // Keep other methods the same but add transaction handling
    public void updateStudent(Student student) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update student
            String sql = "UPDATE students SET first_name=?, last_name=?, class_level=? WHERE user_id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, student.getFirstName());
                stmt.setString(2, student.getLastName());
                stmt.setInt(3, Integer.parseInt(student.getStudentClass()));
                stmt.setInt(4, student.getStudentId());
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            // Handle rollback and errors
        } finally {
            // Close connection
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("user_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setStudentClass(rs.getString("class_level"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM Students WHERE user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}