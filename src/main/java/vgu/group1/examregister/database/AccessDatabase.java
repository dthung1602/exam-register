package vgu.group1.examregister.database;

import org.json.JSONArray;
import vgu.group1.examregister.Utils;

import java.sql.*;

public class AccessDatabase {
    private static PreparedStatement getPreparedStatement(String statement) throws SQLException {
        String host = "jdbc:mysql://localhost:3306/examreg";
        String username = "examreguser";
        String password = "whatever123";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot find MysSQL driver class");
            e.printStackTrace();
            System.exit(1);
        }

        Connection connection = DriverManager.getConnection(host, username, password);
        return connection.prepareStatement(statement);
    }

    // -----------------------  SEMESTER/MODULE -----------------------------------
    //Create Semester
    public static void createSemester(Date startDate, Date endDate) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_SEMESTER(?, ?)");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        statement.executeQuery();
    }

    //Update Semester
    public static void updateSemester(int semesterId, Date startDate, Date endDate) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_SEMESTER(?, ?, ?)");
        statement.setInt(1, semesterId);
        statement.setDate(2, startDate);
        statement.setDate(3, endDate);
        statement.executeQuery();
    }

    //Create Module
    public static void createModule(String moduleName, String moduleCode, int semesterId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_MODULE(?, ?, ?)");
        statement.setString(1, moduleName);
        statement.setString(2, moduleCode);
        statement.setInt(3, semesterId);
        statement.executeQuery();
    }

    //Assign Lecturer
    public static void assignLecturer(int moduleId, int lecturerID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ASSIGN_LECTURER(?,?)");
        statement.setInt(1, moduleId);
        statement.setInt(2, lecturerID);
        statement.executeQuery();
    }

    //Update Module
    public static void updateModule(String moduleName, String moduleCode, int semesterID, int moduleID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_MODULE(?, ?, ?, ?)");
        statement.setString(1, moduleName);
        statement.setString(2, moduleCode);
        statement.setInt(3, semesterID);
        statement.setInt(4, moduleID);
        statement.executeQuery();
    }

    //List Module
    public static JSONArray listModules(int moduleId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE(?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    //List all modules that has overlap sessions
    public static JSONArray listModuleOverlapSessions() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_OVERLAP_SESSION()");
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs, new String[]{"date", "code1", "name1", "start1", "end1", "code2", "name2", "start2", "end2"});
    }

    //List all the modules that a given student has enrolled in
    public static JSONArray listModuleStudentEnroll(int studentID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE_STU_ENROLL(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    //----------------------- EXAM REGISTER -----------------------
    //A student registers for an exam
    public static void registerExam(int studentID, int examID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL REGISTER_EXAM(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, examID);
        statement.executeQuery();
    }

    //Unregister a student for an exam
    public static void unregisterExam(int studentID, int examID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UNREGISTERED_EXAM(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, examID);
        statement.executeQuery();
    }

    //View exam participant list
    public static JSONArray listParticipants(int examID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL VIEW_PARTICIPANTS(?)");
        statement.setInt(1, examID);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs, new String[]{"code", "fname", "lname"});
    }

    //A student view his/her registered exam(s) in a given semester
    public static JSONArray viewRegisteredExam(int studentID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL STUDENT_VIEW_EXAM(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    // ----------------------- SESSION -----------------------
    //create sessions for given module
    public static void createSession(int moduleID, Date sessionDate, Time sessionStart, Time sessionEnd) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_SESSION(?,?,?,?)");
        statement.setInt(1, moduleID);
        statement.setDate(2, sessionDate);
        statement.setTime(3, sessionStart);
        statement.setTime(4, sessionEnd);
        statement.executeQuery();
    }

    // change session time
    public static void changeSessionTime(Time sessionStart, Time sessionEnd, int sessionId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CHANGE_SESSION_TIME(?,?,?)");
        statement.setTime(1, sessionStart);
        statement.setTime(2, sessionEnd);
        statement.setInt(3, sessionId);
        statement.executeQuery();
    }

    //cancel a session
    public static void cancelSession(int sessionId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CANCEL_SESSION(?)");
        statement.setInt(1, sessionId);
        statement.executeQuery();
    }

    //Check for the number of sessions the student "vth" attends in the given module
    public static JSONArray listSessionStudent(String studentLname, int moduleID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_SESSION_STUDENT(?,?)");
        statement.setString(1, studentLname);
        statement.setInt(2, moduleID);
        return Utils.convertAll(statement.executeQuery());
    }

    //Check for the number of sessions the given student attends in all modules
    public static JSONArray listSessionInModules(int studentID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_SESSION_IN_MODULES(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    // a student sign a session
    public static void signSession(int studentID, int sessionID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL SIGN_SESSION(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, sessionID);
        statement.executeQuery();
    }

    //show session of a given date 
    public static JSONArray showSessionOn(Date date) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL SHOW_SESSION_ON(?)");
        statement.setDate(1, date);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs, new String[]{"start", "end", "name", "lname"});
    }

    // ----------------------- ACCOUNT -----------------------
    //List all the accounts (username + password)
    public static JSONArray listAccount() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT()");
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    //List account by a given ID
    public static JSONArray listAccountId(int accountID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT_ID(?)");
        statement.setInt(1, accountID);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    //List account by a given username 
    public static JSONArray listAccountUsername(String accountUsername) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT_USERNAME(?)");
        statement.setString(1, accountUsername);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    public static void addNewStudent(String username, String password, String lname, String fname, String code) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_STUDENT(?, ?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, lname);
        statement.setString(4, fname);
        statement.setString(5, code);
        statement.executeQuery();
    }

    public static void addNewLecturer(String username, String password, String lname, String fname) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_LECTURER(?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, lname);
        statement.setString(4, fname);
        statement.executeQuery();
    }

    public static void addNewAssistant(String username, String password, String lname, String fname) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_ASSISTANT(?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, lname);
        statement.setString(4, fname);
        statement.executeQuery();
    }

    public static void updateLastNameFirstName(int id, String lname, String fname) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_LNAME_FNAME(?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, lname);
        statement.setString(3, fname);
        statement.executeQuery();
    }

    public static void changePassword(int id, String password) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CHANGE_PASSWORD(?, ?)");
        statement.setInt(1, id);
        statement.setString(2, password);
        statement.executeQuery();
    }

    // ---------------------- ENROLL --------------------------
    public static void enrollModule(int studentId, int moduleId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ENROLL_MODULE(?, ?)");
        statement.setInt(1, studentId);
        statement.setInt(2, moduleId);
    }

    public static JSONArray viewStudentsOfModule(int moduleId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL VIEW_STUDENTS_OF_MODULE(?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }
}

