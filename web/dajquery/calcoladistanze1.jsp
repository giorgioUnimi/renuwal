<html>
<head>
<title>Google Maps API - Map Type</title>
<style type="text/css">
div#map_container{
	width:100%;
	height:350px;
}
</style>
<script type="text/javascript" 
     src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
 
<script type="text/javascript">
  function loadMap() {
    var latlng = new google.maps.LatLng(45.4767627, 9.2264264);
    var myOptions = {
      zoom: 13,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.HYBRID,
	  mapTypeControlOptions: {
		style: google.maps.MapTypeControlStyle.DROPDOWN_MENU,
		position: google.maps.ControlPosition.TOP_CENTER
	  }
    };
    var map = new google.maps.Map(document.getElementById("map_container"),myOptions);
 
    var marker = new google.maps.Marker({
      position: latlng, 
      map: map, 
      title:"Azienda1"
    }); 
 
  }
  
  
    function calcRoute() {
        var start = document.getElementById("start").value;
        var end = document.getElementById("end").value;
        
        var request = {
            origin:start,
            destination:end,
            travelMode: google.maps.DirectionsTravelMode.DRIVING
        };
        
        /**
         *calling google maps API function route to show (highlight) the route from one place to another 
         */
        directionsService.route(request, function(response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
                
                /*
                 *This calculates the distance between two points
                 */
                var distanceInput = document.getElementById("distance");
                distanceInput.value = response.routes[0].legs[0].distance.value / 1000;
                
            }
  
  
</script>
</head>
 
<body onload="loadMap()">
<div id="map_container"></div>

<%

    System.out.println("ciao a tutti");
    out.println("hello ");
%>
</body>
 
</html>