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

    //ACCOUNT
    void addNewStudent(String username, String password, String lname, String fname, String code)throws SQLException, ClassNotFoundException{
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_STUDENT(?, ?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, lname);
        statement.setString(4, fname);
        statement.setString(5, code);
        statement.executeQuery();
    }

    void addNewLecturer(String username, String password, String lname, String fname) throws SQLException, ClassNotFoundException{
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_LECTURER(?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, lname);
        statement.setString(4, fname);
        statement.executeQuery();
    }

    void addNewAssistant(String username, String password, String lname, String fname) throws SQLException, ClassNotFoundException{
        PreparedStatement statement = getPreparedStatement("CALL ADD_NEW_ASSISTANT(?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, lname);
        statement.setString(4, fname);
        statement.executeQuery();
    }

    void updateLastNameFirstName(int id, String lname, String fname ) throws SQLException, ClassNotFoundException{
        PreparedStatement statement = getPreparedStatement("CALL UPDATE_LNAME_FNAME(?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, lname);
        statement.setString(3, fname);
        statement.executeQuery();
    }

    void changePassword(int id, String password)throws SQLException, ClassNotFoundException{
        PreparedStatement statement = getPreparedStatement("CALL CHANGE_PASSWORD(?, ?)");
        statement.setInt(1,id);
        statement.setString(2, password);
        statement.executeQuery();
    }

    //ENROLL
    void enrollModule(int studentId, int moduleId) throws SQLException, ClassNotFoundException{
        PreparedStatement statement = getPreparedStatement("CALL ENROLL_MODULE(?, ?)");
        statement.setInt(1, studentId);
        statement.setInt(2, moduleId);
    }
    JSONArray viewStudentsOfModule(int moduleId)throws SQLException, ClassNotFoundException{
        PreparedStatement statement = getPreparedStatement("CALL VIEW_STUDENTS_OF_MODULE(?)");
        statement.setInt(1, moduleId);
        ResultSet rs = statement.executeQuery();
        return convertAll(rs);
    }

    
}
