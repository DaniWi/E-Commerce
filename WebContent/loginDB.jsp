<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	DataHandler handler = new DataHandler();
	User user = handler.getUserLogin(request.getParameter("Username"), request.getParameter("Password"));
	if(user == null) {
		response.sendRedirect("login.html");
	} else {
		session.setAttribute("Username", user.getName());
		session.setAttribute("ID", user.getId());
		session.setAttribute("rights", user.getRights());
		response.sendRedirect("index.jsp");
	}
%>