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
<%
    HouseContainer houses = HouseContainer.getHouseContainer();
    House foundedHouse;
    try {
        foundedHouse = houses.findHouse(request.getParameter("id"));
%>
<%=
    foundedHouse.getArea()
%>
<%
    }

    catch (HouseNotFindException e) {
%>
    <h1>House not Found</h1>
<%
    }
%>

</body>
</html>
