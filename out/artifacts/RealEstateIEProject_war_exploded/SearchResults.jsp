<%@ page import="main.java.RealEstate.HouseContainer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.RealEstate.House" %>
<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/14/18
  Time: 3:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results</title>
</head>
<body>
<%
    HouseContainer s = HouseContainer.getHouseContainer();
    try {
        s.getHouses();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<jsp:include page="UserNameCredit.jsp"/>
<div style="display: flex;flex-wrap: wrap;justify-content: space-between;width: 90%;margin: auto;text-align: center;">
<%
    ArrayList<House> housesList = s.getFiltered();
    for (int i = 0; i< housesList.size(); i++){
        House house = housesList.get(i);
        request.setAttribute("area", house.getArea());
        if (!house.isDealType()) {
            request.setAttribute("dealType", "فروش");
            request.setAttribute("sellPrice", house.getPrice().getSellPrice());
        }
        else {
            request.setAttribute("dealType", "رهن و اجاره");
            request.setAttribute("rentPrice", house.getPrice().getRentPrice());
            request.setAttribute("basePrice", house.getPrice().getBasePrice());
        }
        request.setAttribute("imageURL", housesList.get(i).getImageURL());
        request.setAttribute("id", housesList.get(i).getId());
%>
<jsp:include page="HouseItem.jsp"/>
<%
    }
%>
</div>
</body>
</html>
