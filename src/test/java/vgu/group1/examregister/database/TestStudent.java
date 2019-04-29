package vgu.group1.examregister.database;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestStudent extends TestWithDatabase {
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
            JSONArray modules = Module.listModuleStudentEnroll(studentIDs[i]);
            assertEquals(expectedModuleIds[i].length, modules.length());
            HashSet<Integer> expectedIdSet = new HashSet<>(Arrays.asList(expectedModuleIds[i]));
            for (int j = 0; j < modules.length(); j++) {
                int actualId = ((JSONObject) modules.get(j)).getInt("id");
                assertTrue(expectedIdSet.contains(actualId));
            }
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
            JSONArray modules = Exam.viewRegisteredExam(studentIDs[i]);
            assertEquals(expectedExamIds[i].length, modules.length());
            HashSet<Integer> expectedIdSet = new HashSet<>(Arrays.asList(expectedExamIds[i]));
            for (int j = 0; j < modules.length(); j++) {
                int actualId = ((JSONObject) modules.get(j)).getInt("id");
                assertTrue(expectedIdSet.contains(actualId));
            }
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
                Account.addNewStudent(
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
