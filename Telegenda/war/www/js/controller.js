angular.module('telegendaControllers', []).controller('ListingsCtrl', ['$scope', '$http', 
	function ($scope, $http) {
		
			$scope.calendars = [];
	  	  
			$http.get("http://telegenda-webservice.appspot.com/CalendarList").success(function(data) 
			{
				$scope.calendars = data;
			});
		
		
		
		$scope.findListings = function() 
		{
			$scope.listings = [];
	  	  
			$http.get("http://telegenda-webservice.appspot.com/Listings?keyword="+$scope.keyword).success(function(data) 
			{
				$scope.listings = data.slice(0,10);
			});
    
		};
  
		
}]);