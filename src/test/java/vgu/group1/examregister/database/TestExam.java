package vgu.group1.examregister.database;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class TestExam extends TestWithDatabase {

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

        // get participants in exam 1
        JSONArray a = Exam.listParticipants(1);

        assertEquals(studentCode.length, a.length());
        for (int i = 0; i < studentCode.length; i++) {
            int actualStudentCode = ((JSONObject) a.get(i)).getInt("code");
            assertEquals(studentCode[i], actualStudentCode);
            String actualFname = ((JSONObject) a.get(i)).getString("fname");
            assertEquals(fname[i], actualFname);
            String actualLname = ((JSONObject) a.get(i)).getString("lname");
            assertEquals(lname[i], actualLname);
        }
    }
}
