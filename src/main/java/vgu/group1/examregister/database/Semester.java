package vgu.group1.examregister.database;

import org.json.JSONObject;

import java.sql.Date;
import java.sql.PreparedStatement;
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
}
