/**
 * The directives.
 * Created by Otto on 15.10.2015.
 */
'use strict';

var directives = angular.module('directives', []);

directives.directive("formatState", function () {
    return {
        link: function (scope, element, attrs) {
            var state = scope[attrs["formatState"]];
            console.log(state);
            switch (state) {
                case "Anlegt":
                    state = state + 1
                    break;
                case "In Bearbeitung":
                    state = state + 2
                    break;
                case "Behoben":
                    state = state + 3
                    break;
                case "Abgelehnt":
                    state = state + 4
                    break;
                case "Wiedereröffnet":
                    state = state + 5
                    break;
                case "Geschlossen":
                    state = state + 6
                    break;
                case 0:
                    break;
            }
            element.append(element.text(state));
        },
        restrict: "A"
    }
});