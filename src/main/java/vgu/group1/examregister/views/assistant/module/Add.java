package vgu.group1.examregister.views.assistant.module;

import vgu.group1.examregister.database.Module;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

@Path("/assistant/module/add")
public class Add extends BaseView {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("assistant/add_module.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("module-name") String moduleName,
                           @FormParam("module-code") String moduleCode,
                           @FormParam("semester-id") int semesterID,
                           @FormParam("lecturer-id") int lecturerID) throws SQLException {
        Module.createModule(moduleName, moduleCode, semesterID, lecturerID);
        return Response.seeOther(URI.create("/view/module/")).build();
    }
}