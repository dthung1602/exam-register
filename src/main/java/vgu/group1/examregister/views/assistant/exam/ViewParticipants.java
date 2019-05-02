package vgu.group1.examregister.views.assistant.exam;


import org.json.JSONArray;
import vgu.group1.examregister.database.Exam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/exam/view/{id}")
public class ViewParticipants{

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet(@PathParam("id") String id) throws IOException {
        return Response.ok(getHTMLFile("view_exam_participants.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") String id) throws SQLException {
        JSONArray exam = Exam.listParticipants(Integer.parseInt(id));
        return Response.ok(exam.toString()).build();
    }

}