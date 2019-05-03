package vgu.group1.examregister.views.assistant.session;

import vgu.group1.examregister.database.Session;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/session/delete/{id}")
public class Delete {
    @POST
    public Response doPost(@PathParam("id") int sessionId) throws SQLException {
        Session.cancelSession(sessionId);
        return Response.ok().build();
    }
}