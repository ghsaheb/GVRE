<%@ page import="main.java.RealEstate.House" %>
<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/14/18
  Time: 3:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <%
        House house = (House) request.getAttribute("house");
        if (house == null){
    %>
    <h3>خانه موردنظر یافت نشد. </h3>
    <%

    }
    else {
        if (!house.isDealType()){
    %>
    قیمت: <%= house.getPrice().getSellPrice() %> تومان<br>

    <%
    }
    else {
    %>
    قیمت پایه: <%= house.getPrice().getBasePrice() %> تومان<br>
    مبلغ اجاره: <%= house.getPrice().getRentPrice() %> تومان<br>
    <%
        }
    %>
    متراژ: <%= house.getArea() %><br>
    <%
        if(!house.isDealType()){
    %>
    نوع: فروش<br>
    <%
    }
    else {
    %>
    نوع: رهن و اجاره<br>

    <%
        }
        if (house.getImageURL() != null){
    %>


    <a href="<%= house.getImageURL() %>" style="text-decoration: none;">لینک عکس</a><br>
    <%
        }
    %>
    <a href="HouseDetails.jsp?id=<%=house.getId()%>"><input type="submit" value="اطلاعات بیشتر" style="background-color: #2b69cb;color:white;border-radius:3px;"></a>

    <%
        }
    %>
</div>