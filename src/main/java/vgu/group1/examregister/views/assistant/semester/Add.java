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

@Path("/assistant/semester/add")
public class Add extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("/assistant/add_semester.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@FormParam("start-date") Date startDate,
                           @FormParam("end-date") Date endDate) throws SQLException {
        Semester.createSemester(startDate, endDate);
        return Response.seeOther(URI.create("/view/semester")).build();
    }
}
