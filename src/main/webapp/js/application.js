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
                templateUrl: 'views/auth/register.html'
            }).
            when('/login', {
                templateUrl: 'views/auth/login.html'
            }).
            when('/bugs', {
                templateUrl: 'views/bug/list.html',
                controller: 'listBugController as listCtrl'
            }).
            when('/bugs/create', {
                templateUrl: 'views/bug/edit.html',
                controller: 'editBugController as editCtrl'
            }).
            when('/bugs/:bugId', {
                templateUrl: 'views/bug/show.html',
                controller: 'showBugController as showCtrl'
            }).
            when('/bugs/:bugId/edit', {
                templateUrl: 'views/bug/edit.html',
                controller: 'editBugController as editCtrl'
            }).
            when('/bugs/:bugId/comments/create', {
                templateUrl: 'views/comment/edit.html',
                controller: 'commentController as cmtCtrl'
            }).
            when('/bugs/:bugId/state/change/:stateId', {
                templateUrl: 'views/state/change.html',
                controller: 'commentController as cmtCtrl'
            }).
            otherwise({
                redirectTo: '/bugs'
            });

        //https://github.com/dsyer/spring-security-angular/tree/master/single
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]);
//
//application.run(['$rootScope', '$location', '$scope', 'authService', function ($rootScope, $location, $scope, authService) {
//    $rootScope.$on("$routeChangeStart", function (event, next, current) {
//        if ($scope.mainModel.showError == true) {
//            $scope.mainModel.showError = false;
//        }
//        //if (!(authService.authenticated == true)) {
//        //    if (next.templateUrl == "partials/login.html") {
//        //    } else if (next.templateUrl == "partials/register.html") {
//        //    } else {
//        //        $location.path("/login");
//        //    }
//        //}
//    });
//}]);