angular.module('telegendaControllers', []).controller('ListingsCtrl', ['$scope', '$http', 
	function ($scope, $http) {
		
		$scope.calendars = [];
		$scope.listings = [];		
		$scope.currentPage = 0;
		$scope.pageSize = 10;
		
		$http.get("http://telegenda-webservice.appspot.com/CalendarList").success(function(data) 
		{
			$scope.calendars = data;
			$scope.selectedCalendar = $scope.calendars[0];
		});
		
		$scope.findListings = function() 
		{
			$http.get("http://telegenda-webservice.appspot.com/Listings?keyword="+$scope.keyword).success(function(data) 
			{
				$scope.listings = data;
			});    
			
			$scope.numberOfPages=function()
			{
				return Math.ceil($scope.listings.length/$scope.pageSize);                
			}
		};
		
		$scope.addToCalendar = function(listing)
		{		
			submitEvent($scope.selectedCalendar, angular.toJson(listing));
		}
}]);

angular.module('pageFilter', []).filter('startFrom', function() 
	{
		return function(input, start) {
			start = +start; //parse to int
			
			//TO DO - add blank objects when the last page is <10
			
			return input.slice(start);
		}
	});