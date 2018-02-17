<%@ page import="main.java.RealEstate.House" %><%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/14/18
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
 <%
  House s = (House) request.getAttribute("house");
  if (!s.isDealType()){
 %>
 قیمت: <%= s.getPrice().getSellPrice() %> تومان<br>

 <%
 }
 else {
 %>
 قیمت پایه: <%= s.getPrice().getBasePrice() %> تومان<br>
 مبلغ اجاره: <%= s.getPrice().getRentPrice() %> تومان<br>
 <%
  }
 %>
 متراژ: <%= s.getArea() %><br>
 <%
  if(!s.isDealType()){
 %>
 نوع: فروش<br>
 <%
 }
 else {
 %>
 نوع: رهن و اجاره<br>

 <%
  }
 %>


 <a href="<%= s.getImageURL() %>" style="text-decoration: none;">لینک عکس</a><br>
 <a href="HouseDetails.jsp?id=<%=s.getId()%>"><input type="submit" value="اطلاعات بیشتر" style="background-color: #2b69cb;color:white;border-radius:3px;"></a>
</div>