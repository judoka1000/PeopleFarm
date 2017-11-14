var app = angular.module('PeopleApp', ['ngCookies', 'ngClickCopy']);

app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push(function($q, $window) {
        return {
            'responseError': function(rejection) {
                if(rejection.status == 498) {
                    $window.alert("Your session has expired, your browser will reload");
                    $window.location.reload();
                }
                return $q.reject(rejection);
            }
        };
    });
}]);
