/**
 * The controllers.
 * Created by Otto on 12.10.2015.
 */
'use strict';

var controllers = angular.module('controllers', ['resources', 'services', 'directives', 'filters']);

// Set up the mainController.
controllers.controller('mainController', ['$scope', '$location', 'userService', 'sessionService', 'User', function ($scope, $location, userService, sessionService, User) {
    $scope.mainModel = {
        authenticated: sessionService.isLoggedIn(),
        error: "",
        showError: false
    };

    this.closeError = function () {
        $scope.mainModel.error = "";
        $scope.mainModel.showError = false;
    };

    this.login = function (loginForm) {
        var user = new User();
        user.email = loginForm.email.$modelValue;
        user.password = loginForm.password.$modelValue;
        userService.userExistsWithPromise(user.email)
            .success(function (userData, status, headers, config) {
                sessionService.loginWithPromise(user)
                    .success(function (data, status, headers, config) {
                        sessionService.user = userData;
                        localStorage.setItem("session", {});
                        $scope.mainModel.authenticated = sessionService.isLoggedIn();
                        $location.path("/bugs");
                    }).error(function (data, status, headers, config) {
                        $scope.mainModel.error = "Session kacke";
                        $scope.mainModel.showError = true;
                    });
            }).error(function (data, status, headers, config) {
                $scope.mainModel.error = "Benutzer nicht vorhanden";
                $scope.mainModel.showError = true;
            });
    };

    this.register = function (registForm) {
        var user = new User();
        user.email = registForm.email.$modelValue;
        user.password = registForm.password.$modelValue;
        user.firstname = registForm.firstname.$modelValue;
        user.lastname = registForm.lastname.$modelValue;
        if (registForm.$valid && user) {
            userService.saveUserWithPromise(user)
                .success(function (userData, status, headers, config) {
                    sessionService.loginWithPromise(user)
                        .success(function (data, status, headers, config) {
                            sessionService.user = userData;
                            localStorage.setItem("session", {});
                            $scope.mainModel.authenticated = sessionService.isLoggedIn();
                            $location.path("/bugs");
                        }).error(function (data, status, headers, config) {
                            $scope.mainModel.error = "Session kacke";
                            $scope.mainModel.showError = true;
                        });
                })
                .error(function (data, status, headers, config) {
                    $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
                    $scope.mainModel.showError = true;
                    $scope.error = true;
                });
        }
    };

    this.logout = function () {
        sessionService.logout();
        $scope.mainModel.authenticated = sessionService.isLoggedIn();
    }

}]);

// Set up the listBugController.
controllers.controller('listBugController', ['$scope', '$location', 'bugService', function ($scope, $location, bugService) {
    $scope.bugModel = {
        bugs: []
    };

    $scope.sortType = 'id';
    $scope.sortReverse = false;

    // List the current bugs.
    bugService.listBugsWithPromise()
        .success(function (data, status, headers, config) {
            $scope.bugModel.bugs = data;
        })
        .error(function (data, status, headers, config) {
            $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
            $scope.mainModel.showError = true;
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
controllers.controller('editBugController', ['$scope', '$location', '$routeParams', 'sessionService', 'Bug', 'bugService', function ($scope, $location, $routeParams, sessionService, Bug, bugService) {

    $scope.bugModel = {
        editMode: null,
        selectedBug: null,
        editedBug: null
    };

    if ($routeParams.bugId) {
        bugService.loadBugWithPromise($routeParams.bugId)
            .success(function (data, status, headers, config) {
                $scope.bugModel.editMode = true;
                $scope.bugModel.selectedBug = data;
                $scope.bugModel.editedBug = new Bug(data.id, data.title, data.description, data.state, data.autor, data.developer, data.lastUpdateDate, data.creationDate);
            }).error(function (data, status, headers, config) {
                $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
                $scope.mainModel.showError = true;
                $location.path("/bugs");
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
        var user = sessionService.user;

        if (bugForm.$valid && user && selected && edited) {
            selected.title = edited.title;
            selected.description = edited.description;
            selected.autor = user;
            bugService.saveBugWithPromise(selected)
                .success(function (data, status, headers, config) {
                    $location.path("/bugs/" + data.id);
                }).error(function (data, status, headers, config) {
                    $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
                    $scope.mainModel.showError = true;
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
controllers.controller('showBugController', ['$scope', '$location', '$routeParams', 'bugService', 'stateService', 'commentService', 'dataService', function ($scope, $location, $routeParams, bugService, stateService, commentService, dataService) {
    $scope.bugModel = {
        bug: null,
        comments: [],
        toStates: []
    };

    bugService.loadBugWithPromise($routeParams.bugId)
        .success(function (data, status, headers, config) {
            $scope.bugModel.bug = data;
        }).error(function (data, status, headers, config) {
            $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
            $scope.mainModel.showError = true;
            $location.path("/bugs");
        });

    commentService.listCommentsWithPromise($routeParams.bugId)
        .success(function (data, status, headers, config) {
            $scope.bugModel.comments = data;
        })
        .error(function (data, status, headers, config) {
            $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
            $scope.mainModel.showError = true;
        });

    stateService.listToStatesWithPromise($routeParams.bugId)
        .success(function (data, status, headers, config) {
            $scope.bugModel.toStates = data;
        }).error(function (data, status, headers, config) {
            $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
            $scope.mainModel.showError = true;
            $location.path("/bugs");
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
controllers.controller('commentController', ['$scope', '$location', '$routeParams', 'sessionService', 'bugService', 'commentService', 'Comment', "dataService", 'stateService', function ($scope, $location, $routeParams, sessionService, bugService, commentService, Comment, dataService, stateService) {
    $scope.commentModel = {
        comment: null,
        fromState: dataService.fromState,
        toState: dataService.toState
    };
//TODO: Prüfe ob im dataService richtige bugnr und statenr steht, sonst lade diese!
    if (!dataService.fromState) {
        bugService.loadBugWithPromise($routeParams.bugId)
            .success(function (data, status, headers, config) {
                dataService.fromState = data.state;
                $scope.commentModel.fromState = dataService.fromState;
            }).error(function (data, status, headers, config) {
                $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
                $scope.mainModel.showError = true;
                $location.path("/bugs");
            });
    }
    if (!dataService.toState && $routeParams.stateId) {
        stateService.loadStateWithPromise($routeParams.stateId)
            .success(function (data, status, headers, config) {
                dataService.toState = data;
                $scope.commentModel.toState = dataService.toState;
            }).error(function (data, status, headers, config) {
                $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
                $scope.mainModel.showError = true;
            });
    }

    this.saveComment = function (commentForm) {
        var comment = $scope.commentModel.comment;
        var user = sessionService.user;

        if (commentForm.$valid && user && comment) {
            comment.autor = user; //TODO: BACKEND!
            commentService.saveCommentWithPromise($routeParams.bugId, comment)
                .success(function (data, status, headers, config) {
                    $location.path("/bugs/" + $routeParams.bugId);
                }).error(function (data, status, headers, config) {
                    $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
                    $scope.mainModel.showError = true;
                });
        } else {
            alert("Fehler abfangen!");
        }
    };

    this.changeState = function (stateForm) {
        var comment = new Comment();
        comment.fromState = dataService.fromState.title; //TODO: evtl im backend prüfen
        comment.toState = dataService.toState.title; //TODO: evtl im backend prüfen
        comment.autor = sessionService.user; //TODO: BACKEND!

        if ($scope.commentModel.comment) {
            comment.title = $scope.commentModel.comment.title;
            comment.description = $scope.commentModel.comment.description;
        }

        bugService.setBugStateWithPromise($routeParams.bugId, $routeParams.stateId)
            .success(function (data, status, headers, config) {
            }).error(function (data, status, headers, config) {
                $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
                $scope.mainModel.showError = true;
            });
        commentService.saveCommentWithPromise($routeParams.bugId, comment)
            .success(function (data, status, headers, config) {
            }).error(function (data, status, headers, config) {
                $scope.mainModel.error = "ein Fehler bein laden! -- Hier muss ein Text ausn Backend angezeigt werden";
                $scope.mainModel.showError = true;
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