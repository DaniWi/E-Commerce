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
		
		<!-- OpenLayers 3 css style sheet -->
		<link rel="stylesheet" href="http://openlayers.org/en/v3.12.1/css/ol.css" type="text/css">
		
		<!-- Include OpenLayers -->
		<script src="http://openlayers.org/en/v3.12.1/build/ol.js" type="text/javascript"></script>
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
			  
			  <%
			  	IDataHandler handler = DataHandler.getInstance();
				User user = handler.getUserByID((int)session.getAttribute("ID"));
			  %>

			  <!-- Collect the nav links, forms, and other content for toggling -->
			  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						 <%if(session.getAttribute("rights").equals("admin")) {%>
							 	<li>
							  		<a href="admin/" title="Backend"><span class="glyphicon glyphicon-wrench" aria-hidden="true"></span> Backend </a>
							 	</li>
					 	 <% } %>
						 <li>
						 	<a href="profile.jsp" title="Profile Page"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> <%= user.getName() %> </a>
						 </li>
						 <li>
						 	<a href="logout.jsp" title="Logout"><span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span> Logout </a>
						 </li>
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
			
			<div class="page-header">
				<h2><%= user.getName() %></h2>
			</div>
			<div>
				<p>id: <%= user.getId() %></p>
				<p>email: <%= user.getEmail() %></p>
				<p>status: <%= user.getRights() %></p>
				<p>joined: <%= user.getJoinedDate() %></p>
				<p>address: <%= user.getAddress() %></p>
				<p>geo: (<%= user.getLatitude() %>/<%= user.getLongitude() %>)</p>
			</div>
			<div id="map" class="map"></div>
		    <script type="text/javascript">
			    
		      	
		      	// HTML5 Geolocation (Lat and Lon)
		        //navigator.geolocation.getCurrentPosition(successCallback, errorCallback, options)
		        
		      	var xmlhttp = new XMLHttpRequest();
				var url = "http://nominatim.openstreetmap.org/search/<%=user.getAddress()%>?format=json";
				
				xmlhttp.onreadystatechange = function() {
				    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				        var myArr = JSON.parse(xmlhttp.responseText);
				        
				        // resulting latitude and longitude
				        var lat;
				        var lon;
				        // saved lat and lon in user profile
				        var userLat = "<%= user.getLatitude() %>";
				        var userLon = "<%= user.getLongitude() %>";
				        if (myArr.length < 1) {
				        	lat = userLat;
				        	lon = userLon;
				        }
				        else if (myArr.length > 1) {
				        	var i;
				        	lat = myArr[0].lat;
				        	lon = myArr[0].lon;
				        	var error = Math.sqrt((userLat-lat)*(userLat-lat)+(userLon-lon)*(userLon-lon));
				        	for (i = 1; i < myArr.length; i++) {
				        		var newError = Math.sqrt((userLat-myArr[i].lat)*(userLat-myArr[i].lat)+(userLon-myArr[i].lon)*(userLon-myArr[i].lon)); 
				        		if ( newError < error) {
				        			error = newError;
				        			lat = myArr[i].lat;
				        			lon = myArr[i].lon;
				        		}
				        	}
				        }
				        else {
				        	lat = myArr[0].lat;
				        	lon = myArr[0].lon;
				        }
				        OpenLayersMap(lat, lon);
				    }
				};
				xmlhttp.open("GET", url, true);
				xmlhttp.send();
				
				function OpenLayersMap(strlat, strlon) {
					var lat = parseFloat(strlat);
					var lon = parseFloat(strlon);
					var vectorSource = new ol.source.Vector({
			    		//source: vectorSource
			    	});
					var iconFeature = new ol.Feature({
			    		geometry: new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857')),
			    		/*
			    	  	name: 'Null Island',
			    	  	population: 4000,
			    	  	rainfall: 500
			    	  	*/
			    	});
					vectorSource.addFeature(iconFeature);

			    	var iconStyle = new ol.style.Style({
			    		image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
			    	    	anchor: [0.5, 46],
			    	    	anchorXUnits: 'fraction',
			    	    	anchorYUnits: 'pixels',
			    	    	opacity: 0.75,
			    	    	src: 'http://openlayers.org/en/v3.9.0/examples/data/icon.png'
			    	  	}))
			    	});
			    	
			    	var vectorLayer = new ol.layer.Vector({
			    	      source: vectorSource,
			    	      style: iconStyle
		    	    });
			    	
			    	
			    	var bottomLayer = new ol.layer.Tile({
	            		source: new ol.source.MapQuest({layer: 'osm'})
	          		});
			    
			      	var map = new ol.Map({
			        	target: 'map',
			        	layers: [ new ol.layer.Tile({source: new ol.source.MapQuest({layer: 'osm'})}), vectorLayer ],
			        	view: new ol.View({
			          		center: ol.proj.fromLonLat([lon, lat]),
			          		zoom: 10
			        	})
			      	});
				}
		    </script>
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