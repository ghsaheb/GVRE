<%@ page import="main.java.RealEstate.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Ghazal</title>
  </head>
  <body style="direction: rtl">
  <%--<% House s = new House(10); %>--%>
  <%--<%= s.id %>--%>
  <jsp:include page="UserNameCredit.jsp"/>
  <jsp:include page="SearchForm.jsp"/>
  <jsp:include page="AddPropertyForm.jsp"/>
  <jsp:include page="AddCreditForm.jsp"/>

  </body>
</html>