import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import RealEstatePackage.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "Houses", urlPatterns = "/houses")
public class Houses extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        long area;
        int price;
        String address;
        String phone;
        String description;
        String id;
        Boolean dealType;
        String buildingType;
        try {
            if (request.getParameter("price") == null
                    || request.getParameter("area") == null
                    || request.getParameter("address") == null
                    || request.getParameter("phone") == null
                    || request.getParameter("dealType") == null
                    || request.getParameter("buildingType") == null) {
                throw new InvalidHouseParameterException();
            }

            if (!(request.getParameter("buildingType").equals("آپارتمان") || request.getParameter("buildingType").equals("ویلایی"))){
                throw new InvalidHouseParameterException();
            }
            if (!(request.getParameter("dealType").equals("true") || request.getParameter("dealType").equals("false"))){
                throw new InvalidHouseParameterException();
            }
            if (request.getParameter("address").length() == 0){
                throw new InvalidHouseParameterException();
            }
            if (request.getParameter("phone").length() < 4 || request.getParameter("phone").length() > 20
                    || !(request.getParameter("phone").matches("[0-9]+"))){
                throw new InvalidHouseParameterException();
            }

            price = Integer.parseInt(request.getParameter("price"));
            area = Long.parseLong(request.getParameter("area"));
            address = request.getParameter("address");
            phone = request.getParameter("phone");
            dealType = Boolean.parseBoolean(request.getParameter("dealType"));
            buildingType = request.getParameter("buildingType");
            description = request.getParameter("description");

        } catch (InvalidHouseParameterException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        id = UUID.randomUUID().toString();

        if(!dealType) {
            House newHouse = new House(id, area, buildingType, address, dealType,
                    new Price(0, 0, price), phone, description);
            IndividualContainer.getIndividualContainer().getIndividual().addHouse(newHouse);
        }
        else {
            House newHouse = new House(id, area, buildingType, address, dealType,
                    new Price(0, price, 0), phone, description);
            IndividualContainer.getIndividualContainer().getIndividual().addHouse(newHouse);
        }
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