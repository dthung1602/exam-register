package vgu.group1.examregister.views.assistant.module.participants;

import vgu.group1.examregister.database.Module;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/module/{id}/participants/add")
public class Add extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int moduleID,
                           @FormParam("student-code") String studentCode) throws SQLException {
        Module.addParticipant(studentCode, moduleID);
        return Response.ok().build();
    }
}