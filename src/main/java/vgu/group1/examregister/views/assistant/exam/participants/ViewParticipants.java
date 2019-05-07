package vgu.group1.examregister.views.assistant.exam.participants;

import org.json.JSONArray;
import vgu.group1.examregister.database.Exam;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("/assistant/exam/{id}/participants")
public class ViewParticipants extends BaseView {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") int examID)
            throws SQLException {
        JSONArray participants = Exam.listParticipants(examID);
        return Response.ok(participants.toString()).build();
    }
}