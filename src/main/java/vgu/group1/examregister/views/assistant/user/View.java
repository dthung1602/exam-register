package vgu.group1.examregister.views.assistant.user;

import vgu.group1.examregister.database.Account;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/user/view/{role}")
public class View extends BaseView {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@PathParam("role") String role) throws SQLException {
        String answer = "";
        switch (role) {
            case "assistant":
            case "student":
                break;
            case "lecturer":
                answer = Account.listAllLecturers().toString();
                break;
            default:
                return Response.status(404).build();
        }
        return Response.ok(answer).build();
    }
}
