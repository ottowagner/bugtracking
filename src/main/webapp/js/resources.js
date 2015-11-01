/**
 * The resources.
 * Created by Otto on 13.10.2015.
 */
'use strict';

var resources = angular.module('resources', []);

//Set up the Bug Factory
resources.factory('Bug', function () {
    return function (id, title, description, state, toStates, autor, developer, lastUpdateDate, creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.toStates = toStates;
        this.autor = autor;
        this.developer = developer;
        this.lastUpdateDate = lastUpdateDate;
        this.creationDate = creationDate;
    };
});

//Set up the Comment Factory
resources.factory('Comment', function () {
    return function (id, title, description, bug, userId, creationDate, fromState, toState) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bug = bug;
        this.userId = userId;
        this.creationDate = creationDate;
        this.fromState = fromState;
        this.toState = toState;
    };
});

//Set up the User Factory
resources.factory('User', function () {
    return function (id, firstname, lastname, email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    };
});