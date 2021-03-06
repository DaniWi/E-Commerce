<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% IDataHandler handler = DataHandler.getInstance();
   Integer itemID = Integer.valueOf(request.getParameter("id"));
   Item item = handler.getItemByID(itemID);
   int categoryID = Integer.parseInt(request.getParameter("categoryID"));
   Category category = handler.getCategoryByID(categoryID);
   int userID;
   if(session.getAttribute("ID") != null) {
	   userID = Integer.parseInt(session.getAttribute("ID").toString());
   } else {
	   userID = 0;
   }
   %>
<html>
	<head>
		<title>Webshop 4</title>
		<meta property="og:url"           content="http://10.171.154.227/E-Commerce/item.jsp?id=<%= item.getId() %>&categoryID=<%= item.getCategoryID() %>" />
		<meta property="og:type"          content="website" />
		<meta property="og:title"         content="<%= item.getTitle() %>" />
		<meta property="og:description"   content="<%= item.getDescription() %>" />
		<!-- Bootstrap -->
		<link href="css/bootstrap.min.css" rel="stylesheet">
		
		<!-- Custom styles for this template -->
		<link href="css/sticky-footer-navbar.css" rel="stylesheet">
		
		<!-- Own css for item design etc -->
		<link rel="stylesheet" href="css/styles.css">
	</head>
	<body id="page_item">
		<div id="fb-root"></div>
		<script>(function(d, s, id) {
		  var js, fjs = d.getElementsByTagName(s)[0];
		  if (d.getElementById(id)) return;
		  js = d.createElement(s); js.id = id;
		  js.src = "//connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v2.5";
		  fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));</script>
		
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
					<ul class="nav navbar-nav navbar-right">
						 <% if(session.getAttribute("Username") == null) {%>
						 <li>
							  <a href="login.html" title="Login Page"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Login </a>
						 </li>
						 <%} else {
							 if(session.getAttribute("rights").equals("admin")) {%>
							 	<li>
							  		<a href="admin/" title="Backend"><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span> Backend </a>
							 	</li>
							 	<% } %>
						 <li>
						 	<a href="profile.jsp" title="Profile Page"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <%= session.getAttribute("Username")%> </a>
						 </li>
						 <li>
						 	<a href="logout.jsp" title="Logout"><span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span> Logout </a>
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
			<a type="button" class="btn btn-primary" href="showBasket.jsp">Shopping Basket</a>
			<a type="button" class="btn btn-primary" href="category.jsp?categoryID=<%= categoryID %>">Back to <%= category.getName() %></a>
			<%
			  	String rights = (String) session.getAttribute("rights");
			    if(rights == null) {
			    	rights = "null";
			    } else {
			    	rights = rights.toString();
			    }
		  		if(rights.equals("admin")) {%>
		  		<a type="button" class="btn btn-primary" href="newItem.jsp">New Item</a>
		  		<a type="button" class="btn btn-primary" href="changeItem.jsp?itemID=<%= itemID %>">Change Item</a>
		    <%}%>
		    <%  if(rights.equals("user") || rights.equals("admin")) {%>
		  		<a type="button" class="btn btn-primary" href="#endOfSite" onclick="return newComment()">New Comment</a>
		    <%}%>
			<div class="row item">
			  <div class="col-md-8">
				<h1><%= item.getTitle()%></h1>
				<p>Von <%= handler.getUserByID(item.getAuthorID()).getName()%></p>
				<p class="description"><%= item.getDescription()%></p>
				<p>Price: <%= item.getPrice() %> Euro</p>
				<p class="myinfo">
					Creation-Date: <%= item.getCreationDate().toGMTString() %><br>
					Altertion-Date: <%= item.getAltertionDate().toGMTString() %>
				</p>
				<p>
					<button type="button" class="btn btn-primary" data-toggle="popover" title="Added to the shopping basket" onclick="return addToBasket(<%= item.getId()%>)">Into the shopping basket</button>
					<div class="fb-share-button" data-href="http://10.171.154.227/E-Commerce/item.jsp?id=<%= item.getId() %>&categoryID=<%= item.getCategoryID() %>" data-layout="button"></div>
				</p>
				<div id="allComments">
				<% Collection<Comment> comments = handler.getAllCommentsFromItem(item.getId()); %>
						<% for(Comment comment : comments) { %>
							<div class="comment" id="<%= comment.getId()%>">
								<p><%= comment.getText() %></p>
								<p class="commentinfo">
									Author: <%= handler.getUserByID(comment.getAuthorID()).getName() %><br>
									Creation-Date: <%= comment.getCreationDate().toGMTString() %><br>
									Altertion-Date: <%= comment.getAltertionDate().toGMTString() %><br>
									<% if(rights.equals("admin") || comment.getAuthorID() == userID) { %>
										<a type="button" class="btn btn-primary btn-sm" href="#endOfSite" onclick="return changeComment(<%= comment.getId() %>, '<%= comment.getText() %>')">Change Comment</a>
									<% } %>
							</div>
						<% } %>
				</div>
			  <div id="newComment"></div>
			  </div>			   
			  <a name="endOfSite"></a>
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
				xhttp.open("GET", "resources/divComment.txt", true);
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
				xhttp.send('&comment='+comment+'&id='+'<%= item.getId()%>'+'&authorID='+'<%= userID%>');
			}
			
			function changeComment(commentID, commentText) {
				
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						document.getElementById("newComment").innerHTML = xhttp.responseText;
						document.getElementById("changeCommentID").value = commentID;
						document.getElementById("changeCommentUserID").value = <%= session.getAttribute("ID") %>;
						document.getElementById("changeCommentTextArea").value = commentText;
					}
				}
				xhttp.open("GET", "resources/divChangeComment.txt", true);
				xhttp.send();
			}
			
			function changeCommentDB(commentID, commentText) {
				
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						document.getElementById(commentID).innerHTML = xhttp.responseText;
						document.getElementById("newComment").innerHTML = '';
					}
				}
				var comment = document.getElementById("changeCommentTextArea").value;
				var commentID = document.getElementById("changeCommentID").value;
				var userID = document.getElementById("changeCommentUserID").value;
				
				
				xhttp.open("POST", "changeComment.jsp", true);
				xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xhttp.send('&comment='+comment+'&id='+commentID+'&authorID='+userID+'&itemID='+<%= itemID %>);
			}
			
			function addToBasket(itemID) {
				
				var xhttp = new XMLHttpRequest();
				xhttp.open("POST", "addItemBasket.jsp", true);
				xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xhttp.send('&id='+itemID);
			}
			
			$(function () { $("[data-toggle = 'popover']").popover(); });
		</script>
	</body>
</html>