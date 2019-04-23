package vn.edu.vgu;

import org.json.JSONArray;

import java.sql.*;

import static vn.edu.vgu.Utils.convertAll;

public class AccessDatabase {
    public static void main(String[] args) throws Exception {
        AccessDatabase ad = new AccessDatabase();
        ad.createSemester(new Date(1589), new Date(234234));
        System.out.println(ad.listModules(1));
    }

    private static PreparedStatement getPreparedStatement(String statement) throws ClassNotFoundException, SQLException {
        String host = "jdbc:mysql://localhost:3306/examreg";
        String username = "examreguser";
        String password = "whatever123";

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(host, username, password);
        return connection.prepareStatement(statement);
    }

    void createSemester(Date startDate, Date endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_SEMESTER(?, ?)");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        statement.executeQuery();
    }

    JSONArray listModules(int moduleId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE(?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }
}
