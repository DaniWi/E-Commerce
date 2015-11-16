<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	DataHandler handler = new DataHandler();
	String title = request.getParameter("Title");
	String category = request.getParameter("Category");
	Category cat = handler.getCategoryByName(category);
	String description = request.getParameter("Description");
	int userID = Integer.parseInt(session.getAttribute("ID").toString());
	handler.createItem(title, description, userID, cat.getId());
	response.sendRedirect("index.jsp");
%>