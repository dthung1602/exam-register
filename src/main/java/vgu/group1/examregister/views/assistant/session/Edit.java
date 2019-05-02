package vgu.group1.examregister.views.assistant.session;


import vgu.group1.examregister.database.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;


@Path("/assistant/session/edit/{id}")
public class Edit {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int sessionId,
                           @FormParam("moduleId") int moduleId,
                           @FormParam("sessionDate") Date sessionDate,
                           @FormParam("start") Time start,
                           @FormParam("end") Time end) throws SQLException {
        Session.changeSessionTime(start, end, sessionDate, sessionId);
        return Response.ok("").build();
    }
}
