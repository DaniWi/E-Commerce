<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% DataHandler handler = new DataHandler(); 
   Integer itemID = Integer.valueOf(request.getParameter("id"));
   Item item = handler.getItemByID(itemID);
   String category = request.getParameter("category");
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
	<body id="page_item">
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
			<a type="button" class="btn btn-primary" href="category.jsp?category=<%= category %>">Back to <%= category %></a>
			<% String ID;
			if(session.getAttribute("rights") != null) {
			%>
			<a type="button" class="btn btn-primary" href="#" onclick="return newComment()">New Comment</a>
			<%
			ID = session.getAttribute("ID").toString();
			} else { ID = ""; } 
			%>
			<div class="row item">
			  <div class="col-md-8">
				<h1><%= item.getTitle()%></h1>
				<p>Von <%= handler.getUserByID(item.getAuthorID()).getName()%></p>
				<p class="description"><%= item.getDescription()%></p>
				<p class="myinfo">
					Creation-Date: <%= item.getCreationDate().toGMTString() %><br>
					Altertion-Date: <%= item.getAltertionDate().toGMTString() %>
				</p>
				<div id="allComments">
				<% Collection<Comment> comments = handler.getAllCommentsFromItem(item.getId()); %>
						<% for(Comment comment : comments) { %>
							<div class="comment">
								<p><%= comment.getText() %></p>
								<p class="commentinfo">
									Author: <%= handler.getUserByID(comment.getAuthorID()).getName() %><br>
									Creation-Date: <%= comment.getCreationDate().toGMTString() %><br>
									Altertion-Date: <%= comment.getAltertionDate().toGMTString() %>
							</div>
						<% } %>
				</div>
			  <div id="newComment"></div>
			  </div>			   
			</div>
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
		<script type="text/javascript">
			function newComment() {
				
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						document.getElementById("newComment").innerHTML = xhttp.responseText;
					}
				}
				xhttp.open("GET", "divComment.txt", true);
				xhttp.send();
			}
			
			function saveComment() {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						document.getElementById("allComments").innerHTML += xhttp.responseText;
						document.getElementById("newComment").innerHTML = '';
					}
				}
				var comment = document.getElementById("commentTextArea").value;
				
				
				xhttp.open("POST", "newComment.jsp", true);
				xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xhttp.send('&comment='+comment+'&id='+'<%= item.getId()%>'+'&authorID='+'<%= ID%>');
			}
		</script>
	</body>
</html>