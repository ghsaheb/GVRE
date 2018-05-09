import RealEstatePackage.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.internal.ws.wsdl.parser.Policy15ExtensionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "PaidHouses", urlPatterns = "/paid")
public class PaidHouses extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("this is the answer"+request.getHeader("Authentication"));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String username = JWTHandler.parseJWT(request.getHeader("Authentication"));
        Boolean isAdmin;
        try {
            Individual individual = IndividualDatabaseController.getInstance().select(username);
            isAdmin = individual.isAdmin();
            if (isAdmin){
                Map<String, Object> payload = new HashMap<String, Object>();
                ArrayList<Individual> individuals = IndividualDatabaseController.getInstance().select();
                for (Individual i : individuals) {
                    ArrayList<String> houseIds = PhoneDatabaseController.getInstance().select(i);
                    payload.put(i.getUsername(), houseIds);
                }
                String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
                response.getWriter().write(payloadString);
            }
            else {
                ArrayList<String> payload = PhoneDatabaseController.getInstance().select(individual);
                String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
                response.getWriter().write(payloadString);
            }
        } catch (IndividualNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
