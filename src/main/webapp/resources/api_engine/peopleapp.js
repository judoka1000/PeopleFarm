var app = angular.module('PeopleApp', ['ngCookies', 'ngClickCopy']);

app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push(function($q, $window, apiEngine) {
        return {
            'responseError': function(rejection) {
                if(rejection.status == 498) {
                    apiEngine.disableAPI();
                    $window.alert("Your session has expired, your browser will reload");
                    $window.location.reload();
                }
                return $q.reject(rejection);
            }
        };
    });
}]);
