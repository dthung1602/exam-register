package vgu.group1.examregister.views.assistant.exam;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

@Path("/assistant/exam/edit/{id}")
public class Edit extends BaseView {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("edit_exam.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int examID,
                           @FormParam("module-id") int moduleID,
                           @FormParam("exam-date") Date examDate,
                           @FormParam("exam-deadline") Date examDeadline,
                           @FormParam("exam-start") Time examStart,
                           @FormParam("exam-end") Time examEnd)
            throws SQLException {
        Exam.editExam(examID, moduleID, examDate, examDeadline, examStart, examEnd);
        return Response.seeOther(URI.create("/view/exam/" + examID)).build();
    }
}