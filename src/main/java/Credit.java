import RealEstatePackage.Individual;
import RealEstatePackage.IndividualDatabaseController;
import RealEstatePackage.IndividualNotFoundException;
import RealEstatePackage.JWTHandler;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Credit", urlPatterns = "/credit")
public class Credit extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        int amount = 0;
        String id;
        BufferedReader in = request.getReader();
        String inputLine;
        StringBuffer body = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            body.append(inputLine);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = new ObjectMapper().readValue(body.toString(), new TypeReference<Map<String, String>>(){});
            amount = Integer.parseInt(map.get("amount").toString());
            id = map.get("userId").toString();
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (NullPointerException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (JsonGenerationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (JsonMappingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            response.setStatus(IndividualDatabaseController.getInstance().select(JWTHandler.parseJWT(request.getHeader("Authentication"))).addCredit(amount));
        } catch (IndividualNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            Map<String, Object> payload = new HashMap<String, Object>();
            Individual individual = IndividualDatabaseController.getInstance().select(JWTHandler.parseJWT(request.getHeader("Authentication")));
            payload.put("userId", individual.getUsername());
            payload.put("credit", individual.getCredit());
            String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            response.getWriter().write(payloadString);
        } catch (IndividualNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT, GET, OPTIONS, HEAD, POST");
        resp.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, Authentication");
        resp.addHeader("Access-Control-Expose-Headers", "Authentication");
        resp.addHeader("Access-Control-Max-Age", "1728002");;
    }
}
