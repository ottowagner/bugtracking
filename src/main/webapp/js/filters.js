/**
 * The filters.
 * Created by Otto on 26.10.2015.
 */
'use strict';

var filters = angular.module('filters', []);

// Set up the formatState directive.
filters.filter("tableFilter", function () {
    /**
     * @returns filtered data.
     */
    return function(data,searchString,hideClosed){
        var output = [];

        if(!!searchString && !!hideClosed){
            searchString = searchString.toLowerCase();
            for(var i = 0;i<data.length; i++){
                if(data[i].title.toLowerCase().indexOf(searchString) !== -1){
                    output.push(data[i]);
                }else if(data[i].state.title.toLowerCase().indexOf(searchString) !== -1){
                    output.push(data[i]);
                }
            }
            //TODO: entferne geschlosssen mehr logik einbauen.. zb auch datum filtern
        } else if(!!searchString){
            searchString = searchString.toLowerCase();
            for(var i = 0;i<data.length; i++){
                if(data[i].title.toLowerCase().indexOf(searchString) !== -1){
                    output.push(data[i]);
                }else if(data[i].state.title.toLowerCase().indexOf(searchString) !== -1){
                    output.push(data[i]);
                }
            }
        } else if(!!hideClosed){
            for(var i = 0;i<data.length; i++){
                if(data[i].state.title.toLowerCase().indexOf("geschlossen") === -1){
                    output.push(data[i]);
                }
            }
        } else {
            output = data;
        }
        return output;
    }
});