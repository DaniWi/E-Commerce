<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% IDataHandler handler = DataHandler.getInstance(); 
   int categoryID = Integer.parseInt(request.getParameter("categoryID"));
   Category category = handler.getCategoryByID(categoryID);
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
		
		<!-- OpenLayers 3 css style sheet -->
		<link rel="stylesheet" href="http://openlayers.org/en/v3.12.1/css/ol.css" type="text/css">
		
		<!-- Include OpenLayers -->
		<script src="http://openlayers.org/en/v3.12.1/build/ol.js" type="text/javascript"></script>
	</head>
	<body id="page_login">
		<!-- Fixed navbar -->
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="/E-Commerce/">Webshop 4</a>
				</div>
			</div>
		</nav>
	
		<!-- Begin page content -->
		<div class="container">
			<a type="button" class="btn btn-primary" href="index.jsp">Home</a>
			<a type="button" class="btn btn-primary" href="category.jsp?categoryID=<%=categoryID%>">Back to Items</a>
			
			<div class="page-header">
				<h2><%= category.getName() %></h2>
			</div>
			
			<div id="map" class="map"><div id="popup"></div></div>
			
			<script type="text/javascript">
			// Default location: Innsbruck, Maria-Theresien-Straﬂe
			var DEFAULT_LATITUDE = 47.265647;
			var DEFAULT_LONGITUDE = 11.394225;
			var centerLat;
			var centerLon;
			if (navigator.geolocation) {
		        navigator.geolocation.getCurrentPosition(setPosition);
		    }
			else {
		        shopRequest(DEFAULT_LATITUDE,DEFAULT_LONGITUDE);
		    }
			
			function setPosition(position) {
				//console.log(position.coords.latitude + ", " +position.coords.longitude);
			    shopRequest(position.coords.latitude,position.coords.longitude);
			}
			
			function shopRequest(centerLat, centerLon) {
				var SEARCH_RADIUS = 10.0;	// [km]
				var EARTH_RADIUS = 6371.0;	// [km]
				
				var latOffset = (SEARCH_RADIUS / EARTH_RADIUS) * 180.0 / Math.PI;
				var lonOffset = SEARCH_RADIUS / (EARTH_RADIUS * Math.cos(centerLon * Math.PI / 180.0)) * 180.0 / Math.PI;
				var fromLat = centerLat - latOffset;
				var toLat = centerLat + latOffset;
				var fromLon = centerLon - lonOffset;
				var toLon = centerLon + lonOffset;
				
				var category = "<%=category.getName().toLowerCase()%>";
				var xmlhttp = new XMLHttpRequest();
				var url = "http://overpass-api.de/api/interpreter?data=[out:json];node("+fromLat+","+fromLon+","+toLat+","+toLon+")[shop="+category+"];out;";
				xmlhttp.onreadystatechange = function() {
				    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				        var json = JSON.parse(xmlhttp.responseText);
				        OpenLayersMap(json);
				    }
				};
				xmlhttp.open("GET", url, true);
				xmlhttp.send();
				
				function OpenLayersMap(json) {
					var vectorSource = new ol.source.Vector({ });
					for (var i = 0; i < json.elements.length; i++) {
						var shop = json.elements[i];
						var openingHours;
						if (shop.tags.opening_hours){
							openingHours = shop.tags.opening_hours
						}
						else {
							openingHours = '';
						}
						var iconFeature = new ol.Feature({
				    		geometry: new ol.geom.Point(ol.proj.transform([shop.lon, shop.lat], 'EPSG:4326', 'EPSG:3857')),
				    		name: shop.tags.name,
				    	  	opening_hours: openingHours
				    	});
						vectorSource.addFeature(iconFeature);
					}
					
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
					
			      	var map = new ol.Map({
			        	target: 'map',
			        	layers: [ new ol.layer.Tile({source: new ol.source.MapQuest({layer: 'osm'})}), vectorLayer],
			        	view: new ol.View({
			          		center: ol.proj.fromLonLat([centerLon, centerLat]),
			          		zoom: 13
			        	})
			      	});
			      	
			      	var element = document.getElementById('popup');

			      	var popup = new ol.Overlay({
      	  				element: element,
			      	  	positioning: 'bottom-center',
			      	  	stopEvent: false
			      	});
			      	map.addOverlay(popup);

			      	// display popup on click
			      	map.on('click', function(evt) {
			      	  	var feature = map.forEachFeatureAtPixel(evt.pixel,
		      	      	function(feature, layer) {
		      	        	return feature;
		      	      	});
			      	  	if (feature) {
			      	    	popup.setPosition(evt.coordinate);
			      	    	$(element).popover({
			      	      		'placement': 'top',
			      	      		'html': true,
			      	      		'content': feature.get('name') +' '+ feature.get('opening_hours')
			      	    	});
			      	    	$(element).popover('show');
			      	  	}
			      	  	else {
			      	    	$(element).popover('destroy');
			      	  	}
		      		});
			      	
			     	// change mouse cursor when over marker
			      	var cursorHoverStyle = "pointer";
			      	var target = map.getTarget();

			      	//target returned might be the DOM element or the ID of this element dependeing on how the map was initialized
			      	//either way get a jQuery object for it
			      	var jTarget = typeof target === "string" ? $("#"+target) : $(target);

			      	map.on("pointermove", function (event) {
			      	    var mouseCoordInMapPixels = [event.originalEvent.offsetX, event.originalEvent.offsetY];

			      	    //detect feature at mouse coords
			      	    var hit = map.forEachFeatureAtPixel(mouseCoordInMapPixels, function (feature, layer) {
			      	        return true;
			      	    });

			      	    if (hit) {
			      	        jTarget.css("cursor", cursorHoverStyle);
			      	    } else {
			      	        jTarget.css("cursor", "");
			      	    }
			      	});
				}
			}
			</script>

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