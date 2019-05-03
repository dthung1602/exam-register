package vgu.group1.examregister.views.view;

import org.json.JSONObject;
import vgu.group1.examregister.database.Module;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;


@Path("/view/module/{id}")
public class OneModule extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        String role = getUserRole();
        return Response.ok(getHTMLFile(role + "/view_one_module.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("id") int moduleId) throws SQLException {
        JSONObject module = Module.viewModule(moduleId);
        return Response.ok(module.toString()).build();
    }
}