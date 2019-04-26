package vgu.group1.examregister.database;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static vgu.group1.examregister.database.Utils.convertOne;

public class TestSemester extends TestWithDatabase {

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
            Semester.createSemester(startDates[i], endDates[i]);
            ResultSet rs = statement.executeQuery("SELECT * FROM SEMESTER ORDER BY id DESC");
            JSONAssert.assertEquals(expectedResults[i], convertOne(rs).toString(), JSONCompareMode.LENIENT);
        }
    }
}
