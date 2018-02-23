<%@ page import="java.util.UUID" %>
<%@ page import="RealEstate.*" %>
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
        if (!(buildingType.equals("ویلایی") || buildingType.equals("آپارتمان"))){
            throw new InvalidHouseParameterException();
        }
        String address = request.getParameter("address");
        if (address.length() == 0){
            throw new InvalidHouseParameterException();
        }
        Boolean dealType = Boolean.parseBoolean(request.getParameter("dealType"));
        String phone = request.getParameter("phone");
        if (phone.length() < 4 || phone.length() > 20 || !(phone.matches("[0-9]+"))){
            throw new InvalidHouseParameterException();
        }
        String description = request.getParameter("description");
        if(!dealType) //kharid
        {
            House newHouse = new House(id,area,buildingType,address,dealType,
                    new Price(0, 0, Integer.parseInt(request.getParameter("price"))),phone,description);
            HouseContainer.getHouseContainer().addNewHouse(newHouse);
            IndividualContainer.getIndividualContainer().getIndividual().addHouse(newHouse);
        }
        else { //ejare
            House newHouse = new House(id,area,buildingType,address,dealType,
                    new Price(0, Integer.parseInt(request.getParameter("price")), 0),phone,description);
            HouseContainer.getHouseContainer().addNewHouse(newHouse);
            IndividualContainer.getIndividualContainer().getIndividual().addHouse(newHouse);
        }
        request.setAttribute("houseAdded","House added successfully");
    } catch (Exception e){
        request.setAttribute("InvalidHouseParameter","مقادیر فیلد‌های خانه به درستی وارد نشده است.");
    }
%>
<jsp:forward page="index.jsp"/>