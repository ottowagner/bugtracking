/**
 * The application.
 * Created by Stephan on 18.12.2014.
 */
'use strict';

var application = angular.module('bugtracking', ['ngRoute', 'controllers']);

application.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {
        $routeProvider.
            when('/bug', {
                templateUrl: 'partials/bugList.html',
                controller: 'mainController'
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
                redirectTo: '/bug'
            });

        //https://github.com/dsyer/spring-security-angular/tree/master/single
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]);