package vgu.group1.examregister.views.assistant.user;

import org.json.JSONObject;
import vgu.group1.examregister.database.Account;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/user/edit/{id}")
public class Edit extends BaseView {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doPost(@PathParam("id") int userId,
                           @FormParam("username") String username,
                           @FormParam("fname") String fname,
                           @FormParam("lname") String lname,
                           @FormParam("code") String code) throws SQLException {
        JSONObject user = Account.listAccountId(userId);
        if (user.getString("role").equals("student"))
            Account.updateStudent(userId, username, fname, lname, code);
        else
            Account.updateUser(userId, username, fname, lname);
        return Response.ok().build();
    }
}
