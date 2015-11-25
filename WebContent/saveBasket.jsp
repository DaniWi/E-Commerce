<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%
	Integer id = Integer.parseInt(request.getParameter("itemID"));
	Integer amount = Integer.parseInt(request.getParameter("amount"));
	HashMap<Integer, Integer> basket;
	if(session.getAttribute("basket") != null) {
		basket = (HashMap)session.getAttribute("basket");
		basket.replace(id, amount);
		session.setAttribute("basket", basket);
	}
%>