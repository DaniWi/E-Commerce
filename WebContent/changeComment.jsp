<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<%  
	DataHandler handler = new DataHandler();
	int commentID = Integer.valueOf(request.getParameter("id"));
	int authorID = Integer.valueOf(request.getParameter("authorID"));
	int itemID = Integer.valueOf(request.getParameter("itemID"));
	String text = request.getParameter("comment");
	handler.changeComment(commentID, itemID, text, authorID);
%>
<p><%= text %></p>
<p class="commentinfo" id="<%= commentID %>">
	Author: <%= handler.getUserByID(authorID).getName() %><br>
	Creation-Date: <%= handler.getCommentByID(commentID).getCreationDate().toGMTString() %><br>
	Altertion-Date: <%= handler.getCommentByID(commentID).getAltertionDate().toGMTString() %><br>
	<a type="button" class="btn btn-primary btn-sm" href="#endOfSite" onclick="return changeComment(<%= commentID %>, '<%= text %>')">Change Comment</a>
</p>