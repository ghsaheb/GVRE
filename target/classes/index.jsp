<%@ page import="RealEstate.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Khaneh Be Doosh | GVRE</title>
    <meta charset="UTF-8">

</head>
<body style="direction: rtl">
<jsp:include page="UserNameCredit.jsp"/>
<%
    if (request.getAttribute("creditResult") == (Boolean)true){
%>
<h3 style="background-color: #339966;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;">افزایش اعتبار به زیبایی انجام شد</h3>
<%
}
else if (request.getAttribute("creditResult") == (Boolean)false){
%>
<h3 style="background-color: #991d1c;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;">افزایش اعتبار به دیوار برخورد کرد</h3>
<%
}if (request.getAttribute("InvalidMaxPrice") != null){
%>
<h3 style="background-color: #991d1c;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;"><%=request.getAttribute("InvalidMaxPrice")%></h3>
<%
}if (request.getAttribute("InvalidArea") != null){
%>
<h3 style="background-color: #991d1c;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;"><%=request.getAttribute("InvalidArea")%></h3>
<%
    }if (request.getAttribute("InvalidCredit") != null){
%>
<h3 style="background-color: #991d1c;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;"><%=request.getAttribute("InvalidCredit")%></h3>
<%
    }if (request.getAttribute("InvalidHouseParameter") != null){
%>
<h3 style="background-color: #991d1c;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;"><%=request.getAttribute("InvalidHouseParameter")%></h3>
<%
    }if (request.getAttribute("houseAdded") != null) {
%>
<h3 style="background-color: #339966;color: white;text-align:center;border-radius:3px;width:500px;margin:auto;height:30px;">
    <%=request.getAttribute("houseAdded")%>
</h3>
<%
    }
%>
<jsp:include page="SearchForm.jsp"/>
<jsp:include page="AddPropertyForm.jsp"/>
<jsp:include page="AddCreditForm.jsp"/>

</body>
</html>