package vn.edu.vgu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

class Utils {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String host = "jdbc:mysql://localhost:3306/examreg";
        String username = "examreguser";
        String password = "whatever123";

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(host, username, password);
        Statement statement = connection.createStatement();

        String query = "SELECT id, username, password, fname, lname FROM ACCOUNT";
        ResultSet rs = statement.executeQuery(query);

        JSONArray jsonArray = convertAll(rs);
        System.out.println(jsonArray.toString());

        rs.beforeFirst();
        jsonArray = convertAll(rs, new String[]{"new_id", "new_user", "new_pass", "new_fname", "new_lname"});
        System.out.println(jsonArray.toString());
    }

    static JSONObject convertOne(ResultSet rs, String[] fieldNames) throws SQLException, JSONException {
        if (!rs.next())
            return null;

        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumns = rsmd.getColumnCount();
        JSONObject obj = new JSONObject();

        for (int i = 1; i < numColumns + 1; i++) {
            String fieldName = fieldNames[i - 1];
            int columnType = rsmd.getColumnType(i);

            if (columnType == java.sql.Types.ARRAY) {
                obj.put(fieldName, rs.getArray(i));
            } else if (columnType == java.sql.Types.BIGINT) {
                obj.put(fieldName, rs.getInt(i));
            } else if (columnType == java.sql.Types.BOOLEAN) {
                obj.put(fieldName, rs.getBoolean(i));
            } else if (columnType == java.sql.Types.BLOB) {
                obj.put(fieldName, rs.getBlob(i));
            } else if (columnType == java.sql.Types.DOUBLE) {
                obj.put(fieldName, rs.getDouble(i));
            } else if (columnType == java.sql.Types.FLOAT) {
                obj.put(fieldName, rs.getFloat(i));
            } else if (columnType == java.sql.Types.INTEGER) {
                obj.put(fieldName, rs.getInt(i));
            } else if (columnType == java.sql.Types.NVARCHAR) {
                obj.put(fieldName, rs.getNString(i));
            } else if (columnType == java.sql.Types.VARCHAR) {
                obj.put(fieldName, rs.getString(i));
            } else if (columnType == java.sql.Types.TINYINT) {
                obj.put(fieldName, rs.getInt(i));
            } else if (columnType == java.sql.Types.SMALLINT) {
                obj.put(fieldName, rs.getInt(i));
            } else if (columnType == java.sql.Types.DATE) {
                obj.put(fieldName, rs.getString(i));
            } else if (columnType == java.sql.Types.TIME) {
                obj.put(fieldName, rs.getString(i));
            }else if (columnType == java.sql.Types.TIMESTAMP) {
                obj.put(fieldName, rs.getTimestamp(i));
            } else {
                obj.put(fieldName, rs.getObject(i));
            }
        }
        return obj;
    }

    static JSONObject convertOne(ResultSet rs) throws SQLException, JSONException {
        return convertOne(rs, getFieldNames(rs));
    }

    static JSONArray convertAll(ResultSet rs, String[] fieldNames) throws SQLException, JSONException {
        JSONArray jsonArray = new JSONArray();
        if (rs.next()) {
            rs.previous();
            while (!rs.isLast())
                jsonArray.put(convertOne(rs, fieldNames));
        }
        return jsonArray;
    }

    static JSONArray convertAll(ResultSet rs) throws SQLException, JSONException {
        return convertAll(rs, getFieldNames(rs));
    }

    private static String[] getFieldNames(ResultSet rs) throws SQLException, JSONException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] fieldNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            fieldNames[i] = rsmd.getColumnName(i + 1);
        }
        return fieldNames;
    }
}

