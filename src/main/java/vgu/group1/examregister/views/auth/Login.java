package vgu.group1.examregister.views.auth;


import vgu.group1.examregister.database.AccessDatabase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Root resource (exposed at "/example" path)
 */
@Path("/auth/login")
public class Login {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    public Response getIt() throws SQLException {
        return Response.ok(
                AccessDatabase.listModuleOverlapSessions().toString(),
                MediaType.APPLICATION_JSON).build();
    }
}