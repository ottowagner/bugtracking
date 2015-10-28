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
                templateUrl: 'partials/register.html'
            }).
            when('/login', {
                templateUrl: 'partials/login.html'
            }).
            when('/bugs', {
                templateUrl: 'partials/bugList.html',
                controller: 'listBugController as listCtrl'
            }).
            when('/bugs/create', {
                templateUrl: 'partials/createBug.html',
                controller: 'editBugController as editCtrl'
            }).
            when('/bugs/:bugId', {
                templateUrl: 'partials/showBug.html',
                controller: 'showBugController as showCtrl'
            }).
            when('/bugs/:bugId/edit', {
                templateUrl: 'partials/createBug.html',
                controller: 'editBugController as editCtrl'
            }).
            when('/bugs/:bugId/comments/create', {
                templateUrl: 'partials/createComment.html',
                controller: 'commentController as cmtCtrl'
            }).
            when('/bugs/:bugId/state/change/:stateId', {
                templateUrl: 'partials/changeState.html',
                controller: 'commentController as cmtCtrl'
            }).
            otherwise({
                redirectTo: '/bugs'
            });

        //https://github.com/dsyer/spring-security-angular/tree/master/single
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]);

//application.run(['$rootScope', '$location', 'authService', function ($rootScope, $location, authService) {
//    $rootScope.$on("$routeChangeStart", function (event, next, current) {
//        if (!(authService.authenticated == true)) {
//            if (next.templateUrl == "partials/login.html") {
//            } else if (next.templateUrl == "partials/register.html") {
//            } else {
//                $location.path("/login");
//            }
//        }
//    });
//}]);