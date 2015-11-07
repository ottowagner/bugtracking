/**
 * The controllers.
 * Created by Otto on 12.10.2015.
 */
'use strict';

var controllers = angular.module('controllers', ['resources', 'services', 'directives', 'filters']);

// Set up the mainController.
controllers.controller('mainController', ['$scope', '$location', 'userService', 'sessionService', 'User', 'errorService',
    function ($scope, $location, userService, sessionService, User, errorService) {
        var mainController = this;

        $scope.mainModel = {
            authenticated: sessionService.isLoggedIn(),
            user: sessionService.user,
            error: errorService
        };

        //TODO: Auslagern in den Service
        var errorMessages = {
            errors: {
                required: 'Bitte angeben',
                email: 'Bitte eine Email-Adresse angeben',
                unknown: 'Bitte einen gültigen Wert angeben'
            }
        };

        /**
         * Returns the error message for the given element.
         * @param element The element.
         * @returns a string.
         */
        this.getErrorMessage = function (element) {
            var error = null;
            if (element.$error) {
                if (element.$error.email) {
                    error = errorMessages.errors.email;
                }
                else if (element.$error.required) {
                    error = errorMessages.errors.required;
                }
                else {
                    error = errorMessages.errors.unknown;
                }
            }
            return error;
        };

        /**
         * close Error
         */
        this.closeError = function () {
            errorService.closeError();
        };

        /**
         * load the current loggedin User with mainController creation
         */
        if ($scope.mainModel.authenticated) {
            //TODO USER LADEN! Sollte iwie anhand cookie gehen!
            //userService.getUserWithPromise()
            userService.getUserByMailWithPromise("otto-wagner@gmx.net")
                .success(function (userData) {
                    sessionService.user = userData;
                    $scope.mainModel.user = sessionService.user;
                }).error(function (data) {
                    errorService.setError(data);
                });
        }

        /**
         * Login a User.
         * @param loginForm The login Form.
         */
        this.login = function (loginForm) {
            var userForm = new User();
            userForm.email = loginForm.email.$modelValue;
            userForm.password = loginForm.password.$modelValue;
            userService.getUserByMailWithPromise(userForm.email)
                .success(function (userData) {
                    mainController.loginWithService(userForm, userData);
                }).error(function (data) {
                    //User do not exist
                    errorService.setError(data);
                });
        };

        this.loginWithService = function (userForm, userData) {
            sessionService.loginWithPromise(userForm)
                .success(function () {
                    sessionService.setLogIn(true);
                    sessionService.user = userData;
                    $scope.mainModel.user = sessionService.user;
                    $scope.mainModel.authenticated = sessionService.isLoggedIn();
                    $location.path("/bugs");
                    errorService.closeError();
                }).error(function (data) {
                    errorService.setError(data);
                });
        };

        /**
         * Register and login a User.
         * @param regForm The Register Form.
         */
        this.register = function (regForm) {
            var userForm = new User();
            userForm.email = regForm.email.$modelValue;
            userForm.password = regForm.password.$modelValue;
            userForm.firstname = regForm.firstname.$modelValue;
            userForm.lastname = regForm.lastname.$modelValue;
            if (regForm.$valid && userForm) {
                userService.saveUserWithPromise(userForm)
                    .success(function (userData) {
                        mainController.loginWithService(userForm, userData);
                    })
                    .error(function (data) {
                        errorService.setError(data);
                    });
            } else {
                alert("Formular nicht vollständig!");
            }
        };

        /**
         * logout a User.
         */
        this.logout = function () {
            sessionService.logoutWithPromise()
                .success(function () {
                    sessionService.setLogIn(false);
                    $scope.mainModel.authenticated = sessionService.isLoggedIn();
                    $scope.mainModel.user = null;
                    $location.path("/login");
                    errorService.closeError();
                }).error(function (data) {
                    errorService.setError(data);
                });
        };

        /**
         * go to Login
         */
        this.toLogin = function () {
            $location.path("/login");
            errorService.closeError();
        };

        /**
         * go to Register
         */
        this.toRegister = function () {
            $location.path("/register");
            errorService.closeError();
        };
    }]);

// Set up the listBugController.
controllers.controller('listBugController', ['$scope', '$location', 'bugService', 'errorService',
    function ($scope, $location, bugService, errorService) {
        $scope.listBugModel = {
            bugs: [],
            sortType: 'id',
            sortReverse: false
        };

        /**
         * load the current Bugs with listBugController creation
         */
        bugService.listBugsWithPromise()
            .success(function (data) {
                $scope.listBugModel.bugs = data;
            })
            .error(function (data) {
                errorService.setError(data);
            });

        /**
         * Sort by a sortType.
         * @param sortType The sortType to sort.
         */
        this.sortBy = function (sortType) {
            $scope.listBugModel.sortType = sortType;
            $scope.listBugModel.sortReverse = !$scope.listBugModel.sortReverse;
        };

        /**
         * Go to create bug page.
         */
        this.createBug = function () {
            $location.path("/bugs/create");
            errorService.closeError();
        };

        /**
         * Show the details and of the bug.
         * @param selected The bug to be showed / edited.
         */
        this.openBug = function (selected) {
            $location.path("/bugs/" + selected.id);
            errorService.closeError();
        };

    }])
;

// Set up the editBugController.
controllers.controller('editBugController', ['$scope', '$location', '$routeParams', 'sessionService', 'Bug',
    'bugService', 'errorService', function ($scope, $location, $routeParams, sessionService, Bug, bugService, errorService) {
        $scope.editBugModel = {
            editMode: null,
            selectedBug: null,
            editedBug: null
        };

        /**
         * if $routeParams.bugId given load bug, else create new bug
         * @param $routeParams.bugId The bugId to be loaded.
         */
        if ($routeParams.bugId) {
            bugService.loadBugWithPromise($routeParams.bugId)
                .success(function (data) {
                    $scope.editBugModel.editMode = true;
                    $scope.editBugModel.selectedBug = data;
                    $scope.editBugModel.editedBug = new Bug(data.id, data.title, data.description, data.state, data.autor,
                        data.developer, data.lastUpdateDate, data.creationDate);
                }).error(function (data) {
                    errorService.setError(data);
                    $location.path("/bugs");
                });
        } else {
            var bug = new Bug();
            $scope.editBugModel.editMode = false;
            $scope.editBugModel.selectedBug = bug;
            $scope.editBugModel.editedBug = new Bug(bug.id, bug.title, bug.description, bug.state, bug.autor,
                bug.developer, bug.lastUpdateDate, bug.creationDate);
        }

        /**
         * Saves the changes.
         * @param bugForm The bugForm.
         */
        this.saveBug = function (bugForm) {
            var selected = $scope.editBugModel.selectedBug;
            var edited = $scope.editBugModel.editedBug;
            var user = sessionService.user;

            if (bugForm.$valid && user && selected && edited) {
                selected.title = edited.title;
                selected.description = edited.description;
                selected.autor = user; //TODO: BACKEND!
                bugService.saveBugWithPromise(selected)
                    .success(function (data) {
                        $location.path("/bugs/" + data.id);
                        errorService.closeError();
                    }).error(function (data) {
                        errorService.setError(data);
                    });
            } else {
                errorService.setError("Fehler beim anlegen");
            }
        };

        /**
         * go back
         */
        this.pageBack = function () {
            history.back();
            errorService.closeError();
        };

    }]);

// Set up the showBugController.
controllers.controller('showBugController', ['$scope', '$location', '$routeParams', 'bugService', 'stateService',
    'commentService', 'errorService', function ($scope, $location, $routeParams, bugService, stateService,
                                                commentService, errorService) {
        $scope.showBugModel = {
            bug: null,
            comments: [],
            toStates: []
        };

        /**
         * load the current Bug with showBugController creation
         */
        bugService.loadBugWithPromise($routeParams.bugId)
            .success(function (data) {
                $scope.showBugModel.bug = data;
            }).error(function (data) {
                errorService.setError(data);
                $location.path("/bugs");
            });

        /**
         * load comments for the bug with showBugController creation
         * @param $routeParams.bugId The bugId.
         */
        commentService.listCommentsWithPromise($routeParams.bugId)
            .success(function (data) {
                $scope.showBugModel.comments = data;
            })
            .error(function (data) {
                errorService.setError(data);
            });

        /**
         * load possible toStates for the bug with showBugController creation
         * @param $routeParams.bugId The bugId.
         */
        stateService.listToStatesWithPromise($routeParams.bugId)
            .success(function (data) {
                $scope.showBugModel.toStates = data;
            }).error(function (data) {
                errorService.setError(data);
            });
        /**
         * go to changeState
         */
        this.toChangeState = function (state) {
            $location.path("/bugs/" + $routeParams.bugId + "/state/change/" + state.id);
            errorService.closeError();
        };

        /**
         * go to createComment
         */
        this.createComment = function () {
            $location.path("/bugs/" + $routeParams.bugId + "/comments/create");
            errorService.closeError();
        };

        /**
         * go to bugList
         */
        this.toBugList = function () {
            $location.path("/bugs");
            errorService.closeError();
        };

        /**
         * go to editBug
         */
        this.toEditBug = function () {
            $location.path("/bugs/" + $routeParams.bugId + "/edit");
            errorService.closeError();
        };

        /**
         * go to showBug
         */
        this.toShowBug = function () {
            $location.path("/bugs/" + $routeParams.bugId);
            errorService.closeError();
        };

    }]);

// Set up the commentController.
controllers.controller('commentController',
    ['$scope', '$location', '$routeParams', 'bugService', 'commentService', 'Comment', 'stateService', 'errorService',
        function ($scope, $location, $routeParams, bugService, commentService, Comment, stateService, errorService) {
            $scope.commentModel = {
                comment: null,
                fromState: null,
                toState: null
            };

            /**
             * Saves the comment.
             * @param commentForm The commentForm.
             */
            this.saveComment = function (commentForm) {
                var comment = $scope.commentModel.comment;

                if (commentForm.$valid && comment) {
                    commentService.saveCommentWithPromise($routeParams.bugId, comment)
                        .success(function () {
                            $location.path("/bugs/" + $routeParams.bugId);
                            errorService.closeError();
                        }).error(function (data) {
                            errorService.setError(data);
                        });
                } else {
                    errorService.setError("Formular nicht vollständig!");
                }
            };

            /**
             * load the states with commentController creation when stateId given
             */
            if ($routeParams.stateId) {
                bugService.loadBugWithPromise($routeParams.bugId)
                    .success(function (data) {
                        $scope.commentModel.fromState = data.state;
                    }).error(function (data) {
                        errorService.setError(data);
                        $location.path("/bugs");
                    });

                stateService.loadStateWithPromise($routeParams.stateId)
                    .success(function (data) {
                        $scope.commentModel.toState = data;
                    }).error(function (data) {
                        errorService.setError(data);
                        $location.path("/bugs/" + $routeParams.bugId);
                    });
            }

            /**
             * Change State for Bug.
             * @param stateForm The stateForm.
             */
            this.changeStateWithComment = function (stateForm) {
                var comment = new Comment();
                comment.fromState = $scope.commentModel.fromState.title;
                comment.toState = $scope.commentModel.toState.title;

                if ($scope.commentModel.comment) {
                    comment.title = $scope.commentModel.comment.title;
                    comment.description = $scope.commentModel.comment.description;
                }
                if (stateForm.$valid && comment) {
                    bugService.setBugStateWithPromise($routeParams.bugId, $routeParams.stateId)
                        .success(function (data) {
                            commentService.saveCommentWithPromise($routeParams.bugId, comment)
                                .success(function (data) {
                                    $location.path("/bugs/" + $routeParams.bugId);
                                    errorService.closeError();
                                }).error(function (data) {
                                    errorService.setError(data);
                                });
                        }).error(function (data) {
                            errorService.setError(data);
                        });
                } else {
                    errorService.setError("Formular nicht vollständig!");
                }
            };

            /**
             * go to showBug
             */
            this.toShowBug = function () {
                $location.path("/bugs/" + $routeParams.bugId);
                errorService.closeError();
            };

        }])
;