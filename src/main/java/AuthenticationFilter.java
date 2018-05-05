import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebFilter(filterName = "AuthenticationFilter", servletNames = {"HouseDetails", "Phone"})
public class AuthenticationFilter implements Filter {
    private ServletContext context;
    FilterConfig fConfig;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String jwt = request.getHeader("Authentication");
        try {
            JWTHandler.parseJWT(jwt);
//            this.context.log("Unauthorized access request");
            chain.doFilter(req, resp);
        } catch (UnsupportedEncodingException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (IllegalArgumentException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
