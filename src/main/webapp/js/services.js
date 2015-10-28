/**
 * The services.
 * Created by Otto on 13.10.2015.
 */
'use strict';

var services = angular.module('services', ['resources']);

// Set up the session service.
services.service('sessionService', ['$http', function ($http) { //TODO: base64?!
    this.user = null;

    this.loginWithPromise = function (user) {
        return $http({
            url: 'login',
            params: {username: user.email, password: user.password},
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };

    this.logout = function () {
        localStorage.removeItem("session");
    };

    this.isLoggedIn = function () {
        return localStorage.getItem("session") !== null;
    };

}]);

// Set up the user service.
services.service('userService', ['$http', function ($http) {
    this.saveUserWithPromise = function (user) {
        return $http.put('rest/users', user);
    };

    this.getUserWithPromise = function(eMail) {
        return $http({
            url: 'rest/users',
            params: {email: eMail},
            method: 'GET'
        });
    };

    this.userExistsWithPromise = function (eMail) {
        return $http.post('rest/users', eMail);
    };

}]);

// Set up the bug service.
services.service('bugService', ['$http', function ($http) {
    /**
     * Return all bug using an asynchronous REST call with promise.
     * @returns {HttpPromise}.
     */
    this.listBugsWithPromise = function () {
        return $http.get('rest/bugs');
    };

    /**
     * Loads the given bug using an asynchronous REST call with promise.
     * @param bug The bug to be loaded.
     * @returns {HttpPromise}.
     */
    this.loadBugWithPromise = function (bugId) {
        return $http.get('rest/bugs/' + bugId);
    }

    /**
     * Saves a given bug using an asynchronous REST call with promise.
     * @param bug The bug to be saved.
     * @returns {HttpPromise}.
     */
    this.saveBugWithPromise = function (bug) {
        return $http.put('rest/bugs', bug);
    };


    /**
     * Saves a given bug State using an asynchronous REST call with promise.
     * @param bugId, stateId The bug state to be saved.
     * @returns {HttpPromise}.
     */
    this.setBugStateWithPromise = function (bugId, stateId) {
        return $http.put('rest/bugs/' + bugId + '/state/change/' + stateId);
    };
}]);

// Set up the comment service.
services.service('commentService', ['$http', function ($http) {
    /**
     * Return all comment for a bug using an asynchronous REST call with promise.
     * @param bug The bug in which the comment has been saved
     * @returns {HttpPromise}.
     */
    this.listCommentsWithPromise = function (bugId) {
        return $http.get('rest/bugs/' + bugId + '/comments');
    };

    /**
     * Saves a given comment using an asynchronous REST call with promise.
     * @param comment The comment to be saved.
     *        bug The bug in which the comment will be saved
     * @returns {HttpPromise}.
     */
    this.saveCommentWithPromise = function (bugId, comment) {
        return $http.put('rest/bugs/' + bugId + '/comments', comment);
    };

}]);

// Set up the state service.
services.service('stateService', ['$http', function ($http) {
    /**
     * Loads the given bug using an asynchronous REST call with promise.
     * @param bug The bug to be loaded.
     * @returns {HttpPromise}.
     */
    this.loadStateWithPromise = function (stateId) {
        return $http.get('rest/states/' + stateId);
    };

    /**
     * Return all states for a bug using an asynchronous REST call with promise.
     * @param bugId The bugId in which the state has been saved
     * @returns {HttpPromise}.
     */
    this.listToStatesWithPromise = function (bugId) {
        return $http.get('rest/bugs/' + bugId + '/states');
    };
}]);

// Set up the data service.
services.service('dataService', function () {
    var data = this;

    data.fromState = "";
    data.toState = "";
});