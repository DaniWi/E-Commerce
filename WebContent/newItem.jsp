<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<body id="page_login">
		<!-- Fixed navbar -->
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand">Webshop 4</a>
				</div>
			</div>
		</nav>
	
		<!-- Begin page content -->
		<div class="container">
			<a type="button" class="btn btn-primary" href="index.jsp">Home</a>
			<form class="form-signin" action="newItemDB.jsp" method="POST">
			  <h2 class="form-signin-heading">New Item</h2>
			  <label for="categories">Category: </label>
			  <select class="form-control" id="categories" name = "Category">
			  	<% 
			  		DataHandler handler = DataHandler.getInstance();
			  		Collection<Category> cat = handler.getAllCategories();
			  		for(Category category : cat) {%>
				<option><%= category.getName() %></option>
			  	<% } %>
			  </select>
			  <label for="inputTitle" class="sr-only">Title</label>
			  <input type="text" name="Title" id="inputTitle" class="form-control" placeholder="Title" required autofocus>
			  <label for="inputDescription" class="sr-only">Description</label>
			  <textarea class="form-control" rows="5" id="description" name="Description" placeholder="Description"></textarea>
			  <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
			</div>
			</form>

		</div> <!-- /container -->

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