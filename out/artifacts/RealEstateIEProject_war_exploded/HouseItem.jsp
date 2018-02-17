<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/14/18
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
 <%
  if (request.getAttribute("sellPrice") != null){
      %>
 قیمت: <%= request.getAttribute("sellPrice") %> تومان<br>

 <%
  }
  else {
      %>
 قیمت پایه: <%= request.getAttribute("basePrice") %> تومان<br>
 مبلغ اجاره: <%= request.getAttribute("rentPrice") %> تومان<br>
 <%
  }
 %>
 متراژ: <%= request.getAttribute("area") %><br>
 نوع: <%= request.getAttribute("dealType") %><br>

 <a href="<%= request.getAttribute("imageURL") %>" style="text-decoration: none;">لینک عکس</a><br>
 <a href="HouseDetails.jsp?id=<%=request.getAttribute("id")%>"><input type="submit" value="اطلاعات بیشتر" style="background-color: #2b69cb;color:white;border-radius:3px;"></a>
</div>