package vgu.group1.examregister.views.assistant.exam;

import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

@Path("/assistant/exam/edit")
public class Edit extends BaseView {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @Produces(MediaType.TEXT_HTML)
    @GET
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("edit_exam.html")).build();
    }


    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response doPost(@FormParam("exam-id") int examID,
                           @FormParam("module-id") int moduleID,
                           @FormParam("exam-date") Date examDate,
                           @FormParam("exam-deadline") Date examDeadline,
                           @FormParam("exam-start") Time examStart,
                           @FormParam("exam-end") Time examEnd)
            throws SQLException {
        Exam.editExam(examID, moduleID, examDate, examDeadline, examStart, examEnd);
        return Response.ok(Exam.viewAnExam(examID).toString(), MediaType.APPLICATION_JSON).build();
    }
}