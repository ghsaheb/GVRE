<%@ page import="main.java.RealEstate.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Ghazal</title>
  </head>
  <body style="direction: rtl">
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

    }
  %>
  <jsp:include page="UserNameCredit.jsp"/>
  <jsp:include page="SearchForm.jsp"/>
  <jsp:include page="AddPropertyForm.jsp"/>
  <jsp:include page="AddCreditForm.jsp"/>

  </body>
</html>