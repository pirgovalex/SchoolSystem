INSERT INTO users (id, username, password, role) VALUES 
(13, 'teacher8', 'teach890', 'TEACHER'),
(14, 'teacher9', 'teach901', 'TEACHER'),
(15, 'teacher10', 'teach012', 'TEACHER'),
(16, 'student5', 'stud901', 'STUDENT'),
(17, 'student6', 'stud012', 'STUDENT'),
(19, 'student8', 'stud678', 'STUDENT');

INSERT INTO teacher (teacher_id, first_name, last_name, subject, user_id) VALUES 
(9, 'Desislava', 'Koleva', 'Chemistry', 13),
(10, 'Kristian', 'Petrov', 'Geography', 14),
(11, 'Milena', 'Angelova', 'Literature', 15);

INSERT INTO students (user_id, first_name, last_name, class_level) VALUES 
(16, 'Borislav', 'Hristov', 2),
(17, 'Vesela', 'Mihaylova', 2),
(18, 'Martin', 'Kostov', 3),
(19, 'Stefania', 'Ivanova', 4);

INSERT INTO subjects (id, name) VALUES 
(11, 'Chemistry'),
(12, 'Geography'),
(13, 'Literature'),
(14, 'Programming'),
(15, 'Economics');
INSERT INTO grades (student_id, teacher_id, subject_id, grade, date_given, remarks) VALUES 
-- Existing students
(3, 6, 1, 4.90, '2025-04-01', 'Better than before'),
(3, 7, 2, 6.00, '2025-04-01', 'Outstanding!'),
(4, 7, 2, 5.50, '2025-04-01', 'Good work'),
(4, 6, 1, 4.75, '2025-04-01', 'Needs a bit more effort'),
(5, 9, 7, 4.00, '2025-04-01', 'Decent understanding'),
(5, 6, 8, 5.90, '2025-04-01', 'Well-written essay'),
-- New students
(16, 9, 7, 3.80, '2025-04-01', 'Struggles with Chemistry'),
(16, 10, 10, 5.50, '2025-04-01', 'Enjoys music'),
(17, 11, 11, 4.25, '2025-04-01', 'Good logical thinking'),
(17, 7, 12, 5.75, '2025-04-01', 'Creative and talented'),
(18, 9, 13, 6.00, '2025-04-01', 'Best in class'),
(18, 10, 14, 5.10, '2025-04-01', 'Learning well'),
(19, 11, 15, 4.50, '2025-04-01', 'Good understanding of finance'),
(19, 8, 3, 5.25, '2025-04-01', 'Strong historical knowledge');
INSERT INTO grades (student_id, teacher_id, subject_id, grade, date_given, remarks) VALUES 
-- Existing Students
(3, 5, 1, 5.75, '2025-04-02', 'Excellent in Mathematics'),
(3, 6, 2, 4.80, '2025-04-02', 'Needs improvement in Physics'),
(4, 7, 3, 5.60, '2025-04-02', 'Good historical analysis'),
(4, 9, 11, 4.90, '2025-04-02', 'Understanding Chemistry concepts well'),
(5, 10, 12, 5.20, '2025-04-02', 'Geography knowledge is solid'),
(5, 11, 13, 5.80, '2025-04-02', 'Excellent in Literature'),

-- New Students
(16, 9, 11, 4.30, '2025-04-02', 'Struggles with Chemistry basics'),
(16, 10, 12, 5.50, '2025-04-02', 'Geography skills improving'),
(17, 11, 13, 4.75, '2025-04-02', 'Needs more work in Literature'),
(17, 7, 1, 5.90, '2025-04-02', 'Quick learner in Mathematics'),
(18, 9, 14, 6.00, '2025-04-02', 'Top student in Programming'),
(18, 10, 15, 5.40, '2025-04-02', 'Good understanding of Economics'),
(19, 11, 3, 4.60, '2025-04-02', 'Historical analysis needs more depth'),
(19, 8, 2, 5.10, '2025-04-02', 'Physics fundamentals improving');

select * from grades;