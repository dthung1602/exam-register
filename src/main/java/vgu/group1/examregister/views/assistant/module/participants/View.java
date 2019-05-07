
package vgu.group1.examregister.views.assistant.module.participants;

import org.json.JSONArray;
import vgu.group1.examregister.database.Enroll;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("/assistant/module/{id}/participants")
public class View extends BaseView {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("assistant/view_module_participants.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") int moduleId) throws SQLException {
        JSONArray students = Enroll.viewStudentsOfModule(moduleId);
        return Response.ok(students.toString()).build();
    }
}
