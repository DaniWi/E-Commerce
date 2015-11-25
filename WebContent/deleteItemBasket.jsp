<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%
	Integer id = Integer.parseInt(request.getParameter("id"));
	HashMap<Integer,Integer> basket;
	if(session.getAttribute("basket") != null) {
		basket = (HashMap)session.getAttribute("basket");
		basket.remove(id);
		session.setAttribute("basket", basket);
	}
%>