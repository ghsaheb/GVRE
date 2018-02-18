<%@ page import="main.java.RealEstate.Individual" %>
<%@ page import="main.java.RealEstate.IndividualContainer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="height: 70px;
    text-align: center;
    width: 500px;
    margin: auto;">
    <%
        IndividualContainer ic = IndividualContainer.getIndividualContainer();
        Individual user = ic.getIndividual();
    %>
    <p style="direction: rtl;float: right">نام کاربری: <%=user.getName()%></p>
    <p style="direction: rtl;float: left">اعتبار شما: <%=user.getBalance()%></p>
</div>
