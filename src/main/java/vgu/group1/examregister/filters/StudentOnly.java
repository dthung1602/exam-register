package vgu.group1.examregister.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentOnly implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        String role = (String) request.getAttribute("role");
        if (!role.equals("student")) {
            response.sendError(403, "You do not have permission to access this page");
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
