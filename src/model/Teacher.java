package model;

public class Teacher {
    private int teacherId;
    private String firstName;
    private String lastName;
    private String subject;

    public Teacher() {}

    public Teacher(int teacherId, String firstName, String lastName, String subject) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    public int getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
