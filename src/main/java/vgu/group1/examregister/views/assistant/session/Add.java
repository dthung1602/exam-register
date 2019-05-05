package vgu.group1.examregister.views.assistant.session;


import vgu.group1.examregister.database.Session;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;


@Path("/assistant/session/add")
public class Add extends BaseView {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@FormParam("module-id") int moduleId,
                           @FormParam("date") Date date,
                           @FormParam("start") String start,
                           @FormParam("end") String end) throws SQLException {

        Session.createSession(moduleId, date, toSQLTime(start), toSQLTime(end));
        return Response.ok().build();
    }
}