package vgu.group1.examregister.views.student;

import org.json.JSONArray;
import vgu.group1.examregister.database.Session;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("/student/sign")
public class CurrentSession extends BaseView {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("student/sign.html")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost() throws IOException, SQLException {
        JSONArray sessions = Session.listCurrentSessionOfStudent(getAccountId());
        return Response.ok(sessions.toString()).build();
    }
}
