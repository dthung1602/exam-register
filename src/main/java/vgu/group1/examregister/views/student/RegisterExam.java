package vgu.group1.examregister.views.student;

import vgu.group1.examregister.database.Exam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

//import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/view/exams/")
public class RegisterExam {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@FormParam("my_student") int student,
                           @FormParam("my_exam") int exam) throws SQLException {
        Exam.registerExam(student, exam);
        return Response.ok().build();
    }
}
