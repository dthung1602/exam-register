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

    //
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
    public void testModuleStudentEnroll() throws SQLException {
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
    public void testListSessionStudent() throws SQLException, ClassNotFoundException {
        int[] moduleID = new int[]{1, 2, 3};
        String[] lnames = new String[]{"hung", "hung", "hung"};
        int[] expectedAttendanceCount = new int[]{1, 2, 4};

        for (int i = 0; i < moduleID.length; i++) {
            JSONArray sessionsCount = AccessDatabase.listSessionStudent(lnames[i], moduleID[i]);
            int ac = ((JSONObject) sessionsCount.get(i)).getInt("attendance_count");
            assertEquals(expectedAttendanceCount[i], ac);
        }
    }

    @Test
    public void testListSessionInModules() throws SQLException {
        int[] studentID = new int[]{1, 2, 3};
        String[] exepectedName = new String[]{"hung", "hung", "hung"};//check name
        int[] expectedAttendanceCount = new int[]{3, 4, 5}; //check attendance


        for (int i = 0; i < studentID.length; i++) {
            JSONArray returnResult = AccessDatabase.listSessionInModules(studentID[i]);
            int attendance_count = ((JSONObject) returnResult.get(i)).getInt("attendance_count");
            String check_name = ((JSONObject) returnResult.get(i)).getString("name");
            assertEquals(expectedAttendanceCount[i], attendance_count);
            assertEquals(exepectedName[i], check_name);
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
