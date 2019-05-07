package vgu.group1.examregister.database;

import org.json.JSONArray;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static vgu.group1.examregister.database.Utils.convertAll;
import static vgu.group1.examregister.database.Utils.getPreparedStatement;

public class Enroll {
    public static JSONArray viewStudentsOfModule(int moduleId) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL VIEW_STUDENTS_OF_MODULE(?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }
}
