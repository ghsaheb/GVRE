<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/14/18
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
متراژ:<%= request.getParameter("area") %><br>
قیمت پایه:<%= request.getParameter("basePrice") %><br>
 <a href="HouseDetails.jsp?id=<%=request.getParameter("id")%>"><input type="submit" value="اطلاعات بیشتر" style="background-color: #2b69cb;color:white;border-radius:3px;"></a>
</div>