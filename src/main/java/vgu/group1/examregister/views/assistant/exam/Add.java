package vgu.group1.examregister.views.assistant.exam;


import vgu.group1.examregister.database.Exam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/exam/add")
public class Add {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("add_exam.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("examDate") String examDate,
                           @FormParam("deadline") String deadline,
                           @FormParam("moduleId") String id,
                           @FormParam("start") String start,
                           @FormParam("end") String end) throws SQLException {

        Exam.addExam(
                Integer.parseInt(id),
                Date.valueOf(examDate),
                Date.valueOf(deadline),
                Time.valueOf(start),
                Time.valueOf(end)
        );
        return Response.ok("").build();
    }
}