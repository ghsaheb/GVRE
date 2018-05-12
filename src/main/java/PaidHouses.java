import RealEstatePackage.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                if (request.getParameter("size") != null && request.getParameter("page") != null){
                    try {
                        int size = Integer.parseInt(request.getParameter("size"));
                        int page = Integer.parseInt(request.getParameter("page"));
                        if (size <= 0 || page <= 0){
                            throw new InvalidHouseParameterException();
                        }
                        if ((page - 1) * size >= individuals.size()){
                            throw new InvalidHouseParameterException();
                        }
                        int upper = page * size;
                        if ((page * size) >= individuals.size()){
                            upper = individuals.size();
                        }
                        individuals = new ArrayList<Individual> (individuals.subList(((page - 1) * size), upper));

                    } catch (NumberFormatException e){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    } catch (InvalidHouseParameterException e){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }
                for (Individual i : individuals) {
                    ArrayList<String> houseIds = PhoneDatabaseController.getInstance().select(i);
                    payload.put(i.getUsername(), houseIds);
                }
                String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
                response.getWriter().write(payloadString);
            }
            else {
                ArrayList<String> payload = PhoneDatabaseController.getInstance().select(individual);
                if (request.getParameter("size") != null && request.getParameter("page") != null){
                    try {
                        int size = Integer.parseInt(request.getParameter("size"));
                        int page = Integer.parseInt(request.getParameter("page"));
                        if (size <= 0 || page <= 0){
                            throw new InvalidHouseParameterException();
                        }
                        if ((page - 1) * size >= payload.size()){
                            throw new InvalidHouseParameterException();
                        }
                        int upper = page * size;
                        if ((page * size) >= payload.size()){
                            upper = payload.size();
                        }
                        payload = new ArrayList<String> (payload.subList(((page - 1) * size), upper));
                    } catch (NumberFormatException e){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    } catch (InvalidHouseParameterException e){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }
                String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
                response.getWriter().write(payloadString);
            }
        } catch (IndividualNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
