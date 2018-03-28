import RealEstatePackage.HouseNotFindException;
import RealEstatePackage.IndividualContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Phone", urlPatterns = "/phone")
public class Phone extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null || request.getParameter("userId") == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        boolean resp;
        try {
            resp = IndividualContainer.getIndividualContainer().getIndividual().addPaidHouse(request.getParameter("id"));
        } catch (HouseNotFindException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (resp) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null || request.getParameter("userId") == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            if (IndividualContainer.getIndividualContainer().getIndividual().searchForHouse(request.getParameter("id"))){
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (HouseNotFindException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
