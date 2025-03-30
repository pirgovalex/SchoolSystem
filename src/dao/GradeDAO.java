package dao;

import model.Grade;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    public void addGrade(Grade grade) {
        String sql = "INSERT INTO Grades (student_id, teacher_id, subject_id, grade, date_given, remarks) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grade.getStudentId());
            stmt.setInt(2, grade.getTeacherId());
            stmt.setInt(3, grade.getSubjectId()); // New
            stmt.setDouble(4, grade.getGradeValue());
            stmt.setDate(5, grade.getExamDate());
            stmt.setString(6, grade.getRemarks());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGrade(Grade grade) {
        String sql = "UPDATE Grades SET student_id=?, teacher_id=?, subject_id=?, grade=?, date_given=?, remarks=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grade.getStudentId());
            stmt.setInt(2, grade.getTeacherId());
            stmt.setInt(3, grade.getSubjectId()); // New
            stmt.setDouble(4, grade.getGradeValue());
            stmt.setDate(5, grade.getExamDate());
            stmt.setString(6, grade.getRemarks());
            stmt.setInt(7, grade.getGradeId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGrade(int gradeId) {
        String sql = "DELETE FROM Grades WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gradeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM Grades";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("id"));
                grade.setSubjectId(rs.getInt("subject_id"));
                grade.setStudentId(rs.getInt("student_id"));
                grade.setTeacherId(rs.getInt("teacher_id"));
                grade.setGradeValue(rs.getDouble("grade"));
                grade.setExamDate(rs.getDate("date_given"));  // Use correct column name
                grade.setRemarks(rs.getString("remarks"));
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    // Пример за търсене по повече от един критерий (използвайки данни от Student и Teacher)
    public List<Grade> searchGrades(String studentName, String teacherSubject) {
        List<Grade> grades = new ArrayList<>();
        // -----------------------------------------
        String sql = "SELECT g.* FROM grades g " +
                "JOIN students s ON g.student_id = s.user_id " +
                "JOIN teacher t ON g.teacher_id = t.user_id " +  // Changed to user_id since teacher is linked to users
                "WHERE CONCAT(s.first_name, ' ', s.last_name) LIKE ? " +
                "AND t.subject LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + studentName + "%");
            stmt.setString(2, "%" + teacherSubject + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("id"));
                grade.setStudentId(rs.getInt("student_id"));
                grade.setTeacherId(rs.getInt("teacher_id"));
                grade.setGradeValue(rs.getDouble("grade"));
                grade.setExamDate(rs.getDate("date_given"));
                grade.setRemarks(rs.getString("remarks"));
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }
}
