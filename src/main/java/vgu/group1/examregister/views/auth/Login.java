package vgu.group1.examregister.views.auth;


import vgu.group1.examregister.database.Semester;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/semester/add")
public class Login {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("login.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("start-date") String startDate,
                           @FormParam("end-date") String endDate) throws SQLException {
        Semester.createSemester(
                Date.valueOf(startDate),
                Date.valueOf(endDate)
        );
        return Response.ok("").build();
    }
}