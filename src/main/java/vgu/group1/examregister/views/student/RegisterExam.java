package vgu.group1.examregister.views.student;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/view/exams/")
public class RegisterExam extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@FormParam("my_student") int student,
                           @FormParam("my_exam") int exam) throws SQLException {
        Exam.registerExam(student, exam);
        return Response.ok().build();
    }
}
