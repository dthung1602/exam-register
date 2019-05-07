package vgu.group1.examregister.database;

import org.json.JSONArray;
import org.json.JSONObject;
import vgu.group1.examregister.Config;

import java.sql.*;

import static vgu.group1.examregister.database.Utils.convertAll;
import static vgu.group1.examregister.database.Utils.getPreparedStatement;

public class Exam {
    //A student registers for an exam
    public static void registerExam(int studentID, int examID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL REGISTER_EXAM(?,?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, examID);
        statement.setFloat(3, Config.MINIMUM_ATTENDANCE_PERCENT);
        statement.executeQuery();
    }

    //Unregister a student for an exam
    public static void unregisterExam(int studentID, int examID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UNREGISTER_EXAM(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, examID);
        statement.executeQuery();
    }

    //View exam participant list
    public static JSONArray listParticipants(int examID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL VIEW_PARTICIPANTS(?)");
        statement.setInt(1, examID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs, new String[]{"id", "code", "fname", "lname"});
    }

    //A student views his/her registered exam(s) in a given semester
    public static JSONArray viewRegisteredExam(int studentID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL STUDENT_VIEW_EXAM(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    // -------------------------- ASSISTANT/EXAM -------------------

    public static void createExam(int moduleID, Date examDate, Date examDeadline, Time examStart, Time examEnd) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_EXAM(?, ?, ?, ?, ?)");
        statement.setInt(1, moduleID);
        statement.setDate(2, examDate);
        statement.setDate(3, examDeadline);
        statement.setTime(4, examStart);
        statement.setTime(5, examEnd);
        statement.executeQuery();
    }

    public static void cancelExam(int examID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CANCEL_EXAM(?)");
        statement.setInt(1, examID);
        statement.executeQuery();
    }

    public static void editExam(int examID, Date examDate, Date examDeadline, Time examStart, Time examEnd) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL EDIT_EXAM (?, ?, ?, ?, ?)");
        statement.setInt(1, examID);
        statement.setDate(2, examDate);
        statement.setDate(3, examDeadline);
        statement.setTime(4, examStart);
        statement.setTime(5, examEnd);
        statement.executeQuery();
    }

    // ------------------ EXAM ------------//
    // View all exams list
    public static JSONArray listAllExam() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL VIEW_EXAM()");
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    public static JSONObject readExam(int examId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL READ_EXAM (?)");
        statement.setInt(1, examId);
        return Utils.convertOne(statement.executeQuery());
    }

    public static JSONArray listCanRegisterExams(int studentID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_EXAMS_AVAILABLE_FOR_STUDENT (?, ?)");
        statement.setInt(1, studentID);
        statement.setFloat(2, Config.MINIMUM_ATTENDANCE_PERCENT);
        return Utils.convertAll(
                statement.executeQuery(),
                new String[]{"id", "date", "deadline", "start", "end", "module", "registered"}
        );
    }

    public static JSONArray listLectureExam(int lecturerID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_LECTURER_EXAM (?)");
        statement.setInt(1, lecturerID);
        return Utils.convertAll(statement.executeQuery());
    }
}
