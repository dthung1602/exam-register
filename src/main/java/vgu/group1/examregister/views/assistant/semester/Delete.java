package vgu.group1.examregister.views.assistant.semester;

import vgu.group1.examregister.database.Semester;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/semester/delete")
public class Delete {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("delete_semester.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("semester-id") int semesterID) throws SQLException {
        Semester.deleteSemester(
                semesterID
        );
        return Response.ok(Semester.listAllSemester().toString(), MediaType.APPLICATION_JSON).build();
    }
}
