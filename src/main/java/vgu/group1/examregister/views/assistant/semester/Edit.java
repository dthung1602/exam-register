package vgu.group1.examregister.views.assistant.semester;

import vgu.group1.examregister.database.Semester;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/semester/edit")
public class Edit {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("edit_semester.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("semester-id") int semesterID,
                           @FormParam("start-date") Date startDate,
                           @FormParam("end-date") Date endDate) throws SQLException {
        Semester.updateSemester(
                semesterID, startDate, endDate
        );
        return Response.ok(Semester.viewASemester(semesterID).toString(), MediaType.APPLICATION_JSON).build();
    }
}
