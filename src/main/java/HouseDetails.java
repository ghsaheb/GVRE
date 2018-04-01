import RealEstatePackage.House;
import RealEstatePackage.HouseNotFindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HouseDetails", urlPatterns = "/houses/*")
public class HouseDetails extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String[] pathInfo = request.getPathInfo().split("/");
        String id;
        try {
            id = pathInfo[1];
        } catch (IndexOutOfBoundsException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        House payload;
        try {
            payload = House.findHouse(id);
        } catch (HouseNotFindException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        response.getWriter().write(payloadString);
    }
}