package vgu.group1.examregister.views.assistant.semester;


import org.json.JSONObject;
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
        return Response.ok(getHTMLFile("add_semester.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("start-date") String startDate,
                           @FormParam("end-date") String endDate) throws SQLException {
        JSONObject semester = Semester.createSemester(
                Date.valueOf(startDate),
                Date.valueOf(endDate)
        );
        URI uri = URI.create("/assistant/semester/edit/" + semester.getString("id"));
        return Response.seeOther(uri).build();
    }
}