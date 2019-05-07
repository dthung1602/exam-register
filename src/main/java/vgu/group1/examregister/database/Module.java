package vgu.group1.examregister.database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static vgu.group1.examregister.database.Utils.*;

public class Module {
    //Create Module
    public static void createModule(String moduleName, String moduleCode, int semesterId, int lecturerId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_MODULE(?, ?, ?, ?)");
        statement.setString(1, moduleName);
        statement.setString(2, moduleCode);
        statement.setInt(3, semesterId);
        statement.setInt(4, lecturerId);
        statement.executeQuery();
    }

    //Cancel/Delete a Module
    public static void cancelModule(int moduleID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CANCEL_MODULE(?)");
        statement.setInt(1, moduleID);
        statement.executeQuery();
    }

    //Update Module
    public static void updateModule(String moduleName, String moduleCode, int lecturerID, int moduleID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_MODULE(?, ?, ?, ?)");
        statement.setString(1, moduleName);
        statement.setString(2, moduleCode);
        statement.setInt(3, lecturerID);
        statement.setInt(4, moduleID);
        statement.executeQuery();
    }

    //List Module
    public static JSONObject viewModule(int moduleId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL VIEW_A_MODULE (?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return convertOne(rs);
    }

    //List all of Modules
    public static JSONArray listAllModules() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ALL_MODULES()");
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    //List all modules that has overlap sessions
    public static JSONArray listModuleOverlapSessions() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_OVERLAP_SESSION()");
        ResultSet rs = statement.executeQuery();
        return convertAll(rs, new String[]{"date", "code1", "name1", "start1", "end1", "code2", "name2", "start2", "end2"});
    }

    //List all the modules that a given student has enrolled in
    public static JSONArray listModuleStudentEnroll(int studentID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE_STU_ENROLL(?)");
        statement.setInt(1, studentID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    public static JSONArray listSessionAttended(int moduleId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_SESSION_ATTENDED(?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs, new String[]{"student_code", "student_id", "student_name", "session_count"});
    }

    public static void addParticipant(String studentCode, int moduleID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ENROLL_MODULE(?,?)");
        statement.setString(1, studentCode);
        statement.setInt(2, moduleID);
        statement.executeQuery();
    }

    public static void deleteParticipant(int studentID, int moduleID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL DELETE_STUDENT_IN_MODULE(?,?)");
        statement.setInt(1, studentID);
        statement.setInt(2, moduleID);
        statement.executeQuery();
    }
}
