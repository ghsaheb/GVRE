<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="main.java.RealEstate.IndividualContainer" %><%--
  Created by IntelliJ IDEA.
  User: ghazals
  Date: 2/17/18
  Time: 8:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    try {
        String url = "http://acm.ut.ac.ir/ieBank/pay";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("apiKey","4e4d47e0-13c6-11e8-87b4-496f79ef1988");
        con.setRequestProperty("Content-Type","application/json");

        JSONObject data = new JSONObject();
        data.put("userId",1);
        data.put("value",Integer.parseInt(request.getParameter("credit")));
        OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
        wr.write(data.toString());
        wr.flush ();
        wr.close ();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        if(responseCode == 200) {
            IndividualContainer.getIndividualContainer().getIndividual().increaseBalance(Integer.parseInt(request.getParameter("credit")));
            request.setAttribute("creditResult", true);
        }
        else {
                request.setAttribute("creditResult", false);
        }
    } catch (Exception e){
        e.printStackTrace();
    }
%>
<jsp:forward page="index.jsp"/>