package vgu.group1.examregister.views.assistant.user;

import vgu.group1.examregister.database.Account;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/assistant/user/delete/{id}")
public class Delete extends BaseView {
    @POST
    public Response doPost(@PathParam("id") int userId) throws SQLException {
        Account.deleteUser(userId);
        return Response.ok().build();
    }
}
