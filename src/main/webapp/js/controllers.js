/**
 * The controllers.
 * Created by Otto on 12.10.2015.
 */
'use strict';

var controllers = angular.module('controllers', ['resources', 'services', 'directives']);

// Set up the mainController controller.
controllers.controller('mainController', ['$rootScope', '$scope', '$http', '$location', 'authService', 'User', 'Bug', function ($rootScope, $scope, $http, $location, authService, User, Bug) {
    $rootScope.authModel = {
        user: null
    };

    $scope.bugModel = {
        bugs: [],
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
    this.login = function (loginForm) {
        //TODO: Hier eig mit Sicherheit arbeiten!!!

        //TODO:BÖSE! Aber muss sowieso überarbeitet werden... Security..
        var user = new User();
        user.email = loginForm.email.$modelValue;
        user.password = loginForm.password.$modelValue;

        if (loginForm.$valid && user) {
            authService.loadUserWithPromise(user)
                .success(function (data, status, headers, config) {
                    $rootScope.authenticated = true;
                    $location.path("/bugs");
                    $rootScope.authModel.user = data;
                })
                .error(function (data, status, headers, config) {
                    alert("an error occured while loading");
                    $rootScope.authenticated = false;
                    $scope.error = true;
                });
        }
        //authenticate($scope.account, function () {
        //    if ($rootScope.authenticated) {
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
                    $rootScope.authenticated = true;
                    $location.path("/bugs");
                    $rootScope.authModel.user = data;
                })
                .error(function (data, status, headers, config) {
                    alert("an error occured while loading");
                    $rootScope.authenticated = false;
                    $scope.error = true;
                });
        }
        //register($scope.account, function () {
        //    if ($rootScope.authenticated) {
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
        $rootScope.authenticated = false;
        $rootScope.authModel.user = null;
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
controllers.controller('editBugController', ['$rootScope', '$scope', '$location', '$routeParams', 'Bug', 'bugService', function ($rootScope, $scope, $location, $routeParams, Bug, bugService) {

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
        var user = $rootScope.authModel.user;
        if (bugForm.$valid && user && selected && edited) {
            selected.title = edited.title;
            selected.description = edited.description;
            selected.autor = user.id;
            bugService.saveBugWithPromise(selected)
                .success(function (data, status, headers, config) {
                    if ($scope.bugModel.bugs.indexOf(selected) === -1) {
                        $scope.bugModel.bugs.push(data);
                    }
                    $location.path("/bugs/" + data.id);
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
controllers.controller('showBugController', ['$rootScope', '$scope', '$location', '$routeParams', 'Bug', 'bugService', 'Comment', 'commentService', function ($rootScope, $scope, $location, $routeParams, Bug, bugService, Comment, commentService) {
    $scope.bugModel = {
        comments: [],
        editMode: null,
        selectedBug: null,
        editedBug: null,
        selectedComment: null,
        editedComment: null
    }

    if ($routeParams.bugId) {
        bugService.loadBugWithPromise($routeParams.bugId)
            .success(function (data, status, headers, config) {
                $scope.bugModel.selectedBug = data;
                $scope.bugModel.editedBug = data;
            }).error(function (data, status, headers, config) {
                $location.path("/bugs");
                alert("an error occured while loading");
            });

        commentService.listCommentsWithPromise($scope.bugModel.selectedBug)
            .success(function (data, status, headers, config) {
                $scope.bugModel.comments = data;
            })
            .error(function (data, status, headers, config) {
                alert("an error occured while loading");
            });
    }

    this.editBug = function () {
        $scope.bugModel.editMode = true;
        $location.path("/bugs/" + $routeParams.bugId + "/edit");
    }

    this.createComment = function () {
        var comment = new Comment();
        $scope.bugModel.editMode = false;
        $scope.bugModel.selectedComment = comment;
        $scope.bugModel.editedComment = new Comment(comment.id, comment.title, comment.description);
        $location.path("/bugs/" + $routeParams.bugId + "/comments/create");
    };

    this.saveComment = function (commentForm) {
        //var selected = $scope.bugModel.selectedComment;
        var comment = $scope.bugModel.editedComment;
        var user = $rootScope.authModel.user;
        var bug = $scope.bugModel.editedBug;
        if (commentForm.$valid && user && comment) {
            comment.bug = bug;
            comment.autor = user.id;
            commentService.saveCommentWithPromise(comment)
                .success(function (data, status, headers, config) {
                    if ($scope.bugModel.comments.indexOf(comment) === -1) {
                        $scope.bugModel.comments.push(data);
                    }
                    $location.path("/bugs/" + $routeParams.bugId);
                }).error(function (data, status, headers, config) {
                    alert("an error occured while saving");
                });
        }
    };

    /**
     * go to BugList
     */
    this.toBugList = function () {
        $location.path("/bugs");
        //history.back() evtl einbauen
    };

    /**
     * go to BugList
     */
    this.toBug = function () {
        history.back()
    };

}]);