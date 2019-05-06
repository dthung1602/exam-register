package vgu.group1.examregister.views.assistant.user;

import org.json.JSONObject;
import vgu.group1.examregister.database.Account;
import vgu.group1.examregister.views.BaseView;
import vgu.group1.examregister.views.auth.PasswordAuth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

@Path("/assistant/user/add")
public class Add extends BaseView {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("assistant/add_user.html")).build();
    }
                           @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@FormParam("role") String role,
                           @FormParam("username") String username,
                           @FormParam("password") String password,
                           @FormParam("fname") String fname,
                           @FormParam("lname") String lname,
                           @FormParam("code") String code) throws SQLException {
        JSONObject result;
        password = (new PasswordAuth()).hash(password);
        switch (role) {
            case "student":
                result = Account.addNewStudent(username, password, fname, lname, code);
                break;
            case "lecturer":
                result = Account.addNewLecturer(username, password, fname, lname);
                break;
            case "assistant":
                result = Account.addNewAssistant(username, password, fname, lname);
                break;
            default:
                return Response.status(400, "Invalid role").build();
        }
        return Response.seeOther(URI.create("/assistant/user/view")).build();
    }
}
