<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	DataHandler handler = DataHandler.getInstance();
	String name = request.getParameter("Name");
	String category = request.getParameter("Categories");
	Category cat = handler.getCategoryByName(category);
	handler.changeCategory(cat.getId(), name);
	response.sendRedirect("index.jsp");
%>