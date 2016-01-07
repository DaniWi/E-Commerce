<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	IDataHandler handler = DataHandler.getInstance();
	if(request.getParameter("Facebook").equals("no")){
		User user = handler.getUserLogin(request.getParameter("Username"), request.getParameter("Password"));
		if(user == null) {
			response.sendRedirect("login.html");
		} else {
			session.setAttribute("Username", user.getName());
			session.setAttribute("ID", user.getId());
			session.setAttribute("rights", user.getRights());
			response.sendRedirect("index.jsp");
		}	
	} else {
		User user = handler.getUserLogin(request.getParameter("Username"), request.getParameter("id"));
		if(user == null){
			user = handler.createUser(request.getParameter("Username"), request.getParameter("email"), request.getParameter("id"), "user");
		}
		
		session.setAttribute("Username", user.getName());
		session.setAttribute("rights", user.getRights());
		session.setAttribute("ID", user.getId());
	}

%>