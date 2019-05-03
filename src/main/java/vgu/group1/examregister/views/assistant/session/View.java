package vgu.group1.examregister.views.assistant.session;


import org.json.JSONArray;
import vgu.group1.examregister.database.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("/assistant/session/{module-id}")
public class View {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("module-id") int moduleId) throws SQLException {
        JSONArray sessions = Session.listSessionInModule(moduleId);
        return Response.ok(sessions.toString()).build();
    }
}
