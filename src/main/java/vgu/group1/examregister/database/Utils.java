package vgu.group1.examregister.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Utils {
    private static Connection connection;
    private static String HOST = "jdbc:mysql://localhost:3306/examreg";
    private static String USERNAME = "examreguser";
    private static String PASSWORD = "whatever123";

    static {
        // TODO read host, uname, pass from env
    }

    static PreparedStatement getPreparedStatement(String statement) throws SQLException {
        // first time call this function, init connection
        if (connection == null) {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        }

        return connection.prepareStatement(statement);
    }
}
