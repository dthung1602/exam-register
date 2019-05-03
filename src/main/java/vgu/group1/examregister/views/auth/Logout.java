package vgu.group1.examregister.views.auth;

import vgu.group1.examregister.Config;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

@Path("/auth/logout")
public class Logout extends BaseView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        NewCookie deleteAuthCookie = new NewCookie(Config.AUTH_COOKIE_NAME, null, "/", null, null, 0, false);
        return Response.seeOther(URI.create("/auth/login")).cookie(deleteAuthCookie).build();
    }
}