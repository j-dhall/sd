<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="link.html" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Details</title>
</head>
<body>
<%@ page import="com.sd.mvc.model.Employee" %>
<% Employee emp = (Employee)request.getAttribute("emp");
	out.print("Id: " + emp.getId() + "\nName: " + emp.getFirstName() + " " + emp.getLastName());
%>
</body>
</html>