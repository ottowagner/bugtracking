/**
 * The resources.
 * Created by Stephan on 18.12.2014.
 */
'use strict';

var resources = angular.module('resources', []);

resources.factory('Bug', function () {
    return function (id, title, description, state, autor, developer, lastUpdateDate, creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.autor = autor;
        this.developer = developer;
        this.lastUpdateDate = lastUpdateDate;
        this.creationDate = creationDate;
    };
});