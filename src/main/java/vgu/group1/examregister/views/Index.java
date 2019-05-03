package vgu.group1.examregister.views;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class Index extends BaseView {

    @GET
    public Response doGet() throws IOException {
        String role = getUserRole();
        return Response.ok(getHTMLFile(role + "/index.html")).build();
    }
}
