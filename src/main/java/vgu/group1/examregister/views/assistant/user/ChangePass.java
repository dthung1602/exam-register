package vgu.group1.examregister.views.assistant.user;

import vgu.group1.examregister.database.Account;
import vgu.group1.examregister.views.BaseView;
import vgu.group1.examregister.views.auth.PasswordAuth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("/assistant/user/changepass/{id}")
public class ChangePass extends BaseView {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("assistant/changepass.html")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int userId,
                           @FormParam("password") String password) throws SQLException {
        password = (new PasswordAuth()).hash(password);
        Account.changePassword(userId, password);
        return Response.ok().build();
    }
}
