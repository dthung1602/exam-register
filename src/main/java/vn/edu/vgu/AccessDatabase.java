package vn.edu.vgu;

import org.json.JSONArray;

import java.sql.*;

import static vn.edu.vgu.Utils.convertAll;

class AccessDatabase {
    private static PreparedStatement getPreparedStatement(String statement) throws SQLException {
        String host = "jdbc:mysql://localhost:3306/examreg";
        String username = "examreguser";
        String password = "whatever123";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot find MysSQL driver class");
            e.printStackTrace();
            System.exit(1);
        }

        Connection connection = DriverManager.getConnection(host, username, password);
        return connection.prepareStatement(statement);
    }

    static void createSemester(Date startDate, Date endDate) throws SQLException {
        PreparedStatement statement = getPreparedStatement("CALL CREATE_SEMESTER(?, ?)");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        statement.executeQuery();
    }

    static JSONArray listModules(int moduleId) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = getPreparedStatement("CALL LIST_MODULE(?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }
}
