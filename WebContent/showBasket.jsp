<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 	IDataHandler handler = DataHandler.getInstance(); 
	List<Item> basket = new LinkedList<>();
	HashMap<Integer, Integer> basketSession = null;
	if(session.getAttribute("basket") != null) {
		basketSession = (HashMap)session.getAttribute("basket");
		for(Integer i : basketSession.keySet()){
			basket.add(handler.getItemByID(i));
		}
	}
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
					<ul class="nav navbar-nav navbar-right">
						 <% if(session.getAttribute("Username") == null) {%>
						 <li>
							  <a href="login.html" title="Login Page"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Login </a>
						 </li>
						 <%} else {
							 if(session.getAttribute("rights").equals("admin")) {%>
							 	<li>
							  		<a href="../admin/index.html" title="Backend"><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span> Backend </a>
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
			<div class="page-header">
				<h2>Shopping Basket</h2>
			</div>
			<%
				if(basket.size() == 0){
			%>
			No items in the shopping basket!	
			<%  }
				double totalPrice = 0;
				for(Item item : basket){ totalPrice += basketSession.get(item.getId())*item.getPrice(); %>
				<div class="row item" id="item<%= item.getId() %>">
					<div class="col-md-8">
						<h1><a href="item.jsp?id=<%= item.getId()%>&categoryID=<%= item.getCategoryID() %>"><%= item.getTitle()%></a></h1>
						<form class="form-inline">
							<p>
								<label>Amount</label>
	    						<input type="number" id="inputAmount<%= item.getId()%>" onchange="return saveBasket(<%= item.getId()%>,<%= item.getPrice()%>)" class="form-control" min="1" value="<%= basketSession.get(item.getId())%>">
	    						<input type="hidden" id="hiddenAmount<%= item.getId()%>" value=<%= basketSession.get(item.getId())%> />
	    					</p>
							<p>
								<button type="button" class="btn btn-primary" onclick="return deleteItem(<%= item.getId()%>,<%= item.getPrice()%>)">Delete</button>
								<!-- <button type="button" class="btn btn-primary" data-toggle="popover" title="Saved" onclick="return saveBasket(<%= item.getId()%>)">Save</button>  -->
							</p>
						</form>
					</div>
				</div>
			<% } 
			if(basket.size() != 0){
				totalPrice = Math.round(totalPrice * 100.0) / 100.0;
			%>
				<div class="row totalprice">
					<div class="col-md-8">
						<p>
							<label>Total Price: </label> <span id="totalPrice"><%= totalPrice%></span> Euro
						</p>
						<p>
							<a type="button" class="btn btn-primary" id="purchaseButton" href="paypal.jsp?euro=<%= totalPrice%>">Purchase Basket</a>
						</p>
					</div>
				<div>
				
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
		<script type="text/javascript">
			function deleteItem(itemID, price) {
				
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						var elementAmount = "hiddenAmount" + parseInt(itemID);
						var amount = document.getElementById(elementAmount).value;
						var element = "item" + parseInt(itemID);
						document.getElementById(element).remove();
						var elem = document.getElementById("totalPrice");
						var total = elem.innerHTML;
						total = total - amount*price;
						elem.innerHTML = Math.round(total * 100.0) / 100.0;
					}
				}
				xhttp.open("POST", "deleteItemBasket.jsp", true);
				xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xhttp.send('&id='+itemID);
			}
			
			function saveBasket(itemID, price) {
				
				var xhttp = new XMLHttpRequest();
				xhttp.open("POST", "saveBasket.jsp", true);
				xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				var element = "inputAmount" + parseInt(itemID);
				var amount = document.getElementById(element).value;
				xhttp.send('&itemID='+itemID+'&amount='+ amount);
				
				var elem = document.getElementById("totalPrice");
				var total = parseFloat(elem.innerHTML);
				var elementAmount = "hiddenAmount" + parseInt(itemID);
				var prevAmount = document.getElementById(elementAmount).value;
				if (prevAmount < amount) {
					// added an additional item
					total = total + (amount-prevAmount)*price;
				}
				else {
					// removed one item
					total = total - (prevAmount-amount)*price;
				}
				document.getElementById(elementAmount).value = amount;
				
				total = Math.round(total * 100.0) / 100.0;
				elem.innerHTML = total;
				
				document.getElementById("purchaseButton").href = "paypal.jsp?euro="+total;
			}
			
			$(function () { $("[data-toggle = 'popover']").popover(); });
		</script>
	</body>
</html>