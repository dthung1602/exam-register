package vgu.group1.examregister.views.student;

import vgu.group1.examregister.database.Session;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("/student/sign/{id}")
public class Sign extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int session) throws SQLException {
        int student = getAccountId();
        Session.signSession(student, session);
        return Response.ok().build();
    }
}