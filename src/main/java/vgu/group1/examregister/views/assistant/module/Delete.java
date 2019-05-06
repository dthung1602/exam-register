package vgu.group1.examregister.views.assistant.module;

import vgu.group1.examregister.database.Module;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/module/delete/{id}")
public class Delete extends BaseView {
    @POST
    public Response doPost(@PathParam("id") int moduleID) throws SQLException {
        Module.cancelModule(moduleID);
        return Response.ok().build();
    }
}
