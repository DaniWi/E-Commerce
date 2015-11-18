<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% DataHandler handler = DataHandler.getInstance(); 
   int categoryID = Integer.parseInt(request.getParameter("categoryID"));
   Category category = handler.getCategoryByID(categoryID);
   Collection<Item> items = handler.getAllItemsFromCategory(categoryID);
   %>
<html>
	<head>
		<title>Webshop 4</title>
		<!-- Bootstrap -->
		<link href="css/bootstrap.min.css" rel="stylesheet">
		
		<!-- Custom styles for this template -->
		<link href="css/sticky-footer-navbar.css" rel="stylesheet">
		
		<!-- Own css for item design etc -->
		<link rel="stylesheet" href="css/styles.css">
	</head>
	<body id="page_category">
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container">
			  <!-- Brand and toggle get grouped for better mobile display -->
			  <div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						 <span class="sr-only">Toggle navigation</span>
						 <span class="icon-bar"></span>
						 <span class="icon-bar"></span>
						 <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/E-Commerce/">Webshop 4</a>
			  </div>

			  <!-- Collect the nav links, forms, and other content for toggling -->
			  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<!--
					<ul class="nav navbar-nav">
						 <li>
							  <a href="http://orf.at" title="ORF">Test Link (orf.at)</a>
						 </li>
					</ul>
					-->
					<ul class="nav navbar-nav navbar-right">
						 <% if(session.getAttribute("Username") == null) {%>
						 <li>
							  <a href="login.html" title="Login Page"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Login </a>
						 </li>
						 <%} else {%>
						 <li>
							  <a href="logout.jsp" title="Logout Page"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Logout - User: <%= session.getAttribute("Username")%> </a>
						 </li>
						<% } %>
					</ul>
			  </div>
			  <!-- /.navbar-collapse -->
			</div>
			<!-- /.container -->
		</nav>
	
		<!-- Begin page content -->
		<div class="container">
			<a type="button" class="btn btn-primary" href="index.jsp">Back Home</a>
			<%
			  	String rights = (String) session.getAttribute("rights");
			    if(rights == null) {
			    	rights = "null";
			    } else {
			    	rights = rights.toString();
			    }
		  		if(rights.equals("admin")) {%>
		  		<a type="button" class="btn btn-primary" href="newCategory.html">New Category</a>
		  		<a type="button" class="btn btn-primary" href="changeCategory.jsp">Change Category</a>
		  		<a type="button" class="btn btn-primary" href="newItem.jsp">New Item</a>
		    <%}%>
			<div class="page-header">
				<h2><%= category.getName() %></h2>
			</div>
			<% for(Item item : items){ %>
				<div class="row item">
					<div class="col-md-8">
						<h1><a href="item.jsp?id=<%= item.getId() %>&categoryID=<%= categoryID %>"><%= item.getTitle()%></a></h1>
						<p>Von <%= handler.getUserByID(item.getAuthorID()).getName()%></p>
						<p class="myinfo"><%= item.getCreationDate().toGMTString() %></p>
						<% Collection<Comment> comments = handler.getAllCommentsFromItem(item.getId()); %>
						<% 
							int i = 0;
							for(Comment comment : comments) { 
								if(i == 2) {
									break;
								}
								i++;
						%>
							<div class="comment">
								<p><%= comment.getText() %></p>
								<p class="commentinfo"><%= handler.getUserByID(comment.getAuthorID()).getName() %> - <%= comment.getCreationDate().toGMTString() %></p>
							</div>
						<% } %>
					</div>
				</div>
			<% } %>
		</div>

		<footer class="footer">
			<div class="container">
				<p class="text-muted">&copy; 2015 Nocker/Witsch. All rights reserved.</p>
			</div>
		</footer>
		
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>