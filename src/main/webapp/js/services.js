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
	 * Saves a given room using an asynchronous REST call with promise.
	 * @param bug The bug to be saved.
	 * @returns {HttpPromise}.
	 */
    this.saveBugWithPromise = function (bug) {
        return $http.put('rest/bugs', bug);
    };

	/**
	 * Deletes the given room using an asynchronous REST call with promise.
	 * @param bug The bug to be deleted.
	 * @returns {HttpPromise}.
	 */
	this.deleteBugWithPromise = function (bug) {
		return $http.delete('rest/bugs/' + bug.id);
	}
}]);