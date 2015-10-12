/**
 * The services.
 * Created by Stephan on 18.12.2014.
 */
'use strict';

var services = angular.module('services', ['resources']);

// Set up the room service.
services.service('roomService', ['$http', function ($http) {
	/**
	 * Return all rooms using an asynchronous REST call with promise.
	 * @returns {HttpPromise}.
	 */
    this.listRoomsWithPromise = function () {
        return $http.get('rest/rooms');
    };

	/**
	 * Saves a given room using an asynchronous REST call with promise.
	 * @param room The room to be saved.
	 * @returns {HttpPromise}.
	 */
    this.saveRoomWithPromise = function (room) {
        return $http.put('rest/rooms', room);
    };

	/**
	 * Deletes the given room using an asynchronous REST call with promise.
	 * @param room The room to be deleted.
	 * @returns {HttpPromise}.
	 */
	this.deleteRoomWithPromise = function (room) {
		return $http.delete('rest/rooms/' + room.building + '-' + room.roomNumber);
	}
}]);