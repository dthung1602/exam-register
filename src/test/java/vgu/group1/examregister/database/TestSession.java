package vgu.group1.examregister.database;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class TestSession extends TestWithDatabase {

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
        JSONArray a = Session.showSessionOn(Date.valueOf("2018-09-05"));
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
