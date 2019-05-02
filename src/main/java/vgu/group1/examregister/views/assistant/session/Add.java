package vgu.group1.examregister.views.assistant.session;


import vgu.group1.examregister.database.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/session/add")
public class Add {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("add_session.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("sessionDate") Date sessionDate,
                           @FormParam("moduleId") int moduleId,
                           @FormParam("start") Time start,
                           @FormParam("end") Time end) throws SQLException {

        Session.createSession(moduleId, sessionDate, start, end);
        return Response.ok("").build();
    }

}