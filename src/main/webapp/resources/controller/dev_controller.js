var app=angular.module('PeopleApp');
app.controller('DevCtrl', DevCtrl);

function DevCtrl($scope,$http,$document,$interval,$timeout){

    $scope.mortalEnabled = true;
    $scope.mortalChanged = function() {
        console.log("CHANGE MORTAL BIATCH");
    };

}