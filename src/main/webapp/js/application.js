/**
 * The application.
 * Created by Otto on 12.10.2015.
 */
'use strict';

var application = angular.module('bugtracking', ['ngRoute', 'controllers']);

application.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {
        $routeProvider.
            when('/register', {
                templateUrl: 'partials/register.html',
                controller: 'mainController'
            }).
            when('/login', {
                templateUrl: 'partials/login.html',
                controller: 'mainController'
            }).
            when('/bugs', {
                templateUrl: 'partials/bugList.html',
                controller: 'listBugController'
            }).
            when('/bugs/create', {
                templateUrl: 'partials/createBug.html',
                controller: 'editBugController'
            }).
            when('/bugs/:bugId', {
                templateUrl: 'partials/showBug.html',
                controller: 'showBugController'
            }).
            when('/bugs/:bugId/edit', {
                templateUrl: 'partials/createBug.html',
                controller: 'editBugController'
            }).
            when('/bugs/:bugId/comments/create', {
                templateUrl: 'partials/createComment.html',
                controller: 'showBugController' //TODO: evtl eigener controller
            }).
            otherwise({
                redirectTo: '/login'
            });

        //https://github.com/dsyer/spring-security-angular/tree/master/single
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]);

application.run(['$rootScope', '$location', function ($rootScope, $location) {
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        if (!($rootScope.authenticated == true)) {
            if (next.templateUrl == "partials/login.html") {
            } else if (next.templateUrl == "partials/register.html") {
            } else {
                $location.path("/login");
            }
        }
    });
}]);