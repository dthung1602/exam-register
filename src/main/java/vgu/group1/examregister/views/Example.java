package vgu.group1.examregister.views;


import vgu.group1.examregister.database.AccessDatabase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Root resource (exposed at "/example" path)
 */
@Path("/example")
public class Example {

//    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    public String gotIt() throws SQLException {
//        return "{}";// AccessDatabase.listModuleOverlapSessions().toString();
//    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    public Response getIt() throws SQLException {
        return Response.ok(AccessDatabase.listModuleOverlapSessions().toString(), MediaType.APPLICATION_JSON).build();
    }
}