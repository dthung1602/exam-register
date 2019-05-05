package vgu.group1.examregister.views.assistant.session;


import vgu.group1.examregister.database.Session;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;


@Path("/assistant/session/edit/{id}")
public class Edit extends BaseView {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int sessionId,
                           @FormParam("date") Date date,
                           @FormParam("start") String start,
                           @FormParam("end") String end) throws SQLException {

        Session.changeSessionTime(toSQLTime(start), toSQLTime(end), date, sessionId);
        return Response.ok().build();
    }
}
