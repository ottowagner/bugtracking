/**
 * The application.
 * Created by Stephan on 18.12.2014.
 */
'use strict';

var application = angular.module('bugtracking', ['ngRoute','controllers']);

application.config(['$routeProvider',
    function($routeProvider) {
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
    }]);