var app = angular.module('PeopleApp', []);
app.controller('LoginCtrl', LoginCtrl);

function LoginCtrl($scope, $http, $location) {
    console.log('from loginctrl');
    $scope.login = function(){

        $http.post('logincheck', angular.toJson($scope.account)).then(function(response) {
            console.log('succes from logincheck');
            console.log(response.data);
            if (response.data == true) {
                window.location = 'main';
            }
            else{
                window.alert("Username of wachtwoord onjuist");
            }
        });

    };

    $scope.save = function(){

        $http.post('createaccount', angular.toJson($scope.account)).then(function(success) {
            console.log('succes');
            window.location = 'main';
            });
  }};

