<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	DataHandler handler = new DataHandler();
	int itemID = Integer.valueOf(request.getParameter("id"));
	int authorID = Integer.valueOf(request.getParameter("authorID"));
	handler.createComment(itemID, request.getParameter("comment"), authorID);
%>
<div class="comment">
	<p><%= request.getParameter("comment") %></p>
	<p class="commentinfo">
		Author: <%= handler.getUserByID(authorID).getName() %><br>
		Creation-Date: <%= new Date().toGMTString() %><br>
		Altertion-Date: <%= new Date().toGMTString() %>
	</p>
</div>