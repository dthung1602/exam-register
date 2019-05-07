package vgu.group1.examregister.database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static vgu.group1.examregister.database.Utils.*;

public class Account {
    //List account by a given ID
    public static JSONObject listAccountId(int accountID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT_ID(?)");
        statement.setInt(1, accountID);
        ResultSet rs = statement.executeQuery();
        return convertOne(rs);
    }

    //List account by a given username
    public static JSONObject listAccountUsername(String accountUsername) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT_USERNAME(?)");
        statement.setString(1, accountUsername);
        ResultSet rs = statement.executeQuery();
        return convertOne(rs);
    }

    public static JSONObject addNewStudent(String username, String password, String fname, String lname, String code) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_STUDENT(?, ?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, fname);
        statement.setString(4, lname);
        statement.setString(5, code);
        return convertOne(statement.executeQuery());
    }

    public static JSONObject addNewLecturer(String username, String password, String fname, String lname) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_LECTURER(?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, fname);
        statement.setString(4, lname);
        return convertOne(statement.executeQuery());
    }

    public static JSONObject addNewAssistant(String username, String password, String fname, String lname) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_ASSISTANT(?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, fname);
        statement.setString(4, lname);
        return convertOne(statement.executeQuery());
    }

    public static void deleteUser(int userId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL DELETE_USER(?)");
        statement.setInt(1, userId);
        statement.executeQuery();
    }

    public static void updateUser(int id, String username, String fname, String lname) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_USER(?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, username);
        statement.setString(3, fname);
        statement.setString(4, lname);
        statement.executeQuery();
    }

    public static void updateStudent(int id, String username, String fname, String lname, String code) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_STUDENT(?, ?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, username);
        statement.setString(3, fname);
        statement.setString(4, lname);
        statement.setString(5, code);
        statement.executeQuery();
    }

    public static void changePassword(int id, String password) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CHANGE_PASSWORD(?, ?)");
        statement.setInt(1, id);
        statement.setString(2, password);
        statement.executeQuery();
    }

    public static JSONArray listAllLecturers() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ALL_LECTURERS()");
        return convertAll(statement.executeQuery());
    }

    public static JSONArray listAllStudents() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ALL_STUDENTS()");
        return convertAll(statement.executeQuery());
    }

    public static JSONArray listAllAssistants() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ALL_ASSISTANTS()");
        return convertAll(statement.executeQuery());
    }
}
