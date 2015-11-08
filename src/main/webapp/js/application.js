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

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $httpProvider.interceptors.push('authHttpResponseInterceptor');
    }]);

//Prüfe bei jeden Seitenaufruf, ob der Benutzer eingeloggt ist.
//Ansonsten kann vorkommen, dass im Frontend ein Aufruf durchgeführt wird ohne eingeloggt zu sein!
application.run(['$rootScope', '$location', 'sessionService', function ($rootScope, $location, sessionService) {
    $rootScope.$on("$routeChangeStart", function (event, next) {
        if (!(sessionService.isLoggedIn())) {
            if (!(next.templateUrl == "views/auth/login.html") && !(next.templateUrl == "views/auth/register.html")) {
                $location.path("/login");
            }
        }
    });
}]);

// Sollte doch mal eine Anfrage ohne Autorisierung durchgeführt werden, so wirft das Backend einen 401er Fehler, der
// im Frontend per authHttpResponseInterceptor abgefangen wird.
application.factory('authHttpResponseInterceptor', ['$q', '$location', 'errorService',
    function ($q, $location, errorService) {
        return {
            responseError: function (rejection) {
                if (rejection.status === 401) {
                    errorService.setError(rejection.message);
                    $location.path('/login');
                }
                return $q.reject(rejection);
            }
        };
    }]);