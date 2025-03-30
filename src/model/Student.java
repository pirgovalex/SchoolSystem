package model;

public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private String studentClass;

    public Student() {}

    public Student(int studentId, String firstName, String lastName, String studentClass) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentClass = studentClass;
    }

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentClass() {
        return studentClass;
    }
    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}
