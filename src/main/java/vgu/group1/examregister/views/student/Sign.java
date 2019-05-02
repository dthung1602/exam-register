package vgu.group1.examregister.views.student;

import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/student/sign")
public class Sign extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("sign.html")).build();
    }
}