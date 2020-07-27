
var myApp = angular.module('myApp', []);

myApp.directive('fileModelValidity', ['$parse', function ($parse) {
   return {
      restrict: 'A',
	  require: 'ngModel',
      link: function(scope, element, attrs) {
		 //console.log('in directive');
         var model = $parse(attrs.fileModelValidity);
         var modelSetter = model.assign;
		         
         element.on('change', function() {
			scope.FileErrorMessage = '';
			scope.disableUpload = false;
			//console.log('in bind change');
			
			if(element[0].files.length == 0){
				scope.disableUpload = true;
				return;
			}	
			
			var name1 = element[0].files[0].name;
			var size = element[0].files[0].size;
            var ext = name1.substring(name1.lastIndexOf('.') + 1).toLowerCase(); 
			console.log(name1 , ext, size);
    
			scope.$apply(function() {
				if (ext !='csv' ){
					scope.FileErrorMessage = 'Invalid file type, only .csv file is accepted';
					scope.disableUpload = true;
				}
				if (size == 0){
					scope.FileErrorMessage = 'Empty file detected, please select valid file';
					scope.disableUpload = true;
				}
				modelSetter(scope, element[0].files[0]);
			});
         });
      }
   };
}]);

myApp.directive('searchvalidity', function () {	
   return {
      restrict: 'A',
	  require: 'ngModel',
      link: function(scope, element) {
		 //console.log('in search directive');
					         
         element.on('change', function() {
			//console.log('in search bind change');
			if(scope.salaryhigh < scope.salarylow){
				scope.searchErrorMessage = 'Invalid salary range for search';
				scope.disableSearch = true;
			}else{
				scope.searchErrorMessage = '';
				scope.disableSearch = false;
			}
         });
      }
   };
});

myApp.controller('myCtrl', ['$scope', '$http', function($scope,$http) {

    $scope.count = 0;
	$scope.userIp = null;
	
	$http.get("http://api.ipify.org/?format=json").then(function(response){
		$scope.userIp = response.data;
		console.log('CLIENT IP: '+$scope.userIp);
		console.log($scope.userIp.ip);
	})

	$scope.uploadFile = function() {
		//console.log('in uploadFile');
		var file = $scope.file;
	    var uploadUrl = "/users/uploadFile";
	    var fd = new FormData();
        fd.append('file', file);
		fd.append('ip', $scope.userIp.ip);

		return $http.post(uploadUrl, fd, {
        	transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        
		}).then(function (response) {

			console.log('success:');
			console.log(response);
			alert(response.data.responseText);
			var fileElement = angular.element(document.querySelector('#userUpload'));
			angular.element(fileElement).val(null);
		
		}, function (errorresponse) {
		
			console.log('error:');
			console.log(errorresponse);
			alert('OOPS!!! Something went wrong !');
			var fileElement = angular.element(document.querySelector('#userUpload'));
			angular.element(fileElement).val(null);
		
		});
    };
	
    $scope.searchUser = function() {
		//console.log('in searchUser');
		$scope.resultUsers= '';
		$scope.salarylow = angular.isDefined($scope.salarylow) ? $scope.salarylow : 0;
		$scope.salaryhigh = angular.isDefined($scope.salaryhigh) ? $scope.salaryhigh : 0;
		var searchUrl = "/users/searchUsers";
		var fd = new FormData();
        fd.append('salarylow', $scope.salarylow);
		fd.append('salaryhigh', $scope.salaryhigh);
		
		if($scope.salaryhigh < $scope.salarylow){
			$scope.searchErrorMessage = 'Invalid salary range for search';
			$scope.disableSearch = true;
			return;
		}
    	return $http.post(searchUrl, fd, {
        	transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        
		}).then(function (successresponse) {
			console.log('success:');
			//console.log(successresponse);
			if(successresponse.data.length == 0){
				alert('No result returned, please search again');
				return;
			}
			$scope.resultDataDiv = true;
			$scope.resultUsers= successresponse.data;
			alert(successresponse.data.length + ' users found');
			
		}, function (errorresponse) {
		
			console.log('error:');
			console.log(errorresponse);
			alert('OOPS!!! Something went wrong !');
		
		});
    };

    $scope.searchFiles = function() {
		//console.log('in searchFiles');
		$scope.resultFiles= '';
		var searchUrl = "/users/getAllFiles";
		
    	return $http.get(searchUrl).then(function (successresponse) {
			console.log('success:');
			//console.log(successresponse);
			if(successresponse.data.length == 0){
				alert('No result returned, please upload 1 file');
				return;
			}
			
			$scope.resultFileDiv = true;
			$scope.resultFiles= successresponse.data;
			alert(successresponse.data.length + ' files found');
			
		}, function (errorresponse) {
		
			console.log('error:');
			console.log(errorresponse);
			alert('OOPS!!! Something went wrong !');
		
		});
    };

    $scope.resetPage = function() {
		//console.log('in reset');
		$scope.searchErrorMessage = '';
		$scope.disableSearch = false;
		$scope.salarylow = 0;
		$scope.salaryhigh = 0;
		$scope.resultUsers= '';
		$scope.resultDataDiv = false;
		$scope.resultFiles = '';
		$scope.resultFileDiv = false;
		var fileElement = angular.element(document.querySelector('#userUpload'));
		angular.element(fileElement).val(null);
		$scope.FileErrorMessage = '';
		$scope.disableUpload = false;
    };

}]);

