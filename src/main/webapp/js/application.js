/**
 * The application.
 * Created by Stephan on 18.12.2014.
 */
'use strict';

var application = angular.module('bugtracking', ['ngRoute', 'controllers']);

application.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {
        $routeProvider.
            when('/bugs', {
                templateUrl: 'partials/bugList.html',
                controller: 'bugListController'
            }).
            when('/createBug', {
                templateUrl: 'partials/createBug.html',
                controller: 'formController'
            }).
            when('/register', {
                templateUrl: 'partials/register.html',
                controller: 'mainController'
            }).
            when('/login', {
                templateUrl: 'partials/login.html',
                controller: 'mainController'
            }).
            //when('/bug/:bugId', {
            //    templateUrl: 'partials/phone-detail.html',
            //    controller: 'PhoneDetailCtrl'
            //}).
            otherwise({
                redirectTo: '/login'
            });

        //https://github.com/dsyer/spring-security-angular/tree/master/single
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]);

application.run(['$rootScope', '$location', function ($rootScope, $location) {
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        if ($rootScope.authenticated == false) {
            if (next.templateUrl == "partials/login.html") {
            } else if (next.templateUrl == "partials/register.html") {
            } else {
                $location.path("/login");
            }
        }
    });
}]);