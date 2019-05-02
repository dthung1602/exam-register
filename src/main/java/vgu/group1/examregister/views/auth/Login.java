package vgu.group1.examregister.views.auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static vgu.group1.examregister.views.Utils.getHTMLFile;

@Path("/auth/login")
public class Login {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("login.html")).build();
    }

    /*@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response doPost(@FormParam("username") String username,
                           @FormParam("password") String password) throws SQLException, IOException {
        JSONObject account = listAccountUsername(username);
        if (account != null &&
                account.getString("username").equals(username) &&
                account.getString("password").equals(password)) { // TODO hash password
            String jwt = createJWT(
                    account.getInt("id"),
                    username,
                    account.getString("role")
            );
            NewCookie cookie = new NewCookie(
                    Config.AUTH_COOKIE_NAME, jwt,
                    "/",
                    "",
                    "what",
                    Config.AUTH_COOKIE_MAX_AGE,
                    false
            );
            return Response
                    .seeOther(URI.create("/"))
                    .cookie(cookie)
                    .build();
        }

        return Response.status(401).entity(getHTMLFile("login_fail.html")).build();
    }

    private String createJWT(int id, String username, String role) {
        return Jwts.builder()
                .setAudience("")
                .claim("username", username)
                .claim("accountId", id)
                .claim("role", role)
                .signWith(Config.AUTH_SIGN_ALGO, Config.SECRET_KEY)
                .compact();
    }*/
}