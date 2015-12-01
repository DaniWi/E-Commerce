<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	IDataHandler handler = DataHandler.getInstance();
	String title = request.getParameter("Title");
	String category = request.getParameter("Category");
	Category cat = handler.getCategoryByName(category);
	String description = request.getParameter("Description");
	Double price = Double.valueOf(request.getParameter("Price"));
	int userID = Integer.parseInt(session.getAttribute("ID").toString());
	handler.createItem(title, description, price, userID, cat.getId());
	response.sendRedirect("index.jsp");
%>