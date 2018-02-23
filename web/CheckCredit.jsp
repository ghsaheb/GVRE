<%@ page import="RealEstate.*" %>
<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/18/18
  Time: 6:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(IndividualContainer.getIndividualContainer().getIndividual().getBalance() >= 1000){
        IndividualContainer.getIndividualContainer().getIndividual().decreaseBalance(1000);
        try {
            House house = HouseContainer.getHouseContainer().findHouse(request.getParameter("id"));
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

