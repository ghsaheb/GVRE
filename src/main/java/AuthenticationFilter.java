import UtilsPackage.JWTHandler;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebFilter(filterName = "AuthenticationFilter", servletNames = {"HouseDetails", "Phone", "PaidHouses", "Credit"})
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
            if(request.getMethod().equals("OPTIONS")){
                System.out.println("option request");
                chain.doFilter(req, resp);
            }
            else {
                JWTHandler.parseJWT(jwt);
                System.out.println("jwt"+jwt);
                addCorsHeader(response);
                chain.doFilter(req, resp);
            }
        } catch (UnsupportedEncodingException e){
            System.out.println("unsupported encoding");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (IllegalArgumentException e){
            System.out.println("jwt"+jwt);
            System.out.println("illegal args");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void addCorsHeader(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept,Authentication");
        response.addHeader("Access-Control-Max-Age", "1728000");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
