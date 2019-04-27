package vgu.group1.examregister.views.view;


import org.json.JSONObject;
import vgu.group1.examregister.database.Semester;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;


@Path("/view/semester/{id}")
public class Semesters extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet(@PathParam("id") String id) throws IOException {
        return Response.ok(getHTMLFile("view_one_semester.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") String id) throws SQLException {
        JSONObject semester = Semester.readSemester(Integer.parseInt(id));
        return Response.ok(semester.toString()).build();
    }
}