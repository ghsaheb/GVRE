import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;

import RealEstatePackage.*;
import UtilsPackage.EscapeUtils;
import UtilsPackage.JWTHandler;
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
            if (newHouse.getBuildingType() == null
                    || newHouse.getAddress() == null
                    || newHouse.getPhone() == null
                    || newHouse.getPrice() == null) {
                throw new InvalidHouseParameterException();
            }
            if (!(newHouse.getBuildingType().equals("آپارتمان") || newHouse.getBuildingType().equals("ویلایی"))){
                throw new InvalidHouseParameterException();
            }
            if (newHouse.getAddress().length() == 0){
                throw new InvalidHouseParameterException();
            }
            if (newHouse.getPhone().length() < 4 || newHouse.getPhone().length() > 20
                    || !(newHouse.getPhone().matches("[0-9]+"))){
                throw new InvalidHouseParameterException();
            }
            if (newHouse.getArea() <= 0){
                throw new InvalidHouseParameterException();
            }
            if (newHouse.getDescription() != null){
                if (newHouse.getDescription().length() != 0){
                    StringWriter writer = new StringWriter((int) (newHouse.getDescription().length() * 1.5));
                    EscapeUtils.escape(writer, newHouse.getDescription());
                    newHouse.setDescription(writer.toString());
                }
            }
            if (newHouse.getAddress() != null){
                if (newHouse.getAddress().length() != 0){
                    StringWriter writer = new StringWriter((int) (newHouse.getAddress().length() * 1.5));
                    EscapeUtils.escape(writer, newHouse.getAddress());
                    newHouse.setAddress(writer.toString());
                }
            }

        } catch (JsonGenerationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (JsonMappingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (InvalidHouseParameterException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = UUID.randomUUID().toString();
        newHouse.setId(id);
        try {
            if (request.getHeader("Authentication") == null){
                throw new NullPointerException();
            }
            IndividualDatabaseController.getInstance().select(JWTHandler.parseJWT(request.getHeader("Authentication"))).addHouseToDatabase(newHouse);
        } catch (IndividualNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (NullPointerException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("this is the answer"+request.getHeader("Authentication"));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");

        int maxPrice = Integer.MAX_VALUE;
        long minArea = 0;
        String buildingType = null;
        String dealType = null;
        try {
            if (request.getParameter("price") != null) {
                if (!request.getParameter("price").equals("")) {
                    maxPrice = Integer.parseInt(request.getParameter("price"));
                }
            }
            if (request.getParameter("area") != null) {
                if (!request.getParameter("area").equals("")) {
                    minArea = Long.parseLong(request.getParameter("area"));
                }
            }
            if (request.getParameter("buildingType") != null) {
                if (!(request.getParameter("buildingType").equals("آپارتمان") || request.getParameter("buildingType").equals("ویلایی")
                        || request.getParameter("buildingType").equals(""))){
                    throw new InvalidHouseParameterException();
                }
                if (request.getParameter("buildingType").equals("")){
                    buildingType = null;
                }
                else {
                    buildingType = request.getParameter("buildingType");
                }
            }
            if (request.getParameter("dealType") != null) {
                if (!(request.getParameter("dealType").equals("true") || request.getParameter("dealType").equals("false")
                        || request.getParameter("dealType").equals(""))){
                    throw new InvalidHouseParameterException();
                }
                if (request.getParameter("dealType").equals("")){
                    dealType = null;
                }
                else {
                    dealType = request.getParameter("dealType");
                }
            }
        } catch (InvalidHouseParameterException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        ArrayList<House> payload = House.getFiltered(minArea, dealType
                , buildingType, maxPrice);
        String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        response.getWriter().write(payloadString);
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