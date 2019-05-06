package vgu.group1.examregister.views.student;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/student/register/{id}")
public class RegisterExam extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int examID) throws SQLException {
        Exam.registerExam(getAccountId(), examID);
        return Response.ok().build();
    }
}
