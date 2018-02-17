<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/13/18
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="background-color:#a5cded;text-align:center;width:500px;margin: auto;border-radius:3px;">
    <form method="get" action="SearchResults.jsp">
        <input type="text" name="area" placeholder="حداقل متراژ" style="margin: 5px">
        <input type="text" name="buildingType" placeholder="نوع ملک" style="margin: 5px"><br>
        <input type="text" name="dealType" placeholder="نوع قرارداد (خرید/اجاره)" style="margin: 5px">
        <input type="text" name="maxPrice" placeholder="حداکثر قیمت" style="margin: 5px"><br>
        <input type="submit" value="جست‌وجو" style="background-color: #2b69cb;color:white;border-radius:3px;">
    </form>
</div>
</body>
</html>
