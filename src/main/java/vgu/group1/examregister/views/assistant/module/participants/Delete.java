package vgu.group1.examregister.views.assistant.module.participants;

import vgu.group1.examregister.database.Module;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/module/{id}/participants/delete")
public class Delete extends BaseView {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int moduleID,
                           @FormParam("student-id") int studentID) throws SQLException {
        Module.deleteParticipant(studentID, moduleID);
        return Response.ok().build();
    }
}