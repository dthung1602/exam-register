package vgu.group1.examregister.database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static vgu.group1.examregister.database.Utils.*;

public class Account {
    //List all the accounts (username + password)
    public static JSONArray listAccount() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT()");
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //List account by a given ID
    public static JSONArray listAccountId(int accountID) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT_ID(?)");
        statement.setInt(1, accountID);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    //List account by a given username
    public static JSONObject listAccountUsername(String accountUsername) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_ACCOUNT_USERNAME(?)");
        statement.setString(1, accountUsername);
        ResultSet rs = statement.executeQuery();
        return convertOne(rs);
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
}
