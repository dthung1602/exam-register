package vgu.group1.examregister.views.assistant.semester;


import vgu.group1.examregister.database.Semester;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import java.net.URI;
import java.sql.Date;
import java.sql.SQLException;

@Path("/assistant/semester/edit/{id}")
public class Edit extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("/assistant/edit_semester.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") int semesterID,
                           @FormParam("semester-start") Date startDate,
                           @FormParam("semester-end") Date endDate) throws SQLException {
        Semester.updateSemester(semesterID, startDate, endDate);
        return Response.seeOther(URI.create("/view/semester/")).build();
    }
}
