package vgu.group1.examregister.views.assistant.exam;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/exam/delete/{id}")
public class Delete extends BaseView {
    @POST
    public Response doPost(@PathParam("id") int examID) throws SQLException {
        Exam.cancelExam(examID);
        return Response.ok().build();
    }
}
