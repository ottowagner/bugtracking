/**
 * The controllers.
 * Created by Stephan on 18.12.2014.
 */
'use strict';

var controllers = angular.module('controllers', ['resources', 'services']);

// Set up main controller.
controllers.controller('mainController', ['$rootScope', '$scope', '$http', '$location', function ($rootScope, $scope, $http, $location) {
    console.log("mainController");
    // Set up the scope model
    $scope.model = {
        bugs: [],
        selectedBug: null
    };

    var authenticate = function (account, callback) {
//TODO: In einen Service auslagern

        //var headers = account ? {
        //    authorization: "Basic "
        //    + btoa(account.username + ":" + account.password)
        //} : {};
        var accountData = account ? {
            authorization: account.username + ":" + account.password
        } : {};

        if (accountData.authorization) {
            $rootScope.authenticated = true;
        } else {
            $rootScope.authenticated = false;
        }
        callback && callback();
        //$http.get('rest/user', {headers: headers}).success(function (data) {
        //    console.log(data);
        //    if (data.id) {
        //        $rootScope.authenticated = true;
        //    } else {
        //        $rootScope.authenticated = false;
        //    }
        //    callback && callback();
        //}).error(function () {
        //    $rootScope.authenticated = false;
        //    callback && callback();
        //});
    }

    //authenticate();
    $scope.account = {};
    $scope.login = function () {
        authenticate($scope.account, function () {
            if ($rootScope.authenticated) {
                $location.path("/");
                $scope.error = false;
            } else {
                $location.path("/login");
                $scope.error = true;
            }
        });
    };


    var register = function (account, callback) {
//TODO: In einen Service auslagern

        var headers = account ? {
            authorization: "Basic "
            + btoa(account.username + ":" + account.password)
        } : {};
        $rootScope.authenticated = true;
        callback && callback();
        //$http.post('rest/user', {headers: headers}).success(function (data) {
        //    console.log(data);
        //    if (data.id) {
        //        $rootScope.authenticated = true;
        //    } else {
        //        $rootScope.authenticated = false;
        //    }
        //    callback && callback();
        //}).error(function () {
        //    $rootScope.authenticated = false;
        //    callback && callback();
        //});
    }

    $scope.register = function () {
        register($scope.account, function () {
            if ($rootScope.authenticated) {
                $location.path("/");
                $scope.error = false;
            } else {
                $location.path("/register");
                $scope.error = true;
            }
        });
    };

    $scope.logout = function () {
        //TODO: In einen Service auslagern
        $http.post('logout', {}).success(function () {
            $rootScope.authenticated = false;
            $location.path("/login");
        }).error(function (data) {
            $rootScope.authenticated = false;
        });
    }

}]);

// Set up the list controller.
controllers.controller('bugListController', ['$scope', '$location', 'Bug', 'bugService', function ($scope, $location, Bug, bugService) {
    console.log("bugListController");
    /**
     * Selects a bug.
     * @param selected The bug to be selected.
     */
    $scope.selectBug = function (selected) {
        $scope.model.selectedBug = selected;
    };

    /**
     * Show the details and Starts the editing of the bug.
     * @param selected The bug to be edited.
     */
    $scope.openBug = function (selected) {
        this.selectedBug(selected);
        console.log("openBug");
        //$scope.switchToScreen($scope.screens.editRoomScreen);
    };

    /**
     * Starts the creation of a new bug.
     */
    $scope.createBug = function () {
        console.log("createBug");
        $scope.model.selectedBug = new Bug();

        //$scope.switchToScreen($scope.screens.editRoomScreen);
    };

    /**
     * Deletes the selected bug.
     * @param selected The bug to be deleted.
     */
    $scope.deleteBug = function () {
        bugService.deleteBugWithPromise($scope.model.selectedBug)
            .then(function (response) {
                $scope.model.selectedBug = null;
                return bugService.listBugsWithPromise();
            })
            .then(function (response) {
                $scope.model.bugs = response.data;
            })
            .error(function (data, status, headers, config) {
                alert("an error occured while deleting");
            });
    };

    // List the current bugs.
    bugService.listBugsWithPromise()
        .success(function (data, status, headers, config) {
            $scope.model.bugs = data;
        })
        .error(function (data, status, headers, config) {
            alert("an error occured while loading");
        });
}]);

// Set up the form controller.
controllers.controller('formController', ['$scope', '$location', 'Bug', 'bugService', function ($scope, $location, Bug, bugService) {
    console.log("formController");
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
        isEdit: $scope.model.selectedBug.id,
        formBug: new Bug($scope.model.selectedBug.title, $scope.model.selectedBug.state, $scope.model.selectedBug.autor,
            $scope.model.selectedBug.developer, $scope.model.selectedBug.lastUpdateDate, $scope.model.selectedBug.creationDate)
    };

    /**
     * Cancels the editing.
     */
    $scope.cancel = function () {
        $location.path("/bugList");
    };

    /**
     * Saves the changes.
     * @param bugForm The form object of the room.
     */
    $scope.saveBug = function (bugForm) {
        var selected = $scope.model.selectedBug;
        var edited = $scope.formModel.formBug;
        if (bugForm.$valid && selected && edited) {
            selected.title = edited.title;
            selected.state = edited.state;
            selected.autor = edited.autor;
            selected.developer = edited.developer;
            selected.lastUpdateDate = edited.lastUpdateDate;
            selected.creationDate = edited.creationDate;
            // do save data
            bugService.saveBugWithPromise(selected)
                .success(function (data, status, headers, config) {
                    if ($scope.model.bugs.indexOf(selected) === -1) {
                        $scope.model.bugs.push(data);
                        $location.path("/bugList");
                    }
                    //$scope.switchToScreen($scope.screens.mainScreen);
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