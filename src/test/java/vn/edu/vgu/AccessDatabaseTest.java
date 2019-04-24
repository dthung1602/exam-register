package vn.edu.vgu;

import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static vn.edu.vgu.Utils.convertOne;

public class AccessDatabaseTest extends TestWithDatabase {

    @Before
    public void before() {
        loadFixtures(new String[]{"account.sql", "assistant.sql", "lecturer.sql", "student.sql", "semester.sql", "module.sql",  "session.sql", "enroll.sql", "sign.sql", "exam.sql", "teach.sql"});
    }

    @Test
    public void testListModule() throws SQLException, ClassNotFoundException {
        int[] semesterIds = new int[]{1, 2, 3};
        Integer[][] expectedModuleIds = new Integer[][]{
                new Integer[]{1, 2, 3},
                new Integer[]{4, 5},
                new Integer[]{},
        };
        for (int i = 0; i < semesterIds.length; i++) {
            JSONArray modules = AccessDatabase.listModules(semesterIds[i]);
            assertEquals(expectedModuleIds[i].length, modules.length());

            HashSet<Integer> expectedIdSet = new HashSet<>(Arrays.asList(expectedModuleIds[i]));
            for (int j = 0; j < modules.length(); j++) {
                int actualId = ((JSONObject) modules.get(i)).getInt("id");
                assertTrue(expectedIdSet.contains(actualId));
            }
        }
    }

    @Test
    public void testCreateSemester() throws SQLException {
        Date[] startDates = new Date[]{
                Date.valueOf("2011-09-01"),
                Date.valueOf("2012-6-15"),
        };
        Date[] endDates = new Date[]{
                Date.valueOf("2012-02-29"),
                Date.valueOf("2013-3-1"),
        };
        String[] expectedResults = new String[]{
                "{start: \"2011-09-01\", end: \"2012-02-29\", id: 4}",
                "{start: \"2012-06-15\", end: \"2013-03-01\", id: 5}",
        };
        Statement statement = connection.createStatement();
        for (int i = 0; i < startDates.length; i++) {
            AccessDatabase.createSemester(startDates[i], endDates[i]);
            ResultSet rs = statement.executeQuery("SELECT * FROM SEMESTER ORDER BY id DESC");
            JSONAssert.assertEquals(expectedResults[i], convertOne(rs).toString(), JSONCompareMode.LENIENT);
        }
    }

    @Test
    public void testModuleOverlapSessions() throws SQLException, ParseException {
        String[] sessionDates = new String[]{
                ("2018-04-01"),
                ("2018-04-01"),
        };

        String[] module1Code = new String[]{
                "RS",
                "RS",
        };

        String[] module2Code = new String[]{
                "SWEA",
                "SWEA",
        };

        JSONArray a = AccessDatabase.listModuleOverlapSessions();
        for (int i = 0; i < module1Code.length; i++) {
            String actualDate = ((JSONObject) a.get(i)).getString("date");
            assertEquals(sessionDates[i], actualDate);

            String actualModule1Code = ((JSONObject) a.get(i)).getString("code1");
            assertEquals(module1Code[i], actualModule1Code);

            String actualModule2Code = ((JSONObject) a.get(i)).getString("code2");
            assertEquals(module2Code[i], actualModule2Code);
        }
    }

    @Test
    public void testViewParticipants() throws SQLException {
        int[] studentCode = new int[]{11111, 33333, 55555};

        String[] fname = new String[]{
                "tuan hung",
                "ho tat dat",
                "chi minh"
        };

        String[] lname = new String[]{
                "vu",
                "nguyen",
                "truong"
        };
        JSONArray a = AccessDatabase.listParticipants(1);
        //assertEquals(studentCode.length, a.length());
        for (int i = 0; i< studentCode.length; i++){
            int actualStudentCode = ((JSONObject) a.get(i)).getInt("code");
            assertEquals(studentCode[i], actualStudentCode);
            String actualFname = ((JSONObject) a.get(i)).getString("fname");
            assertEquals(fname[i], actualFname);
            String actualLname = ((JSONObject) a.get(i)).getString("lname");
            assertEquals(lname[i], actualLname);
        }
    }

    @Test
    public void testShowSessionOn() throws SQLException {
        String[] startTime = new String[]{
                ("08:45:00"),
                ("13:00:00")
        };

        String[] endTime = new String[]{
                ("12:00:00"),
                ("16:00:00")
        };

        String[] moduleName = new String[]{
                ("Programming"),
                ("Programming"),
        };

        String[] lname = new String[]{
                ("clavel"),
                ("clavel"),
        };
        JSONArray a = AccessDatabase.showSessionOn(Date.valueOf("2018-09-05"));
        for (int i = 0; i < startTime.length; i++) {
            String actualStartTime = ((JSONObject) a.get(i)).getString("start");
            assertEquals(startTime[i], actualStartTime);

            String actualEndTime = ((JSONObject) a.get(i)).getString("end");
            assertEquals(endTime[i], actualEndTime);

            String actualModuleName = ((JSONObject) a.get(i)).getString("name");
            assertEquals(moduleName[i], actualModuleName);

            String actualLname = ((JSONObject) a.get(i)).getString("lname");
            assertEquals(lname[i], actualLname);

        }

    }


}
