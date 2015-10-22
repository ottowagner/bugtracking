/**
 * The services.
 * Created by Otto on 13.10.2015.
 */
'use strict';

var services = angular.module('services', ['resources']);

// Set up the auth service.
services.service('authService', ['$http', function ($http) {
    /**
     * Loads the given user using an asynchronous REST call with promise.
     * @param user The user to be loaded.
     * @returns {HttpPromise}.
     */
        //TODO: SICHERHEIT, nicht direkt mit dem user/password
    this.loadUserWithPromise = function (user) {
        return $http.post('rest/users', user);
    }

    /**
     * Saves a given user using an asynchronous REST call with promise.
     * @param user The user to be saved.
     * @returns {HttpPromise}.
     */
        //TODO: SICHERHEIT, nicht direkt mit dem user/password
    this.saveUserWithPromise = function (user) {
        return $http.put('rest/users', user);
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
     * @param state The bug state to be saved.
     * @returns {HttpPromise}.
     */
    this.setBugStateWithPromise = function (bugId, state) {
        return $http.put('rest/bugs/' + bugId, state);
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
        return $http.get('rest/bugs/'+ bugId +'/comments');
    };

    /**
     * Saves a given comment using an asynchronous REST call with promise.
     * @param comment The comment to be saved.
     *        bug The bug in which the comment will be saved
     * @returns {HttpPromise}.
     */
    this.saveCommentWithPromise = function (bugId, comment) {
        return $http.put('rest/bugs/'+ bugId +'/comments', comment);
    };

}]);