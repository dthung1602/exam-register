USE examreg;

DELETE
FROM mysql.proc
WHERE db LIKE 'examreg';

DELIMITER //

-- ----------------   SEMESTER/MODULE -------------------

# create new semester
CREATE PROCEDURE CREATE_SEMESTER(IN my_start DATE,
                                 IN my_end DATE)
BEGIN
    INSERT INTO SEMESTER (start, end)
    VALUES (my_start, my_end);
END //

# read semester
CREATE PROCEDURE READ_SEMESTER(IN my_id INT)
BEGIN
    SELECT * FROM SEMESTER WHERE id = my_id;
END //

# update semester
CREATE PROCEDURE UPDATE_SEMESTER(IN semester_id INT,
                                 IN my_start DATE,
                                 IN my_end DATE)
BEGIN
    UPDATE SEMESTER
    SET start = my_start,
        end   = my_end
    WHERE id = semester_id;
END //

# create module
CREATE PROCEDURE CREATE_MODULE(IN my_name VARCHAR(50),
                               IN my_code VARCHAR(8),
                               IN my_semester INT)
BEGIN
    INSERT INTO MODULE(name, code, semester)
    VALUES (my_name, my_code, my_semester);
END //

# assign lecturer to a module
CREATE PROCEDURE ASSIGN_LECTURER(IN my_module INT,
                                 IN my_lecturer INT)
BEGIN
    INSERT INTO TEACH (module, lecturer)
        VALUE (my_module, my_lecturer);
END //

# update module
CREATE PROCEDURE UPDATE_MODULE(IN my_name VARCHAR(50),
                               IN my_code VARCHAR(8),
                               IN my_semester INT,
                               IN my_id INT)
BEGIN
    UPDATE MODULE
    SET name     = my_name,
        semester = my_semester,
        code     = my_code
    WHERE id = my_id;
END //

# List all the module of a given semester
CREATE PROCEDURE LIST_MODULE(IN my_semester INT)
BEGIN
    SELECT id, name, code
    FROM MODULE
    WHERE semester = my_semester;
END //

# List all modules that has overlap sessions
CREATE PROCEDURE LIST_OVERLAP_SESSION()
BEGIN
    SELECT S1.date,
           M1.code,
           M1.name,
           S1.start,
           S1.end,
           M2.code,
           M2.name,
           S2.start,
           S2.end
    FROM MODULE M1
             JOIN SESSION S1 on M1.id = S1.module
             JOIN MODULE M2
             JOIN SESSION S2 on M2.id = S2.module
    WHERE S1.module < S2.module
      AND S1.date = S2.date
      AND ((S1.start BETWEEN S2.start AND S2.end)
        OR (S1.end BETWEEN S2.start AND S2.end)
        OR (S1.start < S2.start AND S1.end > S2.end));
END //

# List all the modules that a given student has enrolled in
CREATE PROCEDURE LIST_MODULE_STU_ENROLL(IN my_student INT)
BEGIN
    SELECT *
    FROM MODULE M
             JOIN ENROLL E on E.module = M.id
    WHERE E.student = my_student;
END //

-- -------------------- EXAM REGISTER -----------------------7

# a student registers for an exam
CREATE PROCEDURE REGISTER_EXAM(IN my_student INT,
                               IN my_exam INT)
BEGIN
    INSERT INTO EXAM_REG(student, exam) VALUE
        (my_student, my_exam);
END //

# unregister a student for an exam
CREATE PROCEDURE UNREGISTER_EXAM(IN my_student INT,
                                 IN my_exam INT)
BEGIN
    DELETE
    FROM EXAM_REG
    WHERE student = my_student
      AND exam = my_exam;
END //

# views exam participant list
CREATE PROCEDURE VIEW_PARTICIPANTS(IN exam_id INT)
BEGIN
    SELECT S.code, A.fname, A.lname
    FROM EXAM E
             JOIN EXAM_REG ER on E.id = ER.exam
             JOIN STUDENT S on ER.student = S.account
             JOIN ACCOUNT A on S.account = A.id
    WHERE E.id = exam_id;
END //

# a student views his/her registered exam(s) in a given semester
CREATE PROCEDURE STUDENT_VIEW_EXAM(IN my_student INT)
BEGIN
    SELECT E.id, M.name, E.date, E.start, E.end
    FROM EXAM_REG ER
             JOIN EXAM E on ER.exam = E.id
             JOIN MODULE M on E.module = M.id
    WHERE ER.student = my_student;
END //

-- ----------------- SESSION -----------------------------

# create sessions for given module
CREATE PROCEDURE CREATE_SESSION(IN my_module INT,
                                IN my_date DATE,
                                IN my_start TIME,
                                IN my_end TIME)
BEGIN
    INSERT INTO SESSION(module, date, start, end) VALUE
        (my_module, my_date, my_start, my_end);
END //

# change session time
CREATE PROCEDURE CHANGE_SESSION_TIME(IN my_start TIME,
                                     IN my_end TIME,
                                     IN session_id INT)
BEGIN
    UPDATE SESSION
    SET start = my_start,
        end   = my_end
    WHERE id = session_id;
END //

# cancel a session
CREATE PROCEDURE CANCEL_SESSION(IN session_id INT)
BEGIN
    DELETE
    FROM SESSION
    WHERE id = session_id;
END //

# Check for the number of sessions the student "vth" attends in the given module
CREATE PROCEDURE LIST_SESSION_STUDENT(IN my_lname VARCHAR(50),
                                      IN my_module INT)
BEGIN
    SELECT COUNT(SI.session) AS 'attendance_count'
    FROM SIGN SI
             JOIN SESSION SE ON SE.id = SI.session
             JOIN STUDENT STU ON SI.student = STU.account
             JOIN ACCOUNT A ON STU.account = A.id
    WHERE A.lname = my_lname
      AND SE.module = my_module;
END //

# Check for the number of sessions the given student attends in all modules
CREATE PROCEDURE LIST_SESSION_IN_MODULES(IN my_student INT)
BEGIN
    SELECT STU.account, M.code, M.name, COUNT(SI.session) AS 'attendance_count'
    FROM SIGN SI
             JOIN SESSION SE ON SE.id = SI.session
             JOIN MODULE M on SE.module = M.id
             JOIN STUDENT STU ON SI.student = STU.account
             JOIN ACCOUNT A ON STU.account = A.id
    GROUP BY M.id, STU.account
    HAVING STU.account = my_student;
END //

# a student sign a session
CREATE PROCEDURE SIGN_SESSION(IN my_student INT,
                              IN my_session INT)
BEGIN
    INSERT INTO SIGN(student, session) VALUE
        (my_student, my_session);
END //

# show session of a given date
CREATE PROCEDURE SHOW_SESSION_ON(IN my_date DATE)
BEGIN
    SELECT S.start, S.end, M.name, A.lname
    FROM SESSION S
             JOIN MODULE M on S.module = M.id
             JOIN TEACH T on M.id = T.module
             JOIN LECTURER L on T.lecturer = L.account
             JOIN ACCOUNT A on L.account = A.id
    WHERE S.date = my_date;
END //

-- ------------------------ACCOUNT-----------------------------1

-- ---------------    LOGIN    --------------------2
# List all the accounts (username + password)
CREATE PROCEDURE LIST_ACCOUNT()
BEGIN
    SELECT username, password
    FROM ACCOUNT;
END //

# List account by a given ID
CREATE PROCEDURE LIST_ACCOUNT_ID(IN my_id INT)
BEGIN
    SELECT username, password
    FROM ACCOUNT
    WHERE id = my_id;
END //

# List account by a given username
CREATE PROCEDURE LIST_ACCOUNT_USERNAME(IN my_username VARCHAR(25))
BEGIN
    SELECT A.username, A.password
    FROM ACCOUNT A
    WHERE A.username = my_username;
END //

#add a new student
CREATE PROCEDURE ADD_NEW_STUDENT(IN my_username VARCHAR(25),
                                 IN my_password VARCHAR(128),
                                 IN my_fname VARCHAR(50),
                                 IN my_lname VARCHAR(50),
                                 IN my_code CHAR(8))
BEGIN
    INSERT INTO ACCOUNT(username, password, fname, lname) VALUE
        (my_username, my_password, my_fname, my_lname);
    INSERT INTO STUDENT VALUE (LAST_INSERT_ID(), my_code);
END //

#add a new lecturer
CREATE PROCEDURE ADD_NEW_LECTURER(IN my_username VARCHAR(25),
                                  IN my_password VARCHAR(128),
                                  IN my_fname VARCHAR(50),
                                  IN my_lname VARCHAR(50))
BEGIN
    INSERT INTO ACCOUNT(username, password, fname, lname) VALUE
        (my_username, my_password, my_fname, my_lname);
    INSERT INTO LECTURER VALUE (LAST_INSERT_ID());
END //

#add a new assistant
CREATE PROCEDURE ADD_NEW_ASSISTANT(IN my_username VARCHAR(25),
                                   IN my_password VARCHAR(128),
                                   IN my_fname VARCHAR(50),
                                   IN my_lname VARCHAR(50))
BEGIN
    INSERT INTO ACCOUNT(username, password, fname, lname) VALUE
        (my_username, my_password, my_fname, my_lname);
    INSERT INTO ASSISTANT VALUE (LAST_INSERT_ID());
END //

# a user  updates his/her account first name, last name base on his/her id
CREATE PROCEDURE UPDATE_LNAME_FNAME(IN my_id INT,
                                    IN my_fname VARCHAR(50),
                                    IN my_lname VARCHAR(50))
BEGIN
    UPDATE ACCOUNT A
    SET A.fname = my_fname,
        A.lname = my_lname
    WHERE A.id = my_id;
END //

# change password of a user
CREATE PROCEDURE CHANGE_PASSWORD(IN my_id INT,
                                 IN my_password VARCHAR(128))
BEGIN
    UPDATE ACCOUNT A
    SET A.password = my_password
    WHERE A.id = my_id;
END //

-- ----------------ENROLL-------------------
# the assistant enrolls students to a module
CREATE PROCEDURE ENROLL_MODULE(IN student_id INT,
                               IN module_id INT)
BEGIN
    INSERT INTO ENROLL(student, module) VALUE (student_id, module_id);
END //

# the assistant views all the student in a given module
CREATE PROCEDURE VIEW_STUDENTS_OF_MODULE(IN module_id INT)
BEGIN
    SELECT S.code, A.fname, A.lname
    FROM ENROLL E
             JOIN STUDENT S on E.student = S.account
             JOIN ACCOUNT A on S.account = A.id
             JOIN MODULE M on E.module = M.id
    WHERE M.id = module_id;
END //

-- ------------------------UTILS-----------------------------1

# truncate all tables in database
CREATE PROCEDURE TRUNCATE_ALL()
BEGIN
    SET FOREIGN_KEY_CHECKS = 0;
    TRUNCATE TABLE ENROLL;
    TRUNCATE TABLE TEACH;
    TRUNCATE TABLE SIGN;
    TRUNCATE TABLE EXAM_REG;
    TRUNCATE TABLE EXAM;
    TRUNCATE TABLE SESSION;
    TRUNCATE TABLE MODULE;
    TRUNCATE TABLE SEMESTER;
    TRUNCATE TABLE STUDENT;
    TRUNCATE TABLE LECTURER;
    TRUNCATE TABLE ASSISTANT;
    TRUNCATE TABLE ACCOUNT;
    SET FOREIGN_KEY_CHECKS = 1;
END //

DELIMITER ;