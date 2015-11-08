/**
 * The services.
 * Created by Otto on 13.10.2015.
 */
'use strict';

var services = angular.module('services', ['resources']);

// Set up the session service.
services.service('sessionService', ['$http', function ($http) {
    this.user = null;

    /**
     * Login the giving user using an asynchronous REST call with promise.
     * @param user The User to be login.
     * @returns {HttpPromise}.
     */
    this.loginWithPromise = function (user) {
        return $http({
            url: 'login',
            params: {username: user.email, password: user.password},
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };

    /**
     * Logout by using an asynchronous REST call with promise.
     * @returns {HttpPromise}.
     */
    this.logoutWithPromise = function () {
        return $http({
            url: 'logout',
            method: 'POST'
        });
    };

    /**
     * Set / remove session in localStorage
     * @param login The bool to set or remove login.
     */
    this.setLogIn = function (login) {
        if (login) {
            localStorage.setItem("session", {});
        } else {
            localStorage.removeItem("session");
        }
    };

    /**
     * Check session in localStorage
     */
    this.isLoggedIn = function () {
        return localStorage.getItem("session") !== null;
    };

}]);

// Set up the user service.
services.service('userService', ['$http', function ($http) {
    /**
     * Saves the giving user using an asynchronous REST call with promise.
     * @param user The User to be saved.
     * @returns {HttpPromise}.
     */
    this.saveUserWithPromise = function (user) {
        return $http.put('rest/users', user);
    };

    /**
     * Return a user identified with the giving eMail by using an asynchronous REST call with promise.
     * @param eMail The eMail of the User to be loaded.
     * @returns {HttpPromise}.
     */
    this.getUserByMailWithPromise = function (eMail) {
        return $http.post('rest/users', eMail);
    };

    /**
     * Return a user identified with the giving cookie by using an asynchronous REST call with promise.
     * @returns {HttpPromise}.
     */
    this.getUserWithPromise = function () {
        return $http.get('rest/users');
    };
}]);

// Set up the bug service.
services.service('bugService', ['$http', function ($http) {
    /**
     * Return all bugs using an asynchronous REST call with promise.
     * @returns {HttpPromise}.
     */
    this.listBugsWithPromise = function () {
        return $http.get('rest/bugs');
    };

    /**
     * Loads the given bug using an asynchronous REST call with promise.
     * @param bugId The bugId of the bug to be loaded.
     * @returns {HttpPromise}.
     */
    this.loadBugWithPromise = function (bugId) {
        return $http.get('rest/bugs/' + bugId);
    };

    /**
     * Saves a given bug using an asynchronous REST call with promise.
     * @param bug The bug to be saved.
     * @returns {HttpPromise}.
     */
    this.saveBugWithPromise = function (bug) {
        return $http.put('rest/bugs', bug);
    };


    /**
     * Set a given bug State using an asynchronous REST call with promise.
     * @params bugId The bugId of the bug, stateId The stateId of the state to be saved.
     * @returns {HttpPromise}.
     */
    this.setBugStateWithPromise = function (bugId, stateId) {
        return $http.put('rest/bugs/' + bugId + '/state/change/' + stateId);
    };
}]);

// Set up the comment service.
services.service('commentService', ['$http', function ($http) {
    /**
     * Return all comments for a bug using an asynchronous REST call with promise.
     * @param bugId The bugId of the bug where the comment has been saved
     * @returns {HttpPromise}.
     */
    this.listCommentsWithPromise = function (bugId) {
        return $http.get('rest/bugs/' + bugId + '/comments');
    };

    /**
     * Saves a given comment using an asynchronous REST call with promise.
     * @param comment The comment to be saved.
     *        bug The bug where the comment will be saved
     * @returns {HttpPromise}.
     */
    this.saveCommentWithPromise = function (bugId, comment) {
        return $http.put('rest/bugs/' + bugId + '/comments', comment);
    };

}]);

// Set up the state service.
services.service('stateService', ['$http', function ($http) {
    /**
     * Loads the state using an asynchronous REST call with promise.
     * @param stateId The stateId of the state to be loaded.
     * @returns {HttpPromise}.
     */
    this.loadStateWithPromise = function (stateId) {
        return $http.get('rest/states/' + stateId);
    };

    /**
     * Return all possible toStates for a bug using an asynchronous REST call with promise.
     * @param bugId The bugId in which the state has been saved
     * @returns {HttpPromise}.
     */
    this.listToStatesWithPromise = function (bugId) {
        return $http.get('rest/bugs/' + bugId + '/states');
    };
}]);

// Set up the error service.
services.service('errorService', function () {
    var errorMessage = "";
    var showError = false;

    this.errorMessages = {
        errors: {
            required: 'Bitte angeben',
            email: 'Bitte eine Email-Adresse angeben',
            unknown: 'Bitte einen gültigen Wert angeben'
        }
    };

    /**
     * close a error message
     */
    this.closeError = function () {
        this.errorMessage = "";
        this.showError = false;
    };

    /**
     * set a error message
     * @param message The message to be shown
     */
    this.setError = function (message) {
        this.errorMessage = message;
        this.showError = true;
    };

});
