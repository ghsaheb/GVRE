<%@ page import="RealEstatePackage.*" %>
<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/18/18
  Time: 6:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(IndividualContainer.getIndividualContainer().getIndividual().getCredit() >= 1000){
        IndividualContainer.getIndividualContainer().getIndividual().decreaseCredit(1000);
        try {
            House house = House.findHouse(request.getParameter("id"));
            IndividualContainer.getIndividualContainer().getIndividual().addPaidHouse(house);
            request.setAttribute("addcredit", "successfully added");

        }
        catch (HouseNotFindException e){
            request.setAttribute("addcredit", "house not found");
        }

    }
    else {
        request.setAttribute("addcredit", "not enough credit");
    }
%>

<jsp:forward page="HouseDetails.jsp"/>

