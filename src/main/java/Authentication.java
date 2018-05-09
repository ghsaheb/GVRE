import RealEstatePackage.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@WebServlet(name = "Authentication", urlPatterns = "/auth")
public class Authentication extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        BufferedReader in = request.getReader();
        String inputLine;
        StringBuffer body = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            body.append(inputLine);
        }
        Individual newIndiv = null;
        try {
            newIndiv = new ObjectMapper().readValue(body.toString(), Individual.class);
            if (newIndiv.getUsername() == null
                    || newIndiv.getPassword() == null) {
                throw new InvalidIndividualParameterException();
            }
        } catch (InvalidIndividualParameterException e) {
            e.printStackTrace();
        }
        try {
            Individual dbIndiv = IndividualDatabaseController.getInstance().select(newIndiv.getUsername());
            if(dbIndiv.getPassword().equals(newIndiv.getPassword())){
                System.out.println(dbIndiv.getPassword()+"ba in :"+newIndiv.getPassword());
                String jwt = JWTHandler.createJWT(dbIndiv.getUsername());
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader("Authentication",jwt);
                response.getWriter().write(dbIndiv.getName());
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (IndividualNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        response.addHeader("Access-Control-Expose-Headers", "Authentication");
    }


    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT, GET, OPTIONS, HEAD, POST");
        resp.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, Authentication");
        resp.addHeader("Access-Control-Expose-Headers", "Authentication");
        resp.addHeader("Access-Control-Max-Age", "1728002");;
    }
}
