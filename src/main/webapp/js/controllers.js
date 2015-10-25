/**
 * The controllers.
 * Created by Otto on 12.10.2015.
 */
'use strict';

var controllers = angular.module('controllers', ['resources', 'services', 'directives']);

// Set up the mainController.
controllers.controller('mainController', ['$scope', '$location', 'authService', 'User', function ($scope, $location, authService, User) {
    $scope.mainModel = {
        authenticated: authService.authenticated
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
            authService.authenticated = true;
        } else {
            authService.authenticated = false;
        }
        $scope.mainModel.authenticated = authService.authenticated;
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
    //$scope.account = {};
    this.login = function (loginForm) {
        //TODO: Hier eig mit Sicherheit arbeiten!!!

        //TODO:BÖSE! Aber muss sowieso überarbeitet werden... Security..
        var user = new User();
        user.email = loginForm.email.$modelValue;
        user.password = loginForm.password.$modelValue;

        if (loginForm.$valid && user) {
            authService.loadUserWithPromise(user)
                .success(function (data, status, headers, config) {
                    authService.authenticated = true;
                    $scope.mainModel.authenticated = authService.authenticated;
                    $location.path("/bugs");
                    authService.user = data;
                })
                .error(function (data, status, headers, config) {
                    alert("an error occured while loading");
                    authService.authenticated = false;
                    $scope.mainModel.authenticated = authService.authenticated;
                    $scope.error = true;
                });

        }
        //authenticate($scope.account, function () {
        //    if (authService.authenticated) {
        //        $location.path("/bugs");
        //        $scope.error = false;
        //    } else {
        //        $location.path("/login");
        //        $scope.error = true;
        //    }
        //});
    };


    var register = function (account, callback) {
        //TODO: Hier eig mit Sicherheit arbeiten!!!
        var headers = account ? {
            authorization: "Basic "
            + btoa(account.username + ":" + account.password)
        } : {};
        authService.authenticated = true;
        $scope.mainModel.authenticated = authService.authenticated;
        callback && callback();
        //$http.post('rest/user', {headers: headers}).success(function (data) {
        //    console.log(data);
        //    if (data.id) {
        //        authService.authenticated = true;
        //    } else {
        //        authService.authenticated = false;
        //    }
        //    callback && callback();
        //}).error(function () {
        //    authService.authenticated = false;
        //    callback && callback();
        //});
    }

    this.register = function (registForm) {
        //TODO: Hier eig mit Sicherheit arbeiten!!!
        //TODO:BÖSE! Aber muss sowieso überarbeitet werden... Security..
        var user = new User();
        user.email = registForm.email.$modelValue;
        user.password = registForm.password.$modelValue;
        user.firstname = registForm.firstname.$modelValue;
        user.lastname = registForm.lastname.$modelValue;
        if (registForm.$valid && user) {
            authService.saveUserWithPromise(user)
                .success(function (data, status, headers, config) {
                    authService.authenticated = true;
                    $location.path("/bugs");
                    authService.user = data;
                })
                .error(function (data, status, headers, config) {
                    alert("an error occured while loading");
                    authService.authenticated = false;
                    $scope.error = true;
                });
        }
        $scope.mainModel.authenticated = authService.authenticated;
        //register($scope.account, function () {
        //    if (authService.authenticated) {
        //        $location.path("/bugs");
        //        $scope.error = false;
        //    } else {
        //        $location.path("/register");
        //        $scope.error = true;
        //    }
        //});
    };

    this.logout = function () {
        //TODO: Hier eig mit Sicherheit arbeiten!!!
        authService.authenticated = false;
        $scope.mainModel.authenticated = authService.authenticated;
        authService.user = null;
        $location.path("/login");
        //$http.post('logout', {}).success(function () {
        //    authService.authenticated = false;
        //    $location.path("/login");
        //}).error(function (data) {
        //    authService.authenticated = false;
        //    $location.path("/login");
        //});
    }

}]);

// Set up the listBugController.
controllers.controller('listBugController', ['$scope', '$location', 'bugService', function ($scope, $location, bugService) {
    $scope.bugModel = {
        bugs: []
    }

    // List the current bugs.
    bugService.listBugsWithPromise()
        .success(function (data, status, headers, config) {
            $scope.bugModel.bugs = data;
        })
        .error(function (data, status, headers, config) {
            alert("an error occured while loading");
        });

    /**
     * Starts the creation of a new bug.
     */
    this.createBug = function () {
        $location.path("/bugs/create");
    };

    /**
     * Show the details and Starts the editing of the bug.
     * @param selected The bug to be showed / edited.
     */
    this.openBug = function (selected) {
        $location.path("/bugs/" + selected.id);
    };

}]);

// Set up the editBugController.
controllers.controller('editBugController', ['$scope', '$location', '$routeParams', 'authService', 'Bug', 'bugService', function ($scope, $location, $routeParams, authService, Bug, bugService) {

    $scope.bugModel = {
        editMode: null,
        selectedBug: null,
        editedBug: null
    }

    if ($routeParams.bugId) {
        bugService.loadBugWithPromise($routeParams.bugId)
            .success(function (data, status, headers, config) {
                $scope.bugModel.editMode = true;
                $scope.bugModel.selectedBug = data;
                $scope.bugModel.editedBug = new Bug(data.id, data.title, data.description, data.state, data.autor, data.developer, data.lastUpdateDate, data.creationDate);
            }).error(function (data, status, headers, config) {
                $location.path("/bugs");
                alert("an error occured while loading");
            });
    } else {
        var bug = new Bug();
        $scope.bugModel.editMode = false;
        $scope.bugModel.selectedBug = bug;
        $scope.bugModel.editedBug = new Bug(bug.id, bug.title, bug.description, bug.state, bug.autor, bug.developer, bug.lastUpdateDate, bug.creationDate);
    }

    /**
     * Saves the changes.
     * @param bugForm The form object of the room.
     */
    this.saveBug = function (bugForm) {
        var selected = $scope.bugModel.selectedBug;
        var edited = $scope.bugModel.editedBug;
        var user = authService.user;
        if (bugForm.$valid && user && selected && edited) {
            selected.title = edited.title;
            selected.description = edited.description;
            selected.autor = user;
            bugService.saveBugWithPromise(selected)
                .success(function (data, status, headers, config) {
                    $location.path("/bugs/" + data.id);
                }).error(function (data, status, headers, config) {
                    alert("an error occured while saving");
                });
        } else {
            alert("Fehler abfangen!");
        }
    };

    /**
     * go to BugList
     */
    this.pageBack = function () {
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

// Set up the showBugController.
controllers.controller('showBugController', ['$scope', '$location', '$routeParams', 'authService', 'bugService', 'stateService', 'commentService', 'dataService', function ($scope, $location, $routeParams, authService, bugService, stateService, commentService, dataService) {
    $scope.bugModel = {
        bug: null,
        comments: [],
        toStates: []
    };

    bugService.loadBugWithPromise($routeParams.bugId)
        .success(function (data, status, headers, config) {
            $scope.bugModel.bug = data;
        }).error(function (data, status, headers, config) {
            $location.path("/bugs");
            alert("an error occured while loading");
        });

    commentService.listCommentsWithPromise($routeParams.bugId)
        .success(function (data, status, headers, config) {
            $scope.bugModel.comments = data;
        })
        .error(function (data, status, headers, config) {
            alert("an error occured while loading");
        });

    stateService.listToStatesWithPromise($routeParams.bugId)
        .success(function (data, status, headers, config) {
            $scope.bugModel.toStates = data;
        }).error(function (data, status, headers, config) {
            $location.path("/bugs");
            alert("an error occured while loading");
        });

    this.toChangeState = function (state) {
        dataService.fromState = $scope.bugModel.bug.state;
        dataService.toState = state;
        $location.path("/bugs/" + $routeParams.bugId + "/state/change/" + state.id);
    };

    this.createComment = function () {
        $location.path("/bugs/" + $routeParams.bugId + "/comments/create");
    };

    /**
     * go to bugList
     */
    this.toBugList = function () {
        $location.path("/bugs");
        //history.back() evtl einbauen
    };

    /**
     * go to editBug
     */
    this.toEditBug = function () {
        $location.path("/bugs/" + $routeParams.bugId + "/edit");
    };

    /**
     * go to showBug
     */
    this.toShowBug = function () {
        $location.path("/bugs/" + $routeParams.bugId);
    };

}]);

// Set up the commentController.
controllers.controller('commentController', ['$scope', '$location', '$routeParams', 'authService', 'bugService', 'commentService', 'Comment', "dataService", 'stateService', function ($scope, $location, $routeParams, authService, bugService, commentService, Comment, dataService, stateService) {
    $scope.commentModel = {
        comment: null,
        fromState: dataService.fromState,
        toState: dataService.toState
    };

    if (!dataService.fromState) {
        bugService.loadBugWithPromise($routeParams.bugId)
            .success(function (data, status, headers, config) {
                var bug = data;
                dataService.fromState = bug.state;
                $scope.commentModel.fromState = dataService.fromState;
            }).error(function (data, status, headers, config) {
                $location.path("/bugs");
                alert("an error occured while loading");
            });
    }
    if (!dataService.toState && $routeParams.stateId) {
        stateService.loadStateWithPromise($routeParams.stateId)
            .success(function (data, status, headers, config) {
                dataService.toState = data;
                $scope.commentModel.toState = dataService.toState;
            }).error(function (data, status, headers, config) {
                alert("an error occured while saving");
            });
    }

    this.saveComment = function (commentForm) {
        var comment = $scope.commentModel.comment;
        var user = authService.user;

        if (commentForm.$valid && user && comment) {
            comment.autor = user; //TODO: BACKEND!
            commentService.saveCommentWithPromise($routeParams.bugId, comment)
                .success(function (data, status, headers, config) {
                    $location.path("/bugs/" + $routeParams.bugId);
                }).error(function (data, status, headers, config) {
                    alert("an error occured while saving");
                });
        } else {
            alert("Fehler abfangen!");
        }
    };

    this.changeState = function (stateForm) {
        var comment = new Comment();
        comment.fromState = dataService.fromState.title; //TODO: evtl im backend prüfen
        comment.toState = dataService.toState.title; //TODO: evtl im backend prüfen
        comment.autor = authService.user; //TODO: BACKEND!

        if ($scope.commentModel.comment) {
            comment.title = $scope.commentModel.comment.title;
            comment.description = $scope.commentModel.comment.description;
        };

        commentService.saveCommentWithPromise($routeParams.bugId, comment)
            .success(function (data, status, headers, config) {
            }).error(function (data, status, headers, config) {
                alert("an error occured while saving");
            });
        bugService.setBugStateWithPromise($routeParams.bugId, $routeParams.stateId)
            .success(function (data, status, headers, config) {
            }).error(function (data, status, headers, config) {
                alert("an error occured while saving");
            });

        dataService.fromState = "";
        dataService.toState = "";
        $location.path("/bugs/" + $routeParams.bugId);
    };

    /**
     * go to showBug
     */
    this.toShowBug = function () {
        dataService.fromState = "";
        dataService.toState = "";
        $location.path("/bugs/" + $routeParams.bugId);
    };

}]);

//controllers.controller('mymodalcontroller', function ($scope) {
//    $scope.header = 'Put here your header';
//    $scope.body = 'Put here your body';
//    $scope.footer = 'Put here your footer';
//
//    $scope.myRightButton = function (bool) {
//        alert('!!! first function call!');
//    };
//});