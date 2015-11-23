<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	IDataHandler handler = DataHandler.getInstance();
	handler.createCategory(request.getParameter("Name"));
	response.sendRedirect("index.jsp");
%>