import RealEstatePackage.IndividualContainer;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Credit", urlPatterns = "/credit")
public class Credit extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int amount = 0;
        int id = -1;
        try {
            amount = Integer.parseInt(request.getParameter("amount"));
            id = Integer.parseInt(request.getParameter("userId"));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (NullPointerException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setStatus(IndividualContainer.getIndividualContainer().getIndividual().addCredit(amount));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("userId", IndividualContainer.getIndividualContainer().getIndividual().getId());
        payload.put("credit", IndividualContainer.getIndividualContainer().getIndividual().getCredit());
        String payloadString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        response.getWriter().write(payloadString);
    }

    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT, GET, OPTIONS, HEAD");
        resp.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        resp.addHeader("Access-Control-Max-Age", "1728000");;
    }
}
