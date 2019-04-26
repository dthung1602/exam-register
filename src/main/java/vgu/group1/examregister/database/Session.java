package vgu.group1.examregister.database;

import org.json.JSONArray;

import java.sql.*;

import static vgu.group1.examregister.database.Utils.convertAll;
import static vgu.group1.examregister.database.Utils.getPreparedStatement;

public class Session {
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
        return convertAll(statement.executeQuery());
    }

    //Check for the number of sessions the given student attends in all modules
    public static JSONArray listSessionInModules(int studentID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_SESSION_IN_MODULES(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    // a student sign a session
    public static void signSession(int studentID, int sessionID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL SIGN_SESSION(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, sessionID);
        statement.executeQuery();
    }

    // show session of a given date
    public static JSONArray showSessionOn(Date date) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL SHOW_SESSION_ON(?)");
        statement.setDate(1, date);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs, new String[]{"start", "end", "name", "lname"});
    }
}
