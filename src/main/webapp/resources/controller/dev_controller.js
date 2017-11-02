var app=angular.module('PeopleApp');
app.controller('DevCtrl', DevCtrl);

function DevCtrl($scope,$http,$document,$interval,$timeout, apiEngine){
    var baseUrl = "http://localhost:8080/peoplefarm";
    $scope.devSettings = {};

    $scope.init = function(){
        var url = baseUrl + "/getDevSettings";
        $http.get(url).then(function(response){
            $scope.devSettings.mortal = response.data.mortal;

        });
    }

    $scope.settingsChanged = function() {
        var url = baseUrl + "/putDevSettings";
        $http.put(url, $scope.devSettings).then(function(response){
             console.log(response);
        });
    };

    $scope.init();
}