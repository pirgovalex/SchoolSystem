-- 1. Създаване на база данни
CREATE DATABASE school_system;
USE school_system;

-- 2. Създаване на таблица за потребителите (администратори, учители, ученици)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,  -- В реална система трябва да е хеширана!
    role ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL
);


-- 3. Създаване на таблица за класовете
CREATE TABLE classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. Създаване на таблица за предметите
CREATE TABLE subjects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL 
    );
ALTER TABLE subjects ADD UNIQUE (name);
SHOW CREATE TABLE subjects;
-- 4. Now you can add the foreign key in 'teacher'
ALTER TABLE teacher ADD FOREIGN KEY (subject) REFERENCES subjects(name);
-- 5. Създаване на таблица за записване на учениците в класове
CREATE TABLE student_classes (
    student_id INT NOT NULL,
    class_id INT NOT NULL,
    PRIMARY KEY (student_id, class_id),
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);

-- 6. Създаване на таблица за оценките
CREATE TABLE grades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    grade DECIMAL(3,2) CHECK (grade >= 2.00 AND grade <= 6.00),
    date_given TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- 7. Добавяне на примерни потребители
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'ADMIN'),
('teacher1', 'teach123', 'TEACHER'),
('student1', 'stud123', 'STUDENT'),
('student2', 'stud456', 'STUDENT');

-- 8. Добавяне на класове (учители са свързани чрез teacher_id)
INSERT INTO classes (name, teacher_id) VALUES 
('Class A', 2),
('Class B', 2);

-- 9. Добавяне на предмети
INSERT INTO subjects (name) VALUES 
('Mathematics'),
('Physics'),
('History');

-- 10. Записване на ученици в класове
INSERT INTO student_classes (student_id, class_id) VALUES 
(3, 1),
(4, 2);

-- 11. Добавяне на оценки за учениците
INSERT INTO grades (student_id, subject_id, grade) VALUES 
(3, 1, 5.50), 
(3, 2, 4.75), 
(3, 3, 6.00), 
(4, 1, 3.20),
(4, 2, 5.00);

create table teacher( teacher_id int PRIMARY KEY
,first_name varchar (262), 
last_name varchar (262),
subject varchar  (262));
INSERT INTO TEACHER VALUES (1,"Aleks", "P" , "Informatics"),
(2,"Aleks","M","Special Pedagogy"),(3,"Plamen","Pirgov","Veterinarnost");
alter table teacher ADD FOREIGN KEY teacher(subject)  REFERENCES  subjects(name);


-- Create students table linked to users table
CREATE TABLE students (
    user_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    class_level INT CHECK (class_level BETWEEN 1 AND 7), -- Grades 1-4
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

select * from students;
-- Insert sample student data (matching existing users with role 'STUDENT')
INSERT INTO students (user_id, first_name, last_name,  class_level) VALUES 
(3, 'Ivan', 'Petrov', 2),
(4, 'Maria', 'Ivanova',  3);

ALTER TABLE grades ADD COLUMN teacher_id INT NOT NULL;
ALTER TABLE grades ADD FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE;
-- );
select * from students;
-- 1. Add user_id to teacher table
ALTER TABLE teacher ADD COLUMN user_id INT UNIQUE;
ALTER TABLE teacher ADD FOREIGN KEY (user_id) REFERENCES users(id);

-- 2. Link existing teachers to users
UPDATE teacher SET user_id = 2 WHERE first_name = 'Aleks' AND last_name = 'M';

-- 3. Modify grades table to use user_id
ALTER TABLE grades MODIFY teacher_id INT;
UPDATE grades SET teacher_id = 2;
ALTER TABLE grades MODIFY teacher_id INT NOT NULL;

-- 4. Add the foreign key
ALTER TABLE grades ADD FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE;
SELECT * FROM TEACHER ; SELECT * FROM USERS;
INSERT INTO USERS VALUES(5,"teacher2","teach1234","TEACHER"),(6,"teacher3","teach234","TEACHER"),(7,"teacher4","teach345","TEACHER");
UPDATE teacher SET USER_ID = 7 WHERE teacher.last_name = "Pirgov";


INSERT INTO TEACHER VALUES (4, "Matematiko", "M", "Mathematics", 18);

INSERT INTO users (id, username, password, role) VALUES (18, 'teacherX', '12345', 'TEACHER');



select * from students;
select * from teacher;
select * from subjects;
select * from grades;

INSERT INTO grades (id, student_id, teacher_id, grade, date_given, subject_id, remarks) 
VALUES (6, 3, 5, 6.00, '2025-02-28', 1, 'The best!');
ALTER TABLE grades 
ADD COLUMN remarks varchar(262);


SELECT s.user_id, s.first_name, s.last_name, t.teacher_id, t.subject 
FROM students s, teacher t 
WHERE CONCAT(s.first_name, ' ', s.last_name) LIKE '%ivan petrov%' 
AND t.subject LIKE '%informatics%';

