package vgu.group1.examregister.views.student;


import vgu.group1.examregister.database.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

//import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/student/sign/{id}")
public class Sign {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@FormParam("my_student") int student,
                           @PathParam("id") int session) throws SQLException {
        Session.signSession(student, session);
        return Response.ok().build();
    }
}