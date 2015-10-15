/**
 * The resources.
 * Created by Otto on 13.10.2015.
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

resources.factory('State', function () {
    //ENUM implementieren
    return function (id, name, logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    };
});

resources.factory('Comment', function () {
    return function (id, title, description, userID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userID = userID;
        this.creationDate = creationDate;
    };
});

resources.factory('Developer', function () {
    return function (id, firstname, lastname, email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    };
});

resources.factory('Autor', function () {
    return function (id, firstname, lastname, email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    };
});