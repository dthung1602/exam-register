package vgu.group1.examregister.views.assistant.exam;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("/assistant/exam/delete")
public class Delete extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("delete_exam.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("exam-id") int examID) throws SQLException {
        Exam.cancelExam(examID);
        return Response.ok(Exam.listAllExam().toString(), MediaType.APPLICATION_JSON).build();
    }
}
