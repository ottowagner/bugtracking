/**
 * The application.
 * Created by Otto on 12.10.2015.
 */
'use strict';

var application = angular.module('bugtracking', ['ngRoute', 'controllers', 'http-auth-interceptor']);
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

        //https://github.com/dsyer/spring-security-angular/tree/master/singlec
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]).factory('errorInterceptor', ['$q', '$rootScope', '$location',
    function ($q, $rootScope, $location) {
        return {
            request: function (config) {
                return config || $q.when(config);
            },
            requestError: function(request){
                return $q.reject(request);
            },
            response: function (response) {
                return response || $q.when(response);
            },
            responseError: function (response) {
                if (response && response.status === 401) {
                    $location.path('/login');
                }
                    return $q.reject(response);
            }
        };
    }]);
application.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('errorInterceptor');
}]);