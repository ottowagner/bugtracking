/**
 * The controllers.
 * Created by Stephan on 18.12.2014.
 */
'use strict';

var controllers = angular.module('controllers', ['resources', 'services']);

// Set up main controller.
controllers.controller('mainController', ['$scope', function ($scope) {
    // Set up the screens object
    $scope.screens = {
        mainScreen: ['mainScreen', 'mainScreen.html'],
        editRoomScreen: ['editRoomScreen', 'roomScreen.html']
    };

    // Set up the scope model
    $scope.model = {
        buglist: [],
        selectedRoom: null,
        screen: $scope.screens.mainScreen
    };

    /**
     * Switches to a specific screen.
     * @param newScreen The current screen (an array).
     */
    $scope.switchToScreen = function (newScreen) {
        if (angular.isArray(newScreen) && newScreen.length === 2) {
            $scope.model.screen = newScreen;
        }
    };

    /**
     * Returns the current screen.
     * @returns the corresponding array.
     */
    $scope.getCurrentScreen = function () {
        return $scope.model.screen;
    };

    /**
     * Returns the file name of the current screen.
     * @returns a string.
     */
    this.getCurrentScreenSource = function () {
        return $scope.model.screen[1];
    };
}]);

// Set up the list controller.
controllers.controller('listController', ['$scope', 'Room', 'roomService', function ($scope, Room, roomService) {
    /**
     * Selects a room.
     * @param selected The room to be selected.
     */
    this.selectRoom = function (selected) {
        $scope.model.selectedRoom = selected;
    };

    /**
     * Starts the editing of the room.
     * @param selected The room to be edited.
     */
    this.editRoom = function (selected) {
        this.selectRoom(selected);
        $scope.switchToScreen($scope.screens.editRoomScreen);
    };

    /**
     * Starts the creation of a new room.
     */
    this.newRoom = function () {
        $scope.model.selectedRoom = new Room('', '', 0, false);
        $scope.switchToScreen($scope.screens.editRoomScreen);
    };

    /**
     * Deletes the selected room.
     * @param selected The room to be deleted.
     */
    this.deleteRoom = function () {
        roomService.deleteRoomWithPromise($scope.model.selectedRoom)
            .then(function (response) {
                $scope.model.selectedRoom = null;
                return roomService.listRoomsWithPromise();
            })
            .then(function (response) {
                $scope.model.rooms = response.data;
            })
            .error(function (data, status, headers, config) {
                alert("an error occured while deleting");
            });
    };

    // List the current rooms.
    roomService.listRoomsWithPromise()
        .success(function (data, status, headers, config) {
            $scope.model.rooms = data;
        })
        .error(function (data, status, headers, config) {
            alert("an error occured while loading");
        });
}]);

// Set up the form controller.
controllers.controller('formController', ['$scope', 'Room', 'roomService', function ($scope, Room, roomService) {
    // Object containing the error messages.
    var messages = {
        errors: {
            required: 'Please enter a value!',
            number: 'Please enter a number!',
            min: 'The number is smaller than the minimum allowed!',
            unknown: 'Please enter a valid value!'
        }
    };

    // Set up the form model.
    $scope.formModel = {
        isEdit: $scope.model.selectedRoom.building && $scope.model.selectedRoom.roomNumber,
        formRoom: new Room($scope.model.selectedRoom.building, $scope.model.selectedRoom.roomNumber,
            $scope.model.selectedRoom.seats, $scope.model.selectedRoom.beamerPresent)
    };

    /**
     * Cancels the editing.
     */
    this.cancel = function () {
        $scope.switchToScreen($scope.screens.mainScreen);
    };

    /**
     * Saves the changes.
     * @param roomForm The form object of the room.
     */
    this.saveRoom = function (roomForm) {
        var selected = $scope.model.selectedRoom;
        var edited = $scope.formModel.formRoom;
        if (roomForm.$valid && selected && edited) {
            selected.building = edited.building;
            selected.roomNumber = edited.roomNumber;
            selected.seats = edited.seats;
            selected.beamerPresent = edited.beamerPresent;
            // do save data
            roomService.saveRoomWithPromise(selected)
                .success(function (data, status, headers, config) {
                    if ($scope.model.rooms.indexOf(selected) === -1) {
                        $scope.model.rooms.push(data);
                    }
                    $scope.switchToScreen($scope.screens.mainScreen);
                }).error(function (data, status, headers, config) {
                    alert("an error occured while saving");
                });
        }
    };

    /**
     * Returns the error message for the given element.
     * @param element The element.
     * @returns a string.
     */
    this.getErrorMessage = function (element) {
        var message = null;
        if (element.$error) {
            if (element.$error.required) {
                message = messages.errors.required;
            }
            else if (element.$error.number) {
                message = messages.errors.number;
            }
            else if (element.$error.min) {
                message = messages.errors.min;
            }
            else {
                message = messages.errors.unknown;
            }
        }
        return message;
    };

}]);