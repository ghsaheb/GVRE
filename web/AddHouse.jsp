<%@ page import="java.util.UUID" %>
<%@ page import="main.java.RealEstate.House" %>
<%@ page import="main.java.RealEstate.Price" %>
<%@ page import="main.java.RealEstate.HouseContainer" %>
<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/18/18
  Time: 1:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="utf-8">
</head>
<%
    try {
        String id = UUID.randomUUID().toString();
        int area = Integer.parseInt(request.getParameter("area"));
        String buildingType = request.getParameter("buildingType");
        System.out.println("GHAZAL: " + buildingType);
        String address = request.getParameter("address");
        Boolean dealType = Boolean.parseBoolean(request.getParameter("dealType"));
        String phone = request.getParameter("phone");
        String description = request.getParameter("description");
        if(!dealType) //kharid
        {
            House newHouse = new House(id,area,buildingType,address,dealType,
                    new Price(Integer.parseInt(request.getParameter("price")),0,0),phone,description);
            HouseContainer.getHouseContainer().addNewHouse(newHouse);
        }
        else { //ejare
            House newHouse = new House(id,area,buildingType,address,dealType,
                    new Price(0, 0, Integer.parseInt(request.getParameter("price"))),phone,description);
            HouseContainer.getHouseContainer().addNewHouse(newHouse);
        }
        request.setAttribute("houseAdded","House added successfully");
    } catch (Exception e){
        e.printStackTrace();
    }
%>
<jsp:forward page="index.jsp"/>