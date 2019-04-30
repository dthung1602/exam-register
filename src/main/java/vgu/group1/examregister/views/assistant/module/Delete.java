package vgu.group1.examregister.views.assistant.module;

import vgu.group1.examregister.database.Module;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/module/delete")
public class Delete {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("delete_module.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("module-id") int moduleID) throws SQLException {
        Module.cancelModule(
                moduleID
        );
        return Response.ok(Module.listAllModules().toString(), MediaType.APPLICATION_JSON).build();
    }
}
