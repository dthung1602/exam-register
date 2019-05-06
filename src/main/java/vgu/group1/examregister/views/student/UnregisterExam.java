package vgu.group1.examregister.views.student;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/student/unregister/{id}")
public class UnregisterExam extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int examID) throws SQLException {
        Exam.unregisterExam(getAccountId(), examID);
        return Response.ok().build();
    }
}
