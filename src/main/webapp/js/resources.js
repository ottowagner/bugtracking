/**
 * The resources.
 */
'use strict';

var resources = angular.module('resources', []);

//Set up the Bug Factory
resources.factory('Bug', function () {
    return function (id, title, description, state, author, developer, lastUpdateDate, creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.author = author;
        this.developer = developer;
        this.lastUpdateDate = lastUpdateDate;
        this.creationDate = creationDate;
    };
});

//Set up the Comment Factory
resources.factory('Comment', function () {
    return function (id, bug, title, description, author, creationDate, fromState, toState) {
        this.id = id;
        this.bug = bug;
        this.title = title;
        this.description = description;
        this.author = author;
        this.creationDate = creationDate;
        this.fromState = fromState;
        this.toState = toState;
    };
});

//Set up the User Factory
resources.factory('User', function () {
    return function (id, email, firstname, lastname, password, role) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    };
});