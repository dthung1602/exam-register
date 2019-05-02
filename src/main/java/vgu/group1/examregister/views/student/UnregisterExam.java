package vgu.group1.examregister.views.student;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/view/exams/{id}")
public class UnregisterExam extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") int exam_id) throws SQLException {
        Exam.unregisterExam(getAccountId(), exam_id);
        return Response.ok().build();
    }
}