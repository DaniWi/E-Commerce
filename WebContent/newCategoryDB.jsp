<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	DataHandler handler = new DataHandler();
	handler.createCategory(request.getParameter("Name"));
	response.sendRedirect("index.jsp");
%>