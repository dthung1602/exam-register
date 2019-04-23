package vn.edu.vgu;

import org.json.JSONArray;

import java.sql.*;

import static vn.edu.vgu.Utils.convertAll;

public class AccessDatabase {
    public static void main(String[] args) throws Exception {
        AccessDatabase ad = new AccessDatabase();
        ad.createSemester(new Date(1589), new Date(234234));
        System.out.println(ad.listModules(1));
    }

    private static PreparedStatement getPreparedStatement(String statement) throws ClassNotFoundException, SQLException {
        String host = "jdbc:mysql://localhost:3306/examreg";
        String username = "examreguser";
        String password = "whatever123";

        //Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(host, username, password);
        return connection.prepareStatement(statement);
    }

    //SEMESTER/MODULE
    //Create Semester
    void createSemester(Date startDate, Date endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_SEMESTER(?, ?)");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        statement.executeQuery();
    }

    //Update Semester
    void updateSemester(int semesterId, Date startDate, Date endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_SEMESTER(?, ?, ?)");
        statement.setInt(1, semesterId);
        statement.setDate(2, startDate);
        statement.setDate(3, endDate);
        statement.executeQuery();
    }

    //Create Module
    void createModule(String moduleName, String moduleCode, int semesterId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_MODULE(?, ?, ?)");
        statement.setString(1, moduleName);
        statement.setString(2, moduleCode);
        statement.setInt(3, semesterId);
        statement.executeQuery();
    }

    //Assign Lecturer
    void assignLecturer(int moduleId, int lecturerID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL ASSIGN_LECTURER(?,?)");
        statement.setInt(1, moduleId);
        statement.setInt(2, lecturerID);
        statement.executeQuery();
    }

    //Update Module
    void updateModule(String moduleName, String moduleCode, int semesterID, int moduleID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_MODULE(?, ?, ?, ?)");
        statement.setString(1, moduleName);
        statement.setString(2, moduleCode);
        statement.setInt(3, semesterID);
        statement.setInt(4, moduleID);
        statement.executeQuery();
    }

    //List Module
    JSONArray listModules(int moduleId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE(?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //List module with given code is taught in given semester
    JSONArray listModuleInSemester(String moduleCode, int semesterId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE_IN_SEMESTER(?, ?)");
        statement.setString(1, moduleCode);
        statement.setInt(2, semesterId);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //List all modules that has overlap sessions
    JSONArray listModuleOverlapSessions() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE_OVERLAP_SESSIONS");
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //List all the modules that a given student has enrolled in
    JSONArray listModuleStuEnroll(int studentID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE_STU_ENROLL(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //EXAM REGISTER
    //A student registers for an exam
    void RegisterExam(int studentID, int examID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL REGISTER_EXAM(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, examID);
        statement.executeQuery();
    }

    //Unregister a student for an exam
    void UnregisterExam(int studentID, int examID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL UNREGISTERED_EXAM(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, examID);
        statement.executeQuery();
    }

    //View exam participant list
    JSONArray ListParticipants(int examID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_PARTICIPANTS(?)");
        statement.setInt(1, examID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //A student view his/her registered exam(s) in a given semester
    JSONArray ViewRegisteredExam(int studentID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL STUDENT_VIEW_REGISTERED_EXAM(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //ACCOUNT
    //List all the accounts (username + password)
    JSONArray listAccount() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT()");
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //List account by a given ID
    JSONArray listAccountId(int accountID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT_ID(?)");
        statement.setInt(1, accountID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //List account by a given username
    JSONArray listAccountUsername(String accountUsername) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT_USERNAME(?)");
        statement.setString(1, accountUsername);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //SESSION
    //create sessions for given module
    void createSession(int moduleID, Date sessionDate, Time sessionStart, Time sessionEnd) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_SESSION(?,?,?,?)");
        statement.setInt(1, moduleID);
        statement.setDate(2, sessionDate);
        statement.setTime(3, sessionStart);
        statement.setTime(4, sessionEnd);
        statement.executeQuery();
    }

    // change session time
    void changeSessionTime(Time sessionStart, Time sessionEnd, int sessionId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL CHANGE_SESSION_TIME(?,?,?)");
        statement.setTime(1, sessionStart);
        statement.setTime(2, sessionEnd);
        statement.setInt(3, sessionId);
        statement.executeQuery();
    }

    //cancel a session
    void cancelSession(int sessionId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL CANCEL_SESSION(?)");
        statement.setInt(1, sessionId);
        statement.executeQuery();
    }

    //Check for the number of sessions the student "vth" attends in the given module
    void checkSessionStudent(String studentLname, int moduleID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL CHECK_SESSION_STUDENT(?,?)");
        statement.setString(1, studentLname);
        statement.setInt(2, moduleID);
        statement.executeQuery();
    }

    //Check for the number of sessions the given student attends in all modules
    void checkSessionInModules(int studentID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL CHECK_SESSION_IN_MODULES(?)");
        statement.setInt(1, studentID);
        statement.executeQuery();
    }

    //a student sign a session
    void signSession(int studentID, int sessionID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL SIGN_SESSION(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, sessionID);
        statement.executeQuery();
    }

    //show session of a given date
    JSONArray showSessionOn(Date date) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL SHOW_SESSION_ON(?)");
        statement.setDate(1, date);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }
}

