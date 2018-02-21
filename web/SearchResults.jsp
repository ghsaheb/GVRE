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
        int maxPrice = Integer.MAX_VALUE;
        long area = 0;
        boolean invalidArgs = false;
        try {
            if (!request.getParameter("maxPrice").isEmpty()) {
                maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
            }
        } catch (Exception e) {
            invalidArgs = true;
            request.setAttribute("InvalidMaxPrice","مقدار درستی برای حداکثر قیمت وارد نشده است.");
        }
        try {
            if (!request.getParameter("area").isEmpty()) {
                area = Long.parseLong(request.getParameter("area"));
            }
        } catch (Exception e) {
            request.setAttribute("InvalidArea","مقدار درستی برای متراژ وارد نشده است.");
            invalidArgs = true;
        }
        if(invalidArgs){
    %>
        <jsp:forward page="index.jsp"/>
    <%
        }
        try {
            ArrayList<House> housesList = s.getFiltered(area, Boolean.parseBoolean(request.getParameter("dealType"))
                    ,request.getParameter("buildingType"), maxPrice);
            System.out.println("OOPS: " + housesList.size());
            for (int i = 0; i< housesList.size(); i++){
                House house = housesList.get(i);
                request.setAttribute("house", house);
    %>
    <jsp:include page="HouseItem.jsp"/>
    <%

        }
    }
    catch (Exception e){
    %>
    Exception
    <%
        }
    %>

</div>
</body>
</html>
