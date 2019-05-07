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
DROP PROCEDURE IF EXISTS LIST_CURRENT_SESSIONS_OF_STUDENT;
DROP PROCEDURE IF EXISTS LIST_EXAMS_AVAILABLE_FOR_STUDENT;
DROP PROCEDURE IF EXISTS LIST_LECTURER_EXAM;

DROP FUNCTION IF EXISTS COUNT_ATTENDANCE;
DROP FUNCTION IF EXISTS PERCENT_ATTENDANCE;

DROP PROCEDURE IF EXISTS CHECK_DATE;
DROP PROCEDURE IF EXISTS CHECK_TIME;
DROP PROCEDURE IF EXISTS CHECK_SESSION_OVERLAP;
DROP PROCEDURE IF EXISTS CHECK_DATE_IN_SEMESTER;

DROP TRIGGER IF EXISTS CHECK_INSERT_SEMESTER;
DROP TRIGGER IF EXISTS CHECK_UPDATE_SEMESTER;
DROP TRIGGER IF EXISTS CHECK_INSERT_EXAM;
DROP TRIGGER IF EXISTS CHECK_UPDATE_EXAM;
DROP TRIGGER IF EXISTS CHECK_INSERT_SESSION;
DROP TRIGGER IF EXISTS CHECK_UPDATE_SESSION;

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

# view semester
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
                               IN my_semester INT,
                               IN my_lecturerId INT)
BEGIN
    INSERT INTO MODULE(name, code, semester)
    VALUES (my_name, my_code, my_semester);
    INSERT INTO TEACH(module, lecturer)
    VALUES (LAST_INSERT_ID(), my_lecturerId);
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
    SELECT COUNT(*) INTO @total_session
    FROM SESSION
    WHERE SESSION.module = module_id;

    SELECT M.id,
           M.code,
           M.name,
           M.semester,
           S.start,
           S.end,
           CONCAT(A.fname, ' ', A.lname) AS 'lecturer',
           CONCAT(A.id, '')              AS 'lecturer_id',
           @total_session                AS 'total_session'
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
    SELECT CONCAT('', S.id)              AS semester,
           S.start,
           S.end,
           M.name,
           M.code,
           M.id,
           CONCAT(A.fname, ' ', A.lname) AS lecturer
    FROM MODULE M,
         SEMESTER S,
         TEACH T,
         LECTURER L,
         ACCOUNT A
    WHERE S.id = M.semester
      AND M.id = T.module
      AND L.account = T.lecturer
      AND L.account = A.id;
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

CREATE FUNCTION COUNT_ATTENDANCE(student_id INT,
                                 module_id INT)
    RETURNS INT
BEGIN
    SET @count = 0;
    SELECT COUNT(*) INTO @count
    FROM SESSION SE
             JOIN SIGN SI ON SE.id = SI.session
    WHERE SI.student = student_id
      AND SE.module = module_id;
    RETURN @count;
END //

CREATE FUNCTION PERCENT_ATTENDANCE(student_id INT,
                                   module_id INT)
    RETURNS FLOAT(3, 2)
BEGIN
    SET @count = COUNT_ATTENDANCE(student_id, module_id);

    set @total = 0;
    SELECT COUNT(*) INTO @total FROM SESSION WHERE module = module_id;

    RETURN CAST(@count AS DECIMAL) /
           CAST(@total AS DECIMAL);
END //

CREATE PROCEDURE LIST_EXAMS_AVAILABLE_FOR_STUDENT(IN student_id INT,
                                                  IN percent FLOAT(3, 2))
BEGIN
    SELECT EX.id,
           EX.date,
           EX.deadline,
           EX.start,
           EX.end,
           M.name,
           EXISTS(SELECT *
                  FROM EXAM_REG ER
                  WHERE ER.exam = EX.id
                    AND ER.student = student_id) AS 'registered'
    FROM EXAM EX
             JOIN MODULE M ON EX.module = M.id
             JOIN ENROLL EN ON M.id = EN.module
    WHERE EN.student = student_id
      AND CURRENT_DATE() <= EX.deadline
      AND PERCENT_ATTENDANCE(student_id, M.id) >= percent;
END //

# a student registers for an exam
CREATE PROCEDURE REGISTER_EXAM(IN student_id INT,
                               IN exam_id INT,
                               IN percent FLOAT(3, 2))
BEGIN
    IF exam_id IN (SELECT EX.id
                   FROM EXAM EX
                            JOIN MODULE M ON EX.module = M.id
                            JOIN ENROLL EN ON M.id = EN.module
                   WHERE EN.student = student_id
                     AND CURRENT_DATE() <= EX.deadline
                     AND PERCENT_ATTENDANCE(student_id, M.id) >= percent)
    THEN
        INSERT INTO EXAM_REG(student, exam) VALUE
            (student_id, exam_id);
    ELSE
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Student cannot register for the exam';
    END IF;
END //

# unregister a student for an exam
CREATE PROCEDURE UNREGISTER_EXAM(IN my_student INT,
                                 IN my_exam INT)
BEGIN
    SELECT EX.deadline INTO @deadline
    FROM EXAM EX
    WHERE EX.id = my_exam;

    IF CURRENT_DATE() <= @deadline THEN
        DELETE
        FROM EXAM_REG
        WHERE student = my_student
          AND exam = my_exam;
    ELSE
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Student cannot unregister the exam';
    END IF;
END //

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
    SELECT S.account, S.code, A.fname, A.lname
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
    SELECT EX.id, M.name, EX.date, EX.deadline, EX.start, EX.end
    FROM EXAM EX
             JOIN MODULE M on EX.module = M.id;
END //

CREATE PROCEDURE LIST_CURRENT_SESSIONS_OF_STUDENT(IN student_id INT)
BEGIN
    SELECT SE.id, SE.start, SE.end, M.name, CONCAT(A.fname, A.lname) AS lecturer
    FROM SESSION SE
             JOIN MODULE M ON SE.module = M.id
             JOIN ENROLL E ON M.id = E.module
             JOIN TEACH T on M.id = T.module
             JOIN LECTURER L on T.lecturer = L.account
             JOIN ACCOUNT A on L.account = A.id
    WHERE E.student = student_id
      AND CURRENT_DATE() = SE.date
      AND CURRENT_TIME() BETWEEN SE.start AND SE.end
      AND NOT EXISTS(
            SELECT *
            FROM SIGN SI
            WHERE SI.student = student_id
              AND SI.session = SE.id);
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
    SELECT STU.code,
           STU.account,
           CONCAT(A.fname, ' ', A.lname),
           COUNT(SI.session)
    FROM SIGN SI
             JOIN SESSION SE ON SE.id = SI.session
             JOIN STUDENT STU ON SI.student = STU.account
             JOIN ACCOUNT A ON STU.account = A.id
    GROUP BY STU.code, STU.account, A.fname, A.lname, SE.module
    HAVING SE.module = my_module;
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
                           IN exam_date DATE,
                           IN exam_deadline DATE,
                           IN exam_start TIME,
                           IN exam_end TIME)

BEGIN
    UPDATE EXAM
    SET date     = exam_date,
        deadline = exam_deadline,
        start    = exam_start,
        end      = exam_end
    WHERE id = exam_id;
END //

-- ------------------------EXAM-------------------

CREATE PROCEDURE READ_EXAM(IN exam_id INT)
BEGIN
    SELECT E.*, M.name
    FROM EXAM E
             JOIN MODULE M ON E.module = M.id
    WHERE E.id = exam_id;
END //

#View all exams list
CREATE PROCEDURE VIEW_ALL_EXAM()
BEGIN
    SELECT E.id, E.date, E.start, E.end, M.name
    FROM EXAM E
             JOIN MODULE M on E.module = M.id
    ORDER BY start;
END //

#View an exam info with module ID
CREATE PROCEDURE VIEW_EXAM_WITH_ID(IN module_id INT)
BEGIN
    SELECT *
    FROM EXAM
    WHERE module = module_id;
END //

CREATE PROCEDURE LIST_LECTURER_EXAM(IN lecturer_id INT)
BEGIN
    SELECT E.*, M.name
    FROM EXAM E
             JOIN MODULE M ON E.module = M.id
             JOIN TEACH T ON M.id = T.module
    WHERE T.lecturer = lecturer_id;
END //

-- ------------------------UTILS-----------------------------

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

-- ------------------------ CHECK PROCEDURES FOR TRIGGERS -----------------------------

CREATE PROCEDURE CHECK_DATE(IN start_date DATE,
                            IN end_date DATE)
BEGIN
    IF DATE(start_date) > DATE(end_date)
    THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Invalid starting and ending date received.';
    END IF;
END //

CREATE PROCEDURE CHECK_TIME(IN start_time TIME,
                            IN end_time TIME)
BEGIN
    IF TIME(start_time) > TIME(end_time)
    THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Invalid starting and ending time received.';
    END IF;
END //

CREATE PROCEDURE CHECK_SESSION_OVERLAP(IN date DATE,
                                       IN start TIME,
                                       IN end TIME,
                                       IN module_id INT,
                                       IN self_id INT)
BEGIN
    IF EXISTS(SELECT *
              FROM SESSION SE
              WHERE date = SE.date
                AND SE.module = module_id
                AND SE.id != self_id
                AND (
                      start BETWEEN SE.start AND SE.end
                      OR end BETWEEN SE.start AND SE.end
                      OR (start < SE.start AND SE.end < end)
                  ))
    THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Overlapped sessions.';
    END IF;
END //

CREATE PROCEDURE CHECK_DATE_IN_SEMESTER(IN module_id INT,
                                        IN d DATE)
BEGIN
    SELECT start,
           end
           INTO @sem_start, @sem_end
    FROM SEMESTER SEM
             JOIN MODULE M on SEM.id = M.semester
    WHERE M.id = module_id;

    IF d NOT BETWEEN @sem_start AND @sem_end
    THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Session out of semester bound.';
    END IF;
END //

-- ------------------------  TRIGGERS -----------------------------

CREATE TRIGGER CHECK_INSERT_SEMESTER
    BEFORE INSERT
    ON SEMESTER
    FOR EACH ROW
BEGIN
    CALL CHECK_DATE(NEW.start, NEW.end);
END //

CREATE TRIGGER CHECK_UPDATE_SEMESTER
    BEFORE UPDATE
    ON SEMESTER
    FOR EACH ROW
BEGIN
    CALL CHECK_DATE(NEW.start, NEW.end);
END //

CREATE TRIGGER CHECK_INSERT_EXAM
    BEFORE INSERT
    ON EXAM
    FOR EACH ROW
BEGIN
    CALL CHECK_DATE(NEW.deadline, NEW.date);
    CALL CHECK_TIME(NEW.start, NEW.end);
    CALL CHECK_DATE_IN_SEMESTER(NEW.module, NEW.date);
END //

CREATE TRIGGER CHECK_UPDATE_EXAM
    BEFORE INSERT
    ON EXAM
    FOR EACH ROW
BEGIN
    CALL CHECK_DATE(NEW.deadline, NEW.date);
    CALL CHECK_TIME(NEW.start, NEW.end);
    CALL CHECK_DATE_IN_SEMESTER(NEW.module, NEW.date);
END //

CREATE TRIGGER CHECK_INSERT_SESSION
    BEFORE INSERT
    ON SESSION
    FOR EACH ROW
BEGIN
    CALL CHECK_TIME(NEW.start, NEW.end);
    CALL CHECK_DATE_IN_SEMESTER(NEW.module, NEW.date);
    CALL CHECK_SESSION_OVERLAP(NEW.date, NEW.start, NEW.end, NEW.module, -1);
END //

CREATE TRIGGER CHECK_UPDATE_SESSION
    BEFORE UPDATE
    ON SESSION
    FOR EACH ROW
BEGIN
    CALL CHECK_TIME(NEW.start, NEW.end);
    CALL CHECK_DATE_IN_SEMESTER(NEW.module, NEW.date);
    CALL CHECK_SESSION_OVERLAP(NEW.date, NEW.start, NEW.end, NEW.module, NEW.id);
END //

DELIMITER ;