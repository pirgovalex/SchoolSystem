-- 1. Create Database
DROP DATABASE IF EXISTS school_system;
CREATE DATABASE school_system;
USE school_system;

-- 2. Create Tables
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL
);

CREATE TABLE subjects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE teacher (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(262),
    last_name VARCHAR(262),
    subject VARCHAR(262),
    user_id INT UNIQUE,
    FOREIGN KEY (subject) REFERENCES subjects(name),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE students (
    user_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    class_level INT CHECK (class_level BETWEEN 1 AND 7),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE student_classes (
    student_id INT NOT NULL,
    class_id INT NOT NULL,
    PRIMARY KEY (student_id, class_id),
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);

CREATE TABLE grades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    teacher_id INT NOT NULL,
    grade DECIMAL(3,2) CHECK (grade >= 2.00 AND grade <= 6.00),
    date_given DATE DEFAULT (CURRENT_DATE),
    remarks VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. Insert Sample Data
-- Users
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('teacher1', 'teach123', 'TEACHER'),
('student1', 'stud123', 'STUDENT'),
('student2', 'stud456', 'STUDENT'),
('teacher2', 'teach1234', 'TEACHER'),
('teacher3', 'teach234', 'TEACHER'),
('teacher4', 'teach345', 'TEACHER'),
('teacher8', 'teach890', 'TEACHER'),
('teacher9', 'teach901', 'TEACHER'),
('teacher10', 'teach012', 'TEACHER'),
('student5', 'stud901', 'STUDENT'),
('student6', 'stud012', 'STUDENT'),
('student8', 'stud678', 'STUDENT');

-- Subjects
INSERT INTO subjects (name) VALUES
('Mathematics'),
('Physics'),
('History'),
('Chemistry'),
('Geography'),
('Literature'),
('Programming'),
('Economics');

-- Teachers
INSERT INTO teacher (first_name, last_name, subject, user_id) VALUES
('Aleks', 'P', 'Informatics', 2),
('Aleks', 'M', 'Special Pedagogy', 5),
('Plamen', 'Pirgov', 'Veterinarnost', 7),
('Desislava', 'Koleva', 'Chemistry', 8),
('Kristian', 'Petrov', 'Geography', 9),
('Milena', 'Angelova', 'Literature', 10);

-- Students
INSERT INTO students (user_id, first_name, last_name, class_level) VALUES
(3, 'Ivan', 'Petrov', 2),
(4, 'Maria', 'Ivanova', 3),
(11, 'Borislav', 'Hristov', 2),
(12, 'Vesela', 'Mihaylova', 2),
(13, 'Martin', 'Kostov', 3);

-- Classes
INSERT INTO classes (name, teacher_id) VALUES
('Class A', 2),
('Class B', 5);

-- Student Classes
INSERT INTO student_classes (student_id, class_id) VALUES
(3, 1),
(4, 2),
(11, 1),
(12, 2),
(13, 1);

-- Grades
INSERT INTO grades (student_id, subject_id, teacher_id, grade, date_given, remarks) VALUES
(3, 1, 2, 5.50, '2025-03-30', 'Excellent performance'),
(3, 2, 5, 4.75, '2025-03-30', 'Needs improvement'),
(4, 1, 2, 3.20, '2025-03-30', 'Good effort'),
(11, 4, 8, 4.90, '2025-04-01', 'Understanding concepts'),
(12, 5, 9, 5.50, '2025-04-01', 'Good participation'),
(13, 7, 2, 6.00, '2025-04-01', 'Outstanding work');

-- Verification Queries
SELECT * FROM users;
SELECT * FROM subjects;
SELECT * FROM teacher;
SELECT * FROM students;
SELECT * FROM classes;
SELECT * FROM student_classes;
SELECT * FROM grades;