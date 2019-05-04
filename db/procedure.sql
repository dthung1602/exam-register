USE examreg;

DROP PROCEDURE IF EXISTS CREATE_SEMESTER;
DROP PROCEDURE IF EXISTS READ_SEMESTER;
DROP PROCEDURE IF EXISTS UPDATE_SEMESTER;
DROP PROCEDURE IF EXISTS VIEW_ALL_SEMESTER;
DROP PROCEDURE IF EXISTS VIEW_LAST_SEMESTER;
DROP PROCEDURE IF EXISTS DELETE_SEMESTER;
DROP PROCEDURE IF EXISTS CREATE_MODULE;
DROP PROCEDURE IF EXISTS ASSIGN_LECTURER;
DROP PROCEDURE IF EXISTS UPDATE_MODULE;
DROP PROCEDURE IF EXISTS CANCEL_MODULE;
DROP PROCEDURE IF EXISTS VIEW_A_MODULE;
DROP PROCEDURE IF EXISTS LIST_ALL_MODULES;
DROP PROCEDURE IF EXISTS LIST_MODULE_IN_SEMESTER;
DROP PROCEDURE IF EXISTS LIST_OVERLAP_SESSION;
DROP PROCEDURE IF EXISTS LIST_MODULE_STU_ENROLL;
DROP PROCEDURE IF EXISTS VIEW_LAST_MODULE;
DROP PROCEDURE IF EXISTS REGISTER_EXAM;
DROP PROCEDURE IF EXISTS UNREGISTER_EXAM;
DROP PROCEDURE IF EXISTS VIEW_PARTICIPANTS;
DROP PROCEDURE IF EXISTS STUDENT_VIEW_EXAM;
DROP PROCEDURE IF EXISTS VIEW_EXAM;
DROP PROCEDURE IF EXISTS CREATE_SESSION;
DROP PROCEDURE IF EXISTS CHANGE_SESSION_TIME;
DROP PROCEDURE IF EXISTS CANCEL_SESSION;
DROP PROCEDURE IF EXISTS LIST_SESSION_ATTENDED;
DROP PROCEDURE IF EXISTS LIST_SESSION_IN_MODULE;
DROP PROCEDURE IF EXISTS SIGN_SESSION;
DROP PROCEDURE IF EXISTS SHOW_SESSION_ON;
DROP PROCEDURE IF EXISTS LIST_ACCOUNT;
DROP PROCEDURE IF EXISTS LIST_ACCOUNT_ID;
DROP PROCEDURE IF EXISTS LIST_ACCOUNT_USERNAME;
DROP PROCEDURE IF EXISTS ADD_NEW_STUDENT;
DROP PROCEDURE IF EXISTS ADD_NEW_LECTURER;
DROP PROCEDURE IF EXISTS ADD_NEW_ASSISTANT;
DROP PROCEDURE IF EXISTS UPDATE_USER;
DROP PROCEDURE IF EXISTS UPDATE_STUDENT;
DROP PROCEDURE IF EXISTS CHANGE_PASSWORD;
DROP PROCEDURE IF EXISTS ENROLL_MODULE;
DROP PROCEDURE IF EXISTS VIEW_STUDENTS_OF_MODULE;
DROP PROCEDURE IF EXISTS DELETE_STUDENT_IN_MODULE;
DROP PROCEDURE IF EXISTS CREATE_EXAM;
DROP PROCEDURE IF EXISTS CANCEL_EXAM;
DROP PROCEDURE IF EXISTS EDIT_EXAM;
DROP PROCEDURE IF EXISTS VIEW_ALL_EXAM;
DROP PROCEDURE IF EXISTS VIEW_EXAM_WITH_ID;
DROP PROCEDURE IF EXISTS TRUNCATE_ALL;
DROP PROCEDURE IF EXISTS READ_EXAM;
DROP PROCEDURE IF EXISTS LIST_ALL_LECTURERS;
DROP PROCEDURE IF EXISTS LIST_ALL_STUDENTS;
DROP PROCEDURE IF EXISTS LIST_ALL_ASSISTANTS;
DROP PROCEDURE IF EXISTS ADD_EXAM;
DROP PROCEDURE IF EXISTS UPDATE_EXAM;
DROP PROCEDURE IF EXISTS DELETE_EXAM;
DROP PROCEDURE IF EXISTS DELETE_USER;


DELIMITER //

-- ---------------- SEMESTER -------------------

# create new semester
CREATE PROCEDURE CREATE_SEMESTER(IN my_start DATE,
                                 IN my_end DATE)
BEGIN
    INSERT INTO SEMESTER (start, end)
    VALUES (my_start, my_end);
    SELECT id, start, end FROM SEMESTER WHERE id = LAST_INSERT_ID();
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

# List all semesters
CREATE PROCEDURE VIEW_ALL_SEMESTER()
BEGIN
    SELECT * FROM SEMESTER;
END //

# View last semester info
CREATE PROCEDURE VIEW_LAST_SEMESTER()
BEGIN
    SELECT *
    FROM SEMESTER
    ORDER BY id DESC
    LIMIT 1;
END //

# Delete a Semester
CREATE PROCEDURE DELETE_SEMESTER(IN semester_id INT)
BEGIN
    DELETE FROM SEMESTER WHERE id = semester_id;
END //

-- ------------------------ MODULE --------------------------

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
                               IN lecturer_id INT,
                               IN module_id INT)
BEGIN
    UPDATE MODULE
    SET name = my_name,
        code = my_code
    WHERE id = module_id;

    DELETE FROM TEACH WHERE TEACH.module = module_id;

    INSERT INTO TEACH
    VALUES (module_id, lecturer_id);

END //

CREATE PROCEDURE CANCEL_MODULE(IN module_id INT)
BEGIN
    DELETE
    FROM MODULE
    WHERE id = module_id;
END //

# View a module's info
CREATE PROCEDURE VIEW_A_MODULE(IN module_id INT)
BEGIN
    SELECT M.id,
           code,
           name,
           semester,
           start,
           end,
           CONCAT(A.fname, ' ', A.lname) AS 'lecturer',
           CONCAT(A.id, '')              AS 'lecturer_id'
    FROM MODULE M
             JOIN SEMESTER S on M.semester = S.id
             JOIN TEACH T on M.id = T.module
             JOIN LECTURER L on T.lecturer = L.account
             JOIN ACCOUNT A on L.account = A.id
    WHERE M.id = module_id;
END //

# list all of modules
CREATE PROCEDURE LIST_ALL_MODULES()
BEGIN
    SELECT CONCAT('Semester ', S.id) AS semester, S.start, S.end, M.name, M.code, M.id
    FROM MODULE M,
         SEMESTER S
    WHERE S.id = M.semester;
END //

# List all the module of a given semester
CREATE PROCEDURE LIST_MODULE_IN_SEMESTER(IN my_semester INT)
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

# View last semester info
CREATE PROCEDURE VIEW_LAST_MODULE()
BEGIN
    SELECT *
    FROM MODULE
    ORDER BY id DESC
    LIMIT 1;
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

#############################################################
CREATE PROCEDURE ADD_EXAM(IN moduleId INT,
                          IN examDate DATE,
                          IN my_deadline DATE,
                          IN my_start TIME,
                          IN my_end TIME)
BEGIN
    INSERT INTO EXAM(module, date, deadline, start, end) VALUE
        (moduleId, examDate, my_deadline, my_start, my_end);
END //

CREATE PROCEDURE DELETE_EXAM(IN examId INT)

BEGIN
    DELETE
    FROM EXAM
    WHERE examId = id;
END //

CREATE PROCEDURE UPDATE_EXAM(IN examId INT,
                             IN moduleId INT,
                             IN examDate DATE,
                             IN my_deadline DATE,
                             IN my_start TIME,
                             IN my_end TIME)

BEGIN
    UPDATE EXAM
    SET module   = moduleId,
        date     = examDate,
        deadline = my_deadline,
        start    = my_start,
        end      = my_end
    WHERE id = examId;
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

CREATE PROCEDURE VIEW_EXAM()
BEGIN
    SELECT id, module, date, deadline, start, end
    FROM EXAM;
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
                                     IN my_date DATE,
                                     IN session_id INT)
BEGIN
    UPDATE SESSION
    SET start = my_start,
        end   = my_end,
        date  = my_date
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
CREATE PROCEDURE LIST_SESSION_ATTENDED(IN my_module INT)
BEGIN
    SELECT COUNT(*) INTO @total_session
    FROM SESSION
    WHERE SESSION.module = my_module;

    SELECT STU.account AS 'student_id', COUNT(SI.session) AS 'attendance_count', @total_session AS 'total_session'
    FROM SIGN SI
             JOIN SESSION SE ON SE.id = SI.session
             JOIN STUDENT STU ON SI.student = STU.account
             JOIN ACCOUNT A ON STU.account = A.id
    WHERE SE.module = my_module;
END //

# Check for the number of sessions the given student attends in all modules
CREATE PROCEDURE LIST_SESSION_IN_MODULE(IN my_module INT)
BEGIN
    SELECT id, date, start, end
    FROM SESSION
    WHERE SESSION.module = my_module;
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

-- ---------------    LOGIN    --------------------
# List all the accounts (username + password)
CREATE PROCEDURE LIST_ACCOUNT()
BEGIN
    SELECT username, password
    FROM ACCOUNT;
END //

# List account by a given ID
CREATE PROCEDURE LIST_ACCOUNT_ID(IN my_id INT)
BEGIN
    SET @role = 'student';
    IF EXISTS(SELECT * FROM ASSISTANT WHERE account = my_id) THEN
        SET @role = 'assistant';
    ELSEIF EXISTS(SELECT * FROM LECTURER WHERE account = my_id) THEN
        SET @role = 'lecturer';
    END IF;

    SELECT username, password, id, @role AS 'role'
    FROM ACCOUNT
    WHERE id = my_id;
END //

# List account by a given username
CREATE PROCEDURE LIST_ACCOUNT_USERNAME(IN my_username VARCHAR(25))
BEGIN
    SET @id := 0;

    SELECT ACC.username,
           ACC.password,
           ACC.id
           INTO @username, @password, @id
    FROM ACCOUNT ACC
    WHERE ACC.username = my_username;

    IF @id = 0 THEN
        SELECT 'id', 'username', 'password', 'role'
        FROM dual
        WHERE false;
    ELSE
        SET @role = 'student';
        IF EXISTS(SELECT * FROM ASSISTANT WHERE account = @id) THEN
            SET @role = 'assistant';
        ELSEIF EXISTS(SELECT * FROM LECTURER WHERE account = @id) THEN
            SET @role = 'lecturer';
        END IF;

        SELECT @id AS 'id', @username AS 'username', @password AS 'password', @role AS 'role';
    END IF;
END //

CREATE PROCEDURE LIST_ALL_LECTURERS()
BEGIN
    SELECT id, username, fname, lname
    FROM LECTURER L
             JOIN ACCOUNT A ON L.account = A.id;
END //

CREATE PROCEDURE LIST_ALL_ASSISTANTS()
BEGIN
    SELECT id, username, fname, lname
    FROM ASSISTANT AST
             JOIN ACCOUNT A ON AST.account = A.id;
END //

CREATE PROCEDURE LIST_ALL_STUDENTS()
BEGIN
    SELECT id, username, fname, lname, code
    FROM STUDENT S
             JOIN ACCOUNT A ON S.account = A.id;
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
    INSERT INTO STUDENT(code, account) VALUE (my_code, LAST_INSERT_ID());
    SELECT LAST_INSERT_ID() AS 'id';
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
    SELECT LAST_INSERT_ID() AS 'id';
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
    SELECT LAST_INSERT_ID() AS 'id';
END //

CREATE PROCEDURE DELETE_USER(IN user_id INT)
BEGIN
    DELETE FROM STUDENT WHERE account = user_id;
    DELETE FROM LECTURER WHERE account = user_id;
    DELETE FROM ASSISTANT WHERE account = user_id;
    DELETE FROM ACCOUNT WHERE id = user_id;
END //

# a user  updates his/her account first name, last name base on his/her id
CREATE PROCEDURE UPDATE_USER(IN my_id INT,
                             IN my_username VARCHAR(50),
                             IN my_fname VARCHAR(50),
                             IN my_lname VARCHAR(50))
BEGIN
    UPDATE ACCOUNT A
    SET A.username = my_username,
        A.fname    = my_fname,
        A.lname    = my_lname
    WHERE A.id = my_id;
END //

CREATE PROCEDURE UPDATE_STUDENT(IN my_id INT,
                                IN my_username VARCHAR(50),
                                IN my_fname VARCHAR(50),
                                IN my_lname VARCHAR(50),
                                IN my_code VARCHAR(8))
BEGIN
    CALL UPDATE_USER(my_id, my_username, my_fname, my_lname);
    UPDATE STUDENT
    SET code = my_code
    WHERE account = my_id;
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
CREATE PROCEDURE ENROLL_MODULE(IN student_code INT,
                               IN module_id INT)
BEGIN
    SELECT STUDENT.account INTO @student_id
    FROM STUDENT
    WHERE STUDENT.code = student_code;
    INSERT INTO ENROLL(student, module) VALUE (@student_id, module_id);
END //

# the assistant views all the student in a given module
CREATE PROCEDURE VIEW_STUDENTS_OF_MODULE(IN module_id INT)
BEGIN
    SELECT A.id, S.code, A.fname, A.lname
    FROM ENROLL E
             JOIN STUDENT S on E.student = S.account
             JOIN ACCOUNT A on S.account = A.id
    WHERE E.module = module_id;
END //

CREATE PROCEDURE DELETE_STUDENT_IN_MODULE(IN student_id INT,
                                          IN module_id INT)
BEGIN
    DELETE
    FROM ENROLL
    WHERE student = student_id
      AND module = module_id;
END //

-- ----------------------ASSISTANT/EXAM--------------

#The assistant create an exam

CREATE PROCEDURE CREATE_EXAM(IN module_id INT,
                             IN exam_date DATE,
                             IN exam_deadline DATE,
                             IN exam_start TIME,
                             IN exam_end TIME)
BEGIN
    INSERT INTO EXAM(module, date, deadline, start, end)
    VALUES (module_id, exam_date, exam_deadline, exam_start, exam_end);
END //

#The assistant cancel an exam

CREATE PROCEDURE CANCEL_EXAM(IN exam_id INT)
BEGIN
    DELETE
    FROM EXAM
    WHERE id = exam_id;
END //

#The assistant edit an exam
CREATE PROCEDURE EDIT_EXAM(IN exam_id INT,
                           IN module_id INT,
                           IN exam_date DATE,
                           IN exam_deadline DATE,
                           IN exam_start TIME,
                           IN exam_end TIME)

BEGIN
    UPDATE EXAM
    SET module   = module_id,
        date     = exam_date,
        deadline = exam_deadline,
        start    = exam_start,
        end      = exam_end
    WHERE id = exam_id;
END //

-- ------------------------EXAM-------------------

CREATE PROCEDURE READ_EXAM(IN exam_id INT)
BEGIN
    SELECT * FROM EXAM WHERE id = exam_id;
end //

#View all exams list
CREATE PROCEDURE VIEW_ALL_EXAM()
BEGIN
    SELECT * FROM EXAM ORDER BY start DESC;
END //

#View an exam info with module ID
CREATE PROCEDURE VIEW_EXAM_WITH_ID(IN module_id INT)
BEGIN
    SELECT *
    FROM EXAM
    WHERE module = module_id;
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