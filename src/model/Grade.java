package model;

import java.sql.Date;

public class Grade {
    private int subjectId;
    private int gradeId;
    private int studentId;
    private int teacherId;
    private double gradeValue;
    private Date examDate;
    private String remarks;

    public Grade() {}

    public Grade(int gradeId, int studentId, int teacherId, int subjectId,  double gradeValue, Date examDate, String remarks) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.gradeValue = gradeValue;
        this.examDate = examDate;
        this.remarks = remarks;
        this.subjectId = subjectId;
    }

    public int getGradeId() {
        return gradeId;
    }
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public double getGradeValue() {
        return gradeValue;
    }
    public void setGradeValue(double gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Date getExamDate() {
        return examDate;
    }
    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
}
