package vgu.group1.examregister.views.assistant.session;


import vgu.group1.examregister.database.Session;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;


@Path("/assistant/session/add")
public class Add {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@FormParam("module-id") int moduleId,
                           @FormParam("date") Date date,
                           @FormParam("start") Time start,
                           @FormParam("end") Time end) throws SQLException {

        Session.createSession(moduleId, date, start, end);
        return Response.ok().build();
    }
}