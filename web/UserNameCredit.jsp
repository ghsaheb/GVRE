<%@ page import="main.java.RealEstate.Individual" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="height: 70px;
    text-align: center;
    width: 500px;
    margin: auto;">
    <%
        Individual temp = new Individual("بهنام همایون", "09123456789", 0, "behnamgoolakh", "ILoveBehnamBani");
    %>
    <p style="direction: rtl;float: right">نام کاربری: <%=temp.getName()%></p>
    <p style="direction: rtl;float: left">اعتبار شما: <%=temp.getBalance()%></p>
</div>
