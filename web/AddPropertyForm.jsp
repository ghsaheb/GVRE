<%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/13/18
  Time: 11:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="Content-Language" content="fa" />
    <title>Title</title>
</head>
<body>
<div style="background-color: #ffd96b;text-align:center;width:500px;margin:auto;border-radius:3px;">
    <form method="get" action="AddHouse.jsp" accept-charset="UTF-8">
        <label>
            <select name="buildingType">
                <option value="ویلایی">ویلایی</option>
                <option value="آپارتمان">آپارتمان</option>
            </select>
        </label>
        <label>
            <select name="dealType">
                <option value="false">خرید</option>
                <option value="true">اجاره</option>
            </select>
        </label>
        <input type="text" name="area" placeholder="متراژ" style="margin: 5px"><br>
        <input type="text" name="price" placeholder="قیمت فروش / اجاره" style="margin: 5px"><br>
        <input type="text" name="address" placeholder="آدرس" style="margin: 5px">
        <input type="text" name="phone" placeholder="شماره تلفن" style="margin: 5px"><br>
        <input type="text" name="description" placeholder="توضیحات" style="margin: 5px">
        <input type="submit" value="اضافه‌کردن خانه‌ی جدید" style="background-color: #f7ba00;color:white;margin:5px;border-radius:3px;">
    </form>
</div>
</body>
</html>
