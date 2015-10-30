/**
 * The directives.
 * Created by Otto on 15.10.2015.
 */
'use strict';

var directives = angular.module('directives', []);

// Set up the formatState directive.
directives.directive("formatState", function () {
    /**
     * Return state with glyphicon.
     * @returns append html code to element.
     */
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
                    break;
                case "Abgelehnt":
                    state = "<i class='glyphicon glyphicon-thumbs-down'></i> " + state;
                    break;
                case "Wiedereröffnet":
                    state = "<i class='glyphicon glyphicon-repeat'></i> " + state;
                    break;
                case "Geschlossen":
                    state = "<i class='glyphicon glyphicon-ok'></i> " + state;
                    break;
                case 0:
                    break;
            }
            element.append(state);
        }
    }
});