package vn.edu.vgu;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static vn.edu.vgu.Utils.convertOne;

public class AccessDatabaseTest extends TestWithDatabase {

    @Before
    public void before() {
        loadFixtures(new String[]{
                "account.sql",
                "assistant.sql",
                "lecturer.sql",
                "student.sql",
                "semester.sql",
                "module.sql",
                "session.sql",
                "exam.sql",
                "exam_reg.sql",
                "sign.sql",
                "teach.sql",
                "enroll.sql"
        });
    }

    @Test
    public void testListModule() throws SQLException, ClassNotFoundException {
        int[] semesterIds = new int[]{1, 2, 3, 4, 5};
        Integer[][] expectedModuleIds = new Integer[][]{
                new Integer[]{1, 2, 3},
                new Integer[]{4, 5, 6, 7},
                new Integer[]{8, 9, 10},
                new Integer[]{11, 12},
                new Integer[]{},
        };
        for (int i = 0; i < semesterIds.length; i++) {
            JSONArray modules = AccessDatabase.listModules(semesterIds[i]);
            assertEquals(expectedModuleIds[i].length, modules.length());

            HashSet<Integer> expectedIdSet = new HashSet<>(Arrays.asList(expectedModuleIds[i]));
            for (int j = 0; j < modules.length(); j++) {
                int actualId = ((JSONObject) modules.get(j)).getInt("id");
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
                "{start: \"2011-09-01\", end: \"2012-02-29\", id: 6}",
                "{start: \"2012-06-15\", end: \"2013-03-01\", id: 7}",
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
    public void testListEnrolledModule() throws SQLException {
        int[] studentIDs = new int[]{3, 4, 5, 6, 7, 10};
        Integer[][] expectedModuleIds = new Integer[][]{
                new Integer[]{4, 1},
                new Integer[]{1, 5, 6, 12},
                new Integer[]{1, 2, 3, 4, 5, 6, 7, 8},
                new Integer[]{9, 10, 11, 12},
                new Integer[]{1, 2},
                new Integer[]{}
        };
        for (int i = 0; i < studentIDs.length; i++) {
            JSONArray modules = AccessDatabase.listModuleStudentEnroll(studentIDs[i]);
            assertEquals(expectedModuleIds[i].length, modules.length());
            HashSet<Integer> expectedIdSet = new HashSet<>(Arrays.asList(expectedModuleIds[i]));
            for (int j = 0; j < modules.length(); j++) {
                int actualId = ((JSONObject) modules.get(j)).getInt("id");
                assertTrue(expectedIdSet.contains(actualId));
            }
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
        for (int i = 0; i < studentCode.length; i++) {
            int actualStudentCode = ((JSONObject) a.get(i)).getInt("code");
            assertEquals(studentCode[i], actualStudentCode);
            String actualFname = ((JSONObject) a.get(i)).getString("fname");
            assertEquals(fname[i], actualFname);
            String actualLname = ((JSONObject) a.get(i)).getString("lname");
            assertEquals(lname[i], actualLname);
        }
    }

    @Test
    public void testViewRegisteredExam() throws SQLException {
        int[] studentIDs = new int[]{1, 2, 3, 10};
        Integer[][] expectedExamIds = new Integer[][]{
                new Integer[]{1, 2, 3},
                new Integer[]{2, 3, 4},
                new Integer[]{1, 4},
                new Integer[]{}
        };

        for (int i = 0; i < studentIDs.length; i++) {
            JSONArray modules = AccessDatabase.viewRegisteredExam(studentIDs[i]);
            assertEquals(expectedExamIds[i].length, modules.length());
            HashSet<Integer> expectedIdSet = new HashSet<>(Arrays.asList(expectedExamIds[i]));
            for (int j = 0; j < modules.length(); j++) {
                int actualId = ((JSONObject) modules.get(j)).getInt("id");
                assertTrue(expectedIdSet.contains(actualId));
            }
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

    @Test
    public void testListSessionStudent() throws SQLException, ClassNotFoundException {
        int[] moduleID = new int[]{2, 3};
        String[] lnames = new String[]{"hung", "hung"};
        int[] expectedAttendanceCount = new int[]{4, 5};

        for (int i = 0; i < moduleID.length; i++) {
            JSONArray sessionsCount = AccessDatabase.listSessionStudent(lnames[i], moduleID[i]);
            int ac = ((JSONObject) sessionsCount.get(0)).getInt("attendance_count");
            assertEquals(expectedAttendanceCount[i], ac);
        }
    }

    @Test
    public void testAddNewStudentFail() {
        String[][] expectedValues = new String[][]{
                new String[]{"vth", "dth", "abc"},
                new String[]{"hahahaha", "hihihihi", "hohohoho"},
                new String[]{"vu", "nguyen", "a"},
                new String[]{"tuan hung", "truong thanh hung", "bc"},
                new String[]{"11111", "22222", "12112"}
        };
        for (int i = 0; i < expectedValues[0].length; i++) {
            boolean exceptionThrown = false;
            try {
                AccessDatabase.addNewStudent(
                        expectedValues[0][i],
                        expectedValues[1][i],
                        expectedValues[2][i],
                        expectedValues[3][i],
                        expectedValues[4][i]);

            } catch (SQLException e) {
                exceptionThrown = true;
            }
            assertTrue(exceptionThrown);
        }
    }
}
