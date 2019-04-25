package vgu.group1.examregister.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static vgu.group1.examregister.database.Utils.getPreparedStatement;

public class Semester {
    //Create Semester
    public static void createSemester(Date startDate, Date endDate) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_SEMESTER(?, ?)");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        statement.executeQuery();
    }

    //Update Semester
    public static void updateSemester(int semesterId, Date startDate, Date endDate) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_SEMESTER(?, ?, ?)");
        statement.setInt(1, semesterId);
        statement.setDate(2, startDate);
        statement.setDate(3, endDate);
        statement.executeQuery();
    }
}
