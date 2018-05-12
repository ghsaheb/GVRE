import RealEstatePackage.HouseNotFindException;
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

@WebServlet(name = "Phone", urlPatterns = "/phone")
public class Phone extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean resp;
        BufferedReader in = request.getReader();
        String inputLine;
        StringBuffer body = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            body.append(inputLine);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = new ObjectMapper().readValue(body.toString(), new TypeReference<Map<String, String>>() {});
            if (map.get("id") == null){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            resp = IndividualDatabaseController.getInstance().select(JWTHandler.parseJWT(request.getHeader("Authentication"))).addPaidHouse(map.get("id").toString());
            if (resp) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } catch (HouseNotFindException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
        } catch (IndividualNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("in doGet Phone");
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (request.getParameter("id") == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            if (IndividualDatabaseController.getInstance().select(JWTHandler.parseJWT(request.getHeader("Authentication"))).searchForHouse(request.getParameter("id"))){
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (HouseNotFindException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IndividualNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
