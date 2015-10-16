/**
 * The controllers.
 * Created by Otto on 12.10.2015.
 */
'use strict';

var controllers = angular.module('controllers', ['resources', 'services', 'directives']);

// Set up the mainController controller.
controllers.controller('mainController', ['$rootScope', '$scope', '$http', '$location', 'Bug', function ($rootScope, $scope, $http, $location, Bug) {
    $scope.authModel = {
        account: []
    };

    $scope.bugModel = {
        bugs: [],
        comments: [],
        editMode: null,
        selectedBug: null,
        editedBug: null
    }

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
    this.login = function () {
        authenticate($scope.account, function () {
            if ($rootScope.authenticated) {
                $location.path("/bugs");
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

    this.register = function () {
        register($scope.account, function () {
            if ($rootScope.authenticated) {
                $location.path("/bugs");
                $scope.error = false;
            } else {
                $location.path("/register");
                $scope.error = true;
            }
        });
    };

    this.logout = function () {
        //TODO: In einen Service auslagern
        $rootScope.authenticated = false;
        $location.path("/login");
        //$http.post('logout', {}).success(function () {
        //    $rootScope.authenticated = false;
        //    $location.path("/login");
        //}).error(function (data) {
        //    $rootScope.authenticated = false;
        //    $location.path("/login");
        //});
    }


    /**
     * Starts the creation of a new bug.
     */
    this.createBug = function () {
        var bug = new Bug();
        $scope.bugModel.editMode = false;
        $scope.bugModel.selectedBug = bug;
        $scope.bugModel.editedBug = new Bug(bug.id, bug.title, bug.description, bug.state, bug.autor, bug.developer, bug.lastUpdateDate, bug.creationDate);
        $location.path("/bugs/create");
    };
}]);

// Set up the listBugController.
controllers.controller('listBugController', ['$scope', '$location', 'Bug', 'bugService', function ($scope, $location, Bug, bugService) {

    // List the current bugs.
    bugService.listBugsWithPromise()
        .success(function (data, status, headers, config) {
            $scope.bugModel.bugs = data;
        })
        .error(function (data, status, headers, config) {
            alert("an error occured while loading");
        });

    /**
     * Show the details and Starts the editing of the bug.
     * @param selected The bug to be showed / edited.
     */
    this.openBug = function (selected) {
        $location.path("/bugs/" + selected.id);
    };

}]);

// Set up the editBugController controller.
controllers.controller('editBugController', ['$scope', '$location', '$routeParams', 'Bug', 'bugService', function ($scope, $location, $routeParams, Bug, bugService) {
    if ($routeParams.bugId) {
        bugService.loadBugWithPromise($routeParams.bugId)
            .success(function (data, status, headers, config) {
                $scope.bugModel.selectedBug = data;
                $scope.bugModel.editedBug = new Bug(data.id, data.title, data.description, data.state, data.autor, data.developer, data.lastUpdateDate, data.creationDate);
            }).error(function (data, status, headers, config) {
                $location.path("/bugs");
                alert("an error occured while loading");
            });
    }

    /**
     * Saves the changes.
     * @param bugForm The form object of the room.
     */
    this.saveBug = function (bugForm) {
        var selected = $scope.bugModel.selectedBug;
        var edited = $scope.bugModel.editedBug;
        if (bugForm.$valid && selected && edited) {
            selected.title = edited.title;
            selected.description = edited.description;
            selected.state = edited.state;
            selected.autor = edited.autor;
            selected.developer = edited.developer;
            selected.lastUpdateDate = edited.lastUpdateDate;
            selected.creationDate = edited.creationDate;
            bugService.saveBugWithPromise(selected)
                .success(function (data, status, headers, config) {
                    if ($scope.bugModel.bugs.indexOf(selected) === -1) {
                        $scope.bugModel.bugs.push(data);
                        history.back()
                    }
                    //$scope.switchToScreen($scope.screens.mainScreen);
                }).error(function (data, status, headers, config) {
                    alert("an error occured while saving");
                });
        }
    };

    /**
     * go to BugList
     */
    this.toBugList = function () {
        history.back()
    };

// Object containing the error messages.
    var messages = {
        errors: {
            required: 'Please enter a value!',
            number: 'Please enter a number!',
            min: 'The number is smaller than the minimum allowed!',
            unknown: 'Please enter a valid value!'
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

// Set up the showBugController controller.
controllers.controller('showBugController', ['$scope', '$location', '$routeParams', 'Bug', 'bugService', function ($scope, $location, $routeParams, Bug, bugService) {
    bugService.loadBugWithPromise($routeParams.bugId)
        .success(function (data, status, headers, config) {
            $scope.bugModel.selectedBug = data;
            $scope.bugModel.editedBug = data;
        }).error(function (data, status, headers, config) {
            $location.path("/bugs");
            alert("an error occured while loading");
        });

    this.editBug = function () {
        $scope.bugModel.editMode = true;
        $location.path("/bugs/" + $routeParams.bugId + "/edit");
    }
    /**
     * go to BugList
     */
    this.toBugList = function () {
        $location.path("/bugs");
        //history.back() evtl einbauen
    };
}]);

// Set up the commentController.
controllers.controller('commentController', ['$scope', '$location', 'Bug', 'bugService', function ($scope, $location, Bug, Comment, commentService) {

    // List the current comments.
    commentService.listCommentsWithPromise(bug)
        .success(function (data, status, headers, config) {
            $scope.bugModel.comments = data;
        })
        .error(function (data, status, headers, config) {
            alert("an error occured while loading");
        });

    //this.saveComment = function (commentForm) {
    //    var selected = $scope.bugModel.selectedBug;
    //    var edited = $scope.bugModel.editedBug;
    //    if (bugForm.$valid && selected && edited) {
    //        selected.title = edited.title;
    //        selected.description = edited.description;
    //        selected.state = edited.state;
    //        selected.autor = edited.autor;
    //        selected.developer = edited.developer;
    //        selected.lastUpdateDate = edited.lastUpdateDate;
    //        selected.creationDate = edited.creationDate;
    //        bugService.saveBugWithPromise(selected)
    //            .success(function (data, status, headers, config) {
    //                if ($scope.bugModel.bugs.indexOf(selected) === -1) {
    //                    $scope.bugModel.bugs.push(data);
    //                    $location.path("/bugs");
    //                }
    //                //$scope.switchToScreen($scope.screens.mainScreen);
    //            }).error(function (data, status, headers, config) {
    //                alert("an error occured while saving");
    //            });
    //    }
    //};

}]);