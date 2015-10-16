/**
 * The services.
 * Created by Otto on 13.10.2015.
 */
'use strict';

var services = angular.module('services', ['resources']);

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

    ///**
    // * Deletes the given bug using an asynchronous REST call with promise.
    // * @param bug The bug to be deleted.
    // * @returns {HttpPromise}.
    // */
    //this.deleteBugWithPromise = function (bug) {
    //	return $http.delete('rest/bugs/' + bug.id);
    //}
}]);

// Set up the comment service.
services.service('commentService', ['$http', function ($http) {
    /**
     * Return all comment for a bug using an asynchronous REST call with promise.
     * @param bug The bug in which the comment has been saved
     * @returns {HttpPromise}.
     */
    this.listCommentsWithPromise = function (bug) {
        return $http.get('rest/comments', bug);
    };

    /**
     * Saves a given comment using an asynchronous REST call with promise.
     * @param comment The comment to be saved.
     *        bug The bug in which the comment will be saved
     * @returns {HttpPromise}.
     */
    this.saveCommentWithPromise = function (comment, bug) {
        return $http.put('rest/comments', [comment, bug]);
    };

    ///**
    // * Deletes the given bug using an asynchronous REST call with promise.
    // * @param bug The bug to be deleted.
    // * @returns {HttpPromise}.
    // */
    //this.deleteBugWithPromise = function (bug) {
    //	return $http.delete('rest/bugs/' + bug.id);
    //}
}]);