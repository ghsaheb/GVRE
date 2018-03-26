<%@ page import="RealEstatePackage.House" %>
<%@ page import="RealEstatePackage.HouseNotFindException" %>
<%@ page import="RealEstatePackage.IndividualContainer" %>
<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/14/18
  Time: 5:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Khaneh Be Doosh | HouseDetails</title>
</head>
<body>
<jsp:include page="UserNameCredit.jsp"/>
<div style="direction: rtl">
<%
    if (request.getAttribute("addcredit") != null){
    if (request.getAttribute("addcredit").equals("successfully added")){
%>

    <h3 style="background-color: #339966;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;">دریافت شماره تلفن به خوبی انجام شد</h3>

    <%
    }
    else if (request.getAttribute("addcredit").equals("not enough credit")){
%>
    <h3 style="background-color: #991d1c;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;">موجودی کافی نیست</h3>
    <%
    }
        }
    House foundedHouse;
    try {
        foundedHouse = House.findHouse(request.getParameter("id"));
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
توضیحات: <%= foundedHouse.getDescription()%><br>
    <%
        if (!IndividualContainer.getIndividualContainer().getIndividual().searchForHouse(foundedHouse)){
    %>
<a href="CheckCredit.jsp?id=<%=foundedHouse.getId()%>"><button type="button">دریافت شماره مالک / مشاور </button></a>

    <%
        }
        else {
            %>
    <h3><%=foundedHouse.getPhone()%> شماره تلفن: </h3>
    <%
        }
    %>
    <%

        if (foundedHouse.getImageURL() == null){
    %>
    <img src="static/images/no-pic.jpg" width="200px" height="200px">
    <%
            }
            else {
            %>
    <a href="<%= foundedHouse.getImageURL()%>" style="text-decoration: none;">لینک عکس</a><br>
    <%
            }
    }

    catch (HouseNotFindException e) {
    %>
    <h1>خانه موردنظر یافت نشد. </h1>
    <%
        }
    %>
    </div>
    </body>
    </html>
