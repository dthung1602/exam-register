package vgu.group1.examregister.views.assistant.module;

import vgu.group1.examregister.database.Module;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/assistant/module/edit")
public class Edit {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("edit_module.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("module-id") int moduleID,
                           @FormParam("module-name") String moduleName,
                           @FormParam("module-code") String moduleCode,
                           @FormParam("semester-id") int semesterID)
            throws SQLException {
        Module.updateModule(moduleName, moduleCode, semesterID, moduleID);
        return Response.ok(Module.viewAModule(moduleID).toString(), MediaType.APPLICATION_JSON).build();
    }
}