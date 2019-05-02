package vgu.group1.examregister.database;

import org.json.JSONArray;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static vgu.group1.examregister.database.Utils.convertAll;
import static vgu.group1.examregister.database.Utils.getPreparedStatement;

public class Module {
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
        return convertAll(rs);
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

    public  static JSONArray listSessionAttended(int moduleId) throws SQLException{
        PreparedStatement statement =getPreparedStatement("CALL LIST_SESSION_ATTENDED(?)");
        statement.setInt(1, moduleId);
        ResultSet rs= statement.executeQuery();
        return  convertAll(rs);
    }
}
