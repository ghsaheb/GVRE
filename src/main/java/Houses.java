import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import RealEstatePackage.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "Houses", urlPatterns = "/houses")
public class Houses extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TO DO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        int maxPrice = Integer.MAX_VALUE;
        long minArea = 0;
        try {
            if (request.getParameter("price") != null) {
                maxPrice = Integer.parseInt(request.getParameter("price"));
            }
            if (request.getParameter("area") != null) {
                minArea = Long.parseLong(request.getParameter("area"));
            }
            if (request.getParameter("type") != null) {
                if (!(request.getParameter("type").equals("آپارتمان") || request.getParameter("type").equals("ویلایی"))){
                    throw new InvalidHouseParameterException();
                }
            }
            if (request.getParameter("deal") != null) {
                if (!(request.getParameter("deal").equals("0") || request.getParameter("type").equals("1"))){
                    throw new InvalidHouseParameterException();
                }
            }
        } catch (InvalidHouseParameterException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        ArrayList<House> payload = House.getFiltered(minArea, request.getParameter("deal")
                ,request.getParameter("type"), maxPrice);
        String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        response.getWriter().write(payloadString);
    }
}