<%@ page import="main.java.RealEstate.HouseContainer" %>
<%@ page import="main.java.RealEstate.House" %>
<%@ page import="main.java.RealEstate.HouseNotFindException" %><%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/14/18
  Time: 5:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HouseDetails</title>
</head>
<body>
<jsp:include page="UserNameCredit.jsp"/>
<div style="direction: rtl">
<%
    HouseContainer houses = HouseContainer.getHouseContainer();
    House foundedHouse;
    try {
        foundedHouse = houses.findHouse(request.getParameter("id"));
%>
نوع ساختمان: <%= foundedHouse.getBuildingType()%><br>
متراژ: <%= foundedHouse.getArea()%> متر <br>
<%
    if (!foundedHouse.isDealType()){
        %>
نوع قرارداد: فروش<br>
<%
    }
    else {
%>
نوع قرارداد: رهن و اجاره<br>
<%
    }
%>

آدرس: <%= foundedHouse.getAddress()%><br>
    <a href="<%= foundedHouse.getImageURL()%>" style="text-decoration: none;">لینک عکس</a><br>
توضیحات: <%= foundedHouse.getDescription()%><br>
<%
    }

    catch (HouseNotFindException e) {
%>
    <h1>House not Found</h1>
<%
    }
%>
</div>
</body>
</html>
