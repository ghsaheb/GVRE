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
<%
    ArrayList<House> temp = s.getFiltered();
    for (int i=0;i<temp.size();i++){
%>
<jsp:include page="HouseItem.jsp" flush="true">
    <jsp:param name="area" value="<%=temp.get(i).getArea()%>" />
    <jsp:param name="basePrice" value="<%=temp.get(i).getBasePrice()%>" />
</jsp:include>
<%
    }
%>
</body>
</html>
