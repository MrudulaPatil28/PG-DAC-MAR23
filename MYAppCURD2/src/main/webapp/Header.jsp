<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String userheader=request.getHeader("user-agent");
%>
<h2>user name: <%= userheader %></h2>
<%
String userlang=request.getHeader("accept-language");
Date dt = new Date();
%>
<h2>user language: <%= userlang %></h2>
<h2>Date now: <%= dt %></h2>
<%
response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires", 0);
//response.setHeader("refresh","1");
%>
</body>
</html>
