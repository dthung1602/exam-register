package vgu.group1.examregister.database;

import org.json.JSONArray;

import java.sql.*;

import static vgu.group1.examregister.database.Utils.convertAll;
import static vgu.group1.examregister.database.Utils.getPreparedStatement;

public class Exam {
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
        return convertAll(rs, new String[]{"code", "fname", "lname"});
    }

    //A student views his/her registered exam(s) in a given semester
    public static JSONArray viewRegisteredExam(int studentID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL STUDENT_VIEW_EXAM(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    public static void addExam(int moduleID, Date examDate, Date deadline, Time start, Time end) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ADD_EXAM(?,?,?,?,?)");
        statement.setInt(1, moduleID);
        statement.setDate(2, examDate);
        statement.setDate(3, deadline);
        statement.setTime(4, start);
        statement.setTime(5,end);
        statement.executeQuery();
    }
    public static void deleteExam(int examId) throws SQLException{
        PreparedStatement statement = getPreparedStatement("CALL DELETE_EXAM(?)");
        statement.setInt(1, examId);
        statement.executeQuery();
    }

    public static void updateExam(int examId, int moduleId, Date examDate, Date deadline, Time start, Time end) throws SQLException{
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_EXAM(?,?,?,?,?,?)");
        statement.setInt(1, examId);
        statement.setInt(2, moduleId);
        statement.setDate(3, examDate);
        statement.setDate(4,deadline);
        statement.setTime(5, start);
        statement.setTime(6, end);
        statement.executeQuery();
    }

}
