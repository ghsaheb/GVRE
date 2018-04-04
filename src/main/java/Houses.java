import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

import RealEstatePackage.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "Houses", urlPatterns = "/houses")
public class Houses extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        BufferedReader in = request.getReader();
        String inputLine;
        StringBuffer body = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            body.append(inputLine);
        }
        House newHouse;
        try {
            newHouse = new ObjectMapper().readValue(body.toString(), House.class);
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
        String id = UUID.randomUUID().toString();
        newHouse.setId(id);
        IndividualContainer.getIndividualContainer().getIndividual().addHouse(newHouse);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");

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
                if (!(request.getParameter("deal").equals("0") || request.getParameter("deal").equals("1"))){
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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, HEAD");
        resp.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        resp.addHeader("Access-Control-Max-Age", "1728000");;
    }
}