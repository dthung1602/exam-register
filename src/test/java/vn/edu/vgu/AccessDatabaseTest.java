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
        loadFixtures(new String[]{"semester.sql", "module.sql","enroll.sql","student.sql","exam_reg"});
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
  
    //
    @Test
    public void testListEnrolledModule() throws SQLException {
        int[] studentIDs = new int[]{3, 4, 5, 6, 7, 10};
        Integer[][] expectedModuleIds = new Integer[][]{
            new Integer[]{4,1},
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
                int actualId = ((JSONObject) modules.get(i)).getInt("id");
                assertTrue(expectedIdSet.contains(actualId));
            }
        }
    }

    @Test

    public void testViewRegisterdExam() throws SQLException {
        int[] studentIDs = new int[]{1, 2, 3, 4, 5, 7};
        Integer[][] expectedExamIds = new Integer[][]{
            new Integer[]{1, 2, 3},
            new Integer[]{2, 3, 4},
            new Integer[]{1, 4},
            new Integer[]{4, 5, 6, 12},
            new Integer[]{}
        };

        for (int i = 0; i < studentIDs.length; i++) {
            JSONArray modules = AccessDatabase.listModuleStudentEnroll(studentIDs[i]);
            assertEquals(expectedExamIds[i].length, modules.length());
            HashSet<Integer> expectedIdSet = new HashSet<>(Arrays.asList(expectedExamIds[i]));
            for (int j = 0; j < modules.length(); j++) {
                int actualId = ((JSONObject) modules.get(i)).getInt("id");
                assertTrue(expectedIdSet.contains(actualId));
            }
        }
    }

}
