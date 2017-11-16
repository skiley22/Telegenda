angular.module('telegendaControllers', []).controller('ListingsCtrl', ['$scope', '$http',
	function ($scope, $http)
	{
		$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

		$scope.calendars = [];
		$scope.listings = [];
		$scope.keywords = [];
		$scope.currentPage = 0;
		$scope.pageSize = 10;
		$scope.disableSaveButton = false;
		$scope.disableDeleteButton = false;
		$scope.nothingSearched = true;
		$scope.selectedKeyword = {};
		$scope.showOverlay = true;
		$scope.saveKeywordButtonText = "Save Keyword";
		$scope.deleteKeywordButtonText = "Delete Keyword";

		var checkCount = 0; //need 4 "checks" to turn off the overlay

		$http.get("http://telegenda-185619.appspot.com/SavedKeywords").success(function(data)
		{
			$scope.keywords = data;
			$scope.selectedKeyword = $scope.keywords[0];
			checkCount++;
			if(checkCount == 4)
				$scope.showOverlay = false;
		});
		$http.get("http://telegenda-185619.appspot.com/Username").success(function(data)
		{
			$scope.username = "Welcome, " + data;

			checkCount++;
			if(checkCount == 4)
				$scope.showOverlay = false;
		});

		$http.get("http://telegenda-185619.appspot.com/CalendarList").success(function(data)
        {
            if(data.length == 0)
                alert("You need to set up Google Calendar before using this web app");

            $scope.calendars = data;
            $scope.selectedCalendar = $scope.calendars[0];

            checkCount++;
            if(checkCount == 4)
                $scope.showOverlay = false;
        });

        $scope.findListings = function()
        {
            $http.get("http://telegenda-185619.appspot.com/Listings?keyword="+$scope.keyword).success(function(data)
            {
                $scope.listings = data;

                if(data.length > 0)
                {
                    $scope.numberOfPages=function()
                    {
                        return Math.ceil($scope.listings.length/$scope.pageSize);
                    }
                    $scope.nothingSearched = false;
                }
                else
                    alert("No results found");
                });
        };

        $scope.addEvent = function(listing)
        {
            $http({
                method: 'POST',
                url: "http://telegenda-185619.appspot.com/Event",
                data: $.param({"calendar":$scope.selectedCalendar.id, "listing":angular.toJson(listing)})
            }).then(function successCallback(msg) {
                alert(msg.data);
              }, function errorCallback(msg) {
                alert(msg.data);
              });
        };

        $http.get("http://telegenda-185619.appspot.com/LogoutUrl").success(function(data)
        {
            $scope.logoutUrl = data;

            checkCount++;
            if(checkCount == 4)
                $scope.showOverlay = false;
        });
        $scope.deleteKeyword = function()
        {
            $scope.deleteKeywordButtonText = "Please Wait..";
            $scope.disableDeleteButton = true;

            $http({
                method: 'DELETE',
                url: "http://telegenda-185619.appspot.com/SavedKeywords?id="+$scope.selectedKeyword.keywordId
            }).then(function successCallback(msg)
            {
                var index = $scope.keywords.indexOf($scope.selectedKeyword);
                $scope.keywords.splice(index, 1);
                $scope.selectedKeyword = $scope.keywords[0];
                $scope.deleteKeywordButtonText = "Delete Keyword";
                $scope.disableDeleteButton = false;
                alert(msg.data);
            }, function errorCallback(msg) {
                $scope.deleteKeywordButtonText = "Delete Keyword";
                $scope.disableDeleteButton = false;
                alert(msg.data);
              });
        };
		$scope.saveKeyword = function(calendarId, keyword)
		{
			$scope.saveKeywordButtonText = "Please Wait..";
			$scope.disableSaveButton = true;
		   // $scope.saveKeywordButtonStyle = {'background-image': 'url(www/img/ajax-spinner.gif)'};

			$http({
			  method: 'POST',
			  url: 'http://telegenda-185619.appspot.com/CalendarCron',
			  data: $.param({"calendarid":$scope.selectedCalendar.id, "keyword":$scope.keyword})
			}).then(function successCallback(msg)
			{
				$scope.saveKeywordButtonText = "Save Keyword";
				$scope.disableSaveButton = false;
				//$scope.saveKeywordButtonStyle = {};

				alert(msg.data);

				$http.get("http://telegenda-185619.appspot.com/SavedKeywords").success(function(data)
				{
					$scope.keywords = data;
					$scope.selectedKeyword = $scope.keywords[0];
				});


			  }, function errorCallback(msg)
			  {
					$scope.saveKeywordButtonText = "Save Keyword";
					$scope.disableSaveButton = false;
			//	    $scope.saveKeywordButtonStyle = {};

					alert(msg.data);

			  });
		};
	}
]);

angular.module('pageFilter', []).filter('startFrom', function()
	{
		return function(input, start) {
			start = +start; //parse to int
			//TO DO - add blank objects when the last page is <10
			return input.slice(start);
		}
	});

angular.module('telegendaControllers').directive('ngEnter', function() {
	return function(scope, element, attrs) {
		element.bind("keydown keypress", function(event) {
			if(event.which === 13) {
				scope.$apply(function(){
					scope.$eval(attrs.ngEnter, {'event': event});
				});
				event.preventDefault();
			}
		});
	};
});