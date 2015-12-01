<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	IDataHandler handler = DataHandler.getInstance();
	int id = Integer.parseInt(request.getParameter("ID"));
	String title = request.getParameter("Title");
	String description = request.getParameter("Description");
	String category = request.getParameter("Category");
	Double price = Double.valueOf(request.getParameter("Price"));
	Category cat = handler.getCategoryByName(category);
	int userID = Integer.parseInt(session.getAttribute("ID").toString());
	handler.changeItem(id, title, description, price, userID, cat.getId());
	response.sendRedirect("index.jsp");
%>