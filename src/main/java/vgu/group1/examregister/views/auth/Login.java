package vgu.group1.examregister.views.auth;


import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import vgu.group1.examregister.Config;
import vgu.group1.examregister.views.BaseView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import static vgu.group1.examregister.database.Account.listAccountUsername;

@Path("/auth/login")
public class Login extends BaseView {
    @GET
    public Response doGet() throws IOException {
        return Response.ok(getHTMLFile("login.html")).build();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Response doPost(@FormParam("username") String username,
                           @FormParam("password") String password) throws SQLException, IOException {
        JSONObject account = listAccountUsername(username);
        PasswordAuth passwordAuth = new PasswordAuth();
        if (account != null &&
                passwordAuth.authenticate(password, account.getString("password"))) {
            String jwt = createJWT(
                    account.getInt("id"),
                    username,
                    account.getString("role")
            );
            NewCookie cookie = new NewCookie(
                    Config.AUTH_COOKIE_NAME, jwt,
                    "/",
                    "",
                    "",
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
                .claim("username", username)
                .claim("accountId", id)
                .claim("role", role)
                .signWith(Config.AUTH_SIGN_ALGO, Config.SECRET_KEY)
                .compact();
    }
}