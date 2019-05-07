package vgu.group1.examregister.views.assistant.module;


import org.json.JSONArray;
import vgu.group1.examregister.database.Module;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;


@Path("/assistant/module/attendance/{id}")
public class ViewAttendance extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet(@PathParam("id") String id) throws IOException {
        return Response.ok(getHTMLFile("assistant/view_session_attendance.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") int id) throws SQLException {
        JSONArray module = Module.listSessionAttended(id);
        return Response.ok(module.toString()).build();
    }

}