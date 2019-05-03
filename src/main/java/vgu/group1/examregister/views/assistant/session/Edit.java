package vgu.group1.examregister.views.assistant.session;


import vgu.group1.examregister.database.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Path("/assistant/session/edit/{id}")
public class Edit {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int sessionId,
                           @FormParam("date") Date date,
                           @FormParam("start") Time start,
                           @FormParam("end") Time end) throws SQLException {

        Session.changeSessionTime(start, end, date, sessionId);
        return Response.ok().build();
    }

    private Date toDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        try {
            return new Date(format.parse(str).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}
