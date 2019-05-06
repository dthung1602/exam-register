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


@Path("/assistant/exam/add")
public class Add extends BaseView {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("assistant/add_exam.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("module-id") int moduleID,
                           @FormParam("exam-date") Date examDate,
                           @FormParam("exam-deadline") Date examDeadline,
                           @FormParam("exam-start") String examStart,
                           @FormParam("exam-end") String examEnd) throws SQLException {
        Exam.createExam(
                moduleID,
                examDate,
                examDeadline,
                toSQLTime(examStart),
                toSQLTime(examEnd)
        );
        return Response.seeOther(URI.create("/view/exam")).build();
    }
}