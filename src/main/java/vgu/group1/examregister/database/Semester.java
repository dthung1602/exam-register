package vgu.group1.examregister.database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static vgu.group1.examregister.database.Utils.convertOne;
import static vgu.group1.examregister.database.Utils.getPreparedStatement;

public class Semester {

    public static JSONObject createSemester(Date startDate, Date endDate) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_SEMESTER(?, ?)");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        return convertOne(statement.executeQuery());
    }

    public static JSONObject readSemester(int semesterId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL READ_SEMESTER(?)");
        statement.setInt(1, semesterId);
        return convertOne(statement.executeQuery());
    }

    public static void updateSemester(int semesterId, Date startDate, Date endDate) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_SEMESTER(?, ?, ?)");
        statement.setInt(1, semesterId);
        statement.setDate(2, startDate);
        statement.setDate(3, endDate);
        statement.executeQuery();
    }

    //List all semesters
    public static JSONArray listAllSemester() throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL VIEW_ALL_SEMESTER()");
        ResultSet rs = statement.executeQuery();
        return Utils.convertAll(rs);
    }

    // Delete a Semester
    public static void deleteSemester(int semester_id) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL DELETE_SEMESTER(?)");
        statement.setInt(1, semester_id);
        statement.executeQuery();
    }
}
