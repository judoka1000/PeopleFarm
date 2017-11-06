var app=angular.module('PeopleApp');
app.controller('DevCtrl', DevCtrl);

function DevCtrl($scope, $http,$document,$interval,$timeout, $cookies, apiEngine, personsFactory){
    var baseUrl = "http://localhost:8080/peoplefarm";
    $scope.devSettings = {};
    $scope.newPerson = {};
    $scope.cookieInfo = "JSESSIONID=" + $cookies.get('JSESSIONID');

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

    $scope.createPerson = function(){
        var url = baseUrl + "/createperson";
        console.log($scope.newPerson);
        $http.post(url, $scope.newPerson).then(function(response){
            console.log(response);
            personsFactory.addPerson(response.data);
        });
    };
    $scope.init();
}