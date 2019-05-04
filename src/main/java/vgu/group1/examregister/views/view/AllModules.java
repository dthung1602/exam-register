package vgu.group1.examregister.views.view;


import org.json.JSONArray;
import vgu.group1.examregister.database.Module;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;


@Path("/view/module/")
public class AllModules extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("assistant/view_all_modules.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost() throws SQLException {
        JSONArray modules = Module.listAllModules();
        return Response.ok(modules.toString()).build();
    }
}