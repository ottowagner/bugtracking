/**
 * The application.
 * Created by Otto on 12.10.2015.
 */
'use strict';

var application = angular.module('bugtracking', ['ngRoute', 'controllers']);

application.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {
        $routeProvider.
            when('/bugs', {
                templateUrl: 'partials/bugList.html',
                controller: 'listBugController'
            }).
            when('/createBug', {
                templateUrl: 'partials/createBug.html',
                controller: 'editBugController'
            }).
            when('/openBug', {
                templateUrl: 'partials/showBug.html',
                controller: 'editBugController'
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
        if (!($rootScope.authenticated == true)) {
            if (next.templateUrl == "partials/login.html") {
            } else if (next.templateUrl == "partials/register.html") {
            } else {
                $location.path("/login");
            }
        }
    });
}]);

//TODO: in eigene klasse ziehen!
application.directive("formatState", function () {
    return {
        link: function (scope, element, attrs) {
            var state = attrs["formatState"];

            switch (state) {
                case "Angelegt":
                    state = "<i class='glyphicon glyphicon-file'></i> " + state;
                    break;
                case "In Bearbeitung":
                    state = "<i class='glyphicon glyphicon-pencil'></i> " + state;
                    break;
                case "Behoben":
                    state = "<i class='glyphicon glyphicon-thumbs-up'></i> " + state;
                    state = state + " 3";
                    break;
                case "Abgelehnt":
                    state = "<i class='glyphicon glyphicon-thumbs-down'></i> " + state;
                    state = state + " 4";
                    break;
                case "Wiedereröffnet":
                    state = "<i class='glyphicon glyphicon-repeat'></i> " + state;
                    state = state + " 5";
                    break;
                case "Geschlossen":
                    state = "<i class='glyphicon glyphicon-ok'></i> " + state;
                    state = state + " 6";
                    break;
                case 0:
                    break;
            }
            element.append(state);
        }
    }
});