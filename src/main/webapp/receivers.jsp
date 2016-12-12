<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Lab 2</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.min.js"></script>
    <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
  </head>
  <body>
    <div id="map" style="width: 100%px; height: 500px;"></div>
    <div ng-app="Lab2App">
      <div ng-controller="ReceiversController" class="container">
        <table class="table">
          <tr>
            <th>Serial</th>
            <th>Temperature</th>
            <th>Coordinates</th>
            <th>Received At</th>
          </tr>
          <tr ng-repeat="r in results">
            <th>{{r.serial}}</th>
            <th>{{r.temperature}}</th>
            <th>{{r.coordinates}}</th>
            <th>{{r.receivedAt}}</th>
          </tr>
        </table>
      </div>
    </div>
    <script>
      var results = ${results};

      var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 2,
        center: new google.maps.LatLng(-33.92, 151.25),
        mapTypeId: google.maps.MapTypeId.ROADMAP
      });

      var infowindow = new google.maps.InfoWindow();

      var marker, i;

      for (i = 0; i < results.length; i++) {
        console.log(results[i].coordinates.x, results[i].coordinates.y);
        marker = new google.maps.Marker({
          position: new google.maps.LatLng(results[i].coordinates.x, results[i].coordinates.y),
          map: map
        });

        google.maps.event.addListener(marker, 'click', (function(marker, i) {
          return function() {
            infowindow.setContent(results[i][0]);
            infowindow.open(map, marker);
          }
        })(marker, i));
      }
    </script>

    <script>
      angular.module('Lab2App', [])
        .controller('ReceiversController', function($scope) {
          $scope.results = results;
        });
    </script>
  </body>
</html>
