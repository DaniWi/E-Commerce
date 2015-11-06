<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% DataHandler handler = new DataHandler(); %>
<html>
	<head>
		<!-- Bootstrap -->
		<link href="css/bootstrap.min.css" rel="stylesheet">
		
		<!-- Custom styles for this template -->
		<link href="css/sticky-footer-navbar.css" rel="stylesheet">
		
		<!-- Own css for item design etc -->
		<link rel="stylesheet" href="css/styles.css">
	</head>
	<body>
		<!-- Fixed navbar -->
		<nav class="navbar navbar-default navbar-fixed-top">
		  <div class="container">
			<div class="navbar-header">
			  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			  </button>
			  <a class="navbar-brand">Webshop 4</a>
			</div>
			<!-- 
			<div id="navbar" class="collapse navbar-collapse">
			  <ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#about">About</a></li>
				<li><a href="#contact">Contact</a></li>
			  </ul>
			</div>
			-->
		  </div>
		</nav>
	
		<!-- Begin page content -->
		<div class="container">
		  <a type="button" class="btn btn-primary" href="index.html">Back Home</a>
		  <div class="page-header">
			<h2>Books</h2>
		  </div>
		    <%
		    Collection<Item> items = handler.getAllItemsFromCategory("Werkzeug");
		    for(Item item : items){ %>
			<div class="row item">
			  <div class="col-md-8">
				<h1>
				<%= item.getTitle()%>
				</h1>
				<p>von <%= handler.getUserByID(item.getAuthorID()).getName()%></p>
				<p class="myinfo"><%= item.getCreationDate().toGMTString() %></p>
				<div class="comment">
					<h2>Spitzen Buch!</h2>
					<p>Hier k&ouml;nnte jetzt ein langer Text stehen der beschreit wie suuuuuuuuper dieses Buch ist.</p>
					<p class="myinfo">Happy Customer - 17/11/2015</p>
				</div>
			  </div>
			  <div class="col-md-4">
			   <img src="mysql.jpg" class="img-thumbnail" alt="MySQL" width="304" height="236"> 
			  </div>
			</div>
			<%} %>
			<div class="row item">
			  <div class="col-md-8">
				<h1>Die kleine Raupe Nimmersatt</h1>
				<p>Von Eric Carle</p>
			  </div>
			  <div class="col-md-4">
			   <img src="raupe.jpg" class="img-thumbnail" alt="Die kleine Raupe Nimmersatt"  width="304" height="236"> 
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
	</body>
</html>