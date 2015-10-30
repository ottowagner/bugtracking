/**
 * The filters.
 * Created by Otto on 26.10.2015.
 */
'use strict';

var filters = angular.module('filters', []);

// Set up the tableFilter.
filters.filter("bugListFilter", ['$filter', function ($filter) {
    /**
     * @params data The bugList dataArray,
     *         searchString The searchString which we want to find, hideClosed To hide State "Closed"
     * @returns filtered data.
     */
    return function (data, searchString, hideClosed) {
        var output = [];

        var checkString = function (position) {
            var id = data[position].id.toString();
            var title = data[position].title.toLowerCase();
            var state = data[position].state.title.toLowerCase();
            var autor = (data[position].autor.firstname + " " + data[position].autor.lastname).toLowerCase();

            var developer = "";
            if (!!data[position].developer) {
                developer = (data[position].developer.firstname + " " + data[position].developer.lastname).toLowerCase();
            }
            var creationDate = $filter('date')(data[i].creationDate, 'dd.MM.yyyy - HH:mm');

            var lastUpdateDate = "";
            if (!!data[position].lastUpdateDate) {
                var lastUpdateDate = $filter('date')(data[position].lastUpdateDate, 'dd.MM.yyyy - HH:mm');
            }

            if (id.indexOf(searchString) !== -1) {
                return true;
            } else if (title.indexOf(searchString) !== -1) {
                return true;
            } else if (state.indexOf(searchString) !== -1) {
                return true;
            } else if (autor.indexOf(searchString) !== -1) {
                return true;
            } else if (developer.indexOf(searchString) !== -1) {
                return true;
            } else if (creationDate.indexOf(searchString) !== -1) {
                return true;
            } else if (lastUpdateDate.indexOf(searchString) !== -1) {
                return true;
            } else {
                return false;
            }
        };

        if (!!searchString && !!hideClosed) {
            searchString = searchString.toLowerCase();
            for (var i = 0; i < data.length; i++) {
                if (data[i].state.title.toLowerCase() == "geschlossen") {
                    continue;
                } else if (checkString(i)) {
                    output.push(data[i]);
                }
            }
        } else if (!!searchString) {
            searchString = searchString.toLowerCase();
            for (var i = 0; i < data.length; i++) {
                if (checkString(i)) {
                    output.push(data[i]);
                }
            }
        } else if (!!hideClosed) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].state.title.toLowerCase() == "geschlossen") {
                    continue;
                } else {
                    output.push(data[i]);
                }
            }
        } else {
            output = data;
        }
        return output;
    };

}]);