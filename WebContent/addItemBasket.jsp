<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%
	Integer id = Integer.parseInt(request.getParameter("id"));
	HashMap<Integer, Integer> basket;
	if(session.getAttribute("basket") == null) {
		basket = new HashMap<Integer, Integer>();
	} else {
		basket = (HashMap)session.getAttribute("basket");
	}
	if(basket.containsKey(id)) {
		int current = basket.get(id);
		current++;
		basket.replace(id, current);
	} else {
		basket.put(id, 1);
	}
	session.setAttribute("basket", basket);
%>