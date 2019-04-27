package vgu.group1.examregister.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import vgu.group1.examregister.Config;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequireLogin implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        Cookie authCookie = null;
        for (Cookie cookie :request.getCookies())
            if (cookie.getName().equals(Config.AUTH_COOKIE_NAME)) {
                authCookie = cookie;
                break;
            }

        if (authCookie == null) {
            response.sendError(403, "Missing Authorization cookie");
            return;
        }

        try {
            final String token = authCookie.getValue();
            final Claims claims =
                    Jwts.parser().setSigningKey(Config.SECRET_KEY).parseClaimsJws(token).getBody();

            request.setAttribute("role", claims.get("role", String.class));
            request.setAttribute("accountId", claims.get("accountId", Integer.class));
            request.setAttribute("username", claims.get("username", Integer.class));

        } catch (final Exception e) {
            ((HttpServletResponse) res).sendError(403, "Invalid token");
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
