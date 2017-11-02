var app=angular.module('PeopleApp');
app.controller('DevCtrl', DevCtrl);

function DevCtrl($scope,$http,$document,$interval,$timeout, apiEngine){
    $scope.devSettings = {};

    $scope.init = function(){
        var url = "http://localhost:8080/peoplefarm/getDevSettings";
        $http.get(url).then(function(response){
            $scope.devSettings.mortal = response.data.mortal;

        });
    }

    $scope.settingsChanged = function() {
        var url = "http://localhost:8080/peoplefarm/putDevSettings";
        $http({
            method : "PUT",
            url : url,
            data : angular.toJson($scope.devSettings),
            headers : {
                'Content-Type' : 'application/json'
            }
            }).then(function (response) {
                 console.log(response);
            }, function error(response) {
            });
    };

    $scope.init();
}