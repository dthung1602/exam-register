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

public class TestModule extends TestWithDatabase {

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
    public void testModuleOverlapSessions() throws SQLException {
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

        JSONArray a = Module.listModuleOverlapSessions();
        for (int i = 0; i < module1Code.length; i++) {
            String actualDate = ((JSONObject) a.get(i)).getString("date");
            assertEquals(sessionDates[i], actualDate);

            String actualModule1Code = ((JSONObject) a.get(i)).getString("code1");
            assertEquals(module1Code[i], actualModule1Code);

            String actualModule2Code = ((JSONObject) a.get(i)).getString("code2");
            assertEquals(module2Code[i], actualModule2Code);
        }
    }

//    @Test
//    public void testListModule() throws SQLException {
//        int[] semesterIds = new int[]{1, 2, 3, 4, 5};
//        Integer[][] expectedModuleIds = new Integer[][]{
//                new Integer[]{1, 2, 3},
//                new Integer[]{4, 5, 6, 7},
//                new Integer[]{8, 9, 10},
//                new Integer[]{11, 12},
//                new Integer[]{},
//        };
//        for (int i = 0; i < semesterIds.length; i++) {
//            JSONArray modules = Module.viewModule(semesterIds[i]);
//            assertEquals(expectedModuleIds[i].length, modules.length());
//
//            HashSet<Integer> expectedIdSet = new HashSet<>(Arrays.asList(expectedModuleIds[i]));
//            for (int j = 0; j < modules.length(); j++) {
//                int actualId = ((JSONObject) modules.get(j)).getInt("id");
//                assertTrue(expectedIdSet.contains(actualId));
//            }
//        }
//    }
}
