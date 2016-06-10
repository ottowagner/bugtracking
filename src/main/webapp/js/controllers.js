/**
 * The controllers.
 */
'use strict';

var controllers = angular.module('controllers', ['resources', 'services', 'directives', 'filters']);

// Set up the mainController.
controllers.controller('mainController', ['$scope', '$location', 'userService', 'sessionService', 'User',
    'errorService', function ($scope, $location, userService, sessionService, User, errorService) {
        var mainController = this;

        $scope.mainModel = {
            session: sessionService,
            error: errorService
        };

        /**
         * load the current logged User with mainController creation
         */
        if (sessionService.isLoggedIn()) {
            userService.getUserWithPromise()
                .success(function (userData) {
                    sessionService.user = userData;
                }).error(function (data) {
                    sessionService.setLogIn(false);
                    sessionService.user = null;
                    errorService.setError(data.message);
                });
        }

        /**
         * close Error
         */
        this.closeError = function () {
            errorService.closeError();
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
                    error = errorService.errorMessages.errors.email;
                }
                else if (element.$error.required) {
                    error = errorService.errorMessages.errors.required;
                }
                else {
                    error = errorService.errorMessages.errors.unknown;
                }
            }
            return error;
        };

        /**
         * Login a User.
         * @param loginForm The login Form.
         */
        this.login = function (loginForm) {
            var user = new User();
            user.email = loginForm.email.$modelValue;
            user.password = loginForm.password.$modelValue;
            if (loginForm.$valid && user) {
                userService.getUserByMailWithPromise(user.email)
                    .success(function (userData) {
                        mainController.loginWithService(user, userData);
                    }).error(function (data) {
                        errorService.setError(data.message);
                    });
            } else {
                errorService.setError("Formular nicht vollständig!");
            }
        };

        /**
         * Register and login a User.
         * @param regForm The Register Form.
         */
        this.register = function (regForm) {
            var user = new User();
            user.email = regForm.email.$modelValue;
            user.password = regForm.password.$modelValue;
            user.firstname = regForm.firstname.$modelValue;
            user.lastname = regForm.lastname.$modelValue;
            if (regForm.$valid && user) {
                userService.saveUserWithPromise(user)
                    .success(function (userData) {
                        mainController.loginWithService(user, userData);
                    })
                    .error(function (data) {
                        errorService.setError(data.message);
                    });
            } else {
                errorService.setError("Formular nicht vollständig!");
            }
        };

        this.loginWithService = function (user, userData) {
            sessionService.loginWithPromise(user)
                .success(function () {
                    sessionService.setLogIn(true);
                    sessionService.user = userData;
                    $location.path("/bugs");
                    errorService.closeError();
                }).error(function () {
                    errorService.setError("Passwort fehlerhaft!");
                });
        };

        /**
         * logout a User.
         */
        this.logout = function () {
            sessionService.logoutWithPromise()
                .success(function () {
                    sessionService.setLogIn(false);
                    sessionService.user = null;
                    $location.path("/login");
                    errorService.closeError();
                }).error(function (data) {
                    errorService.setError(data.message);
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
                errorService.setError(data.message);
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
    'bugService', 'errorService', function ($scope, $location, $routeParams, sessionService, Bug, bugService,
                                            errorService) {
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
                    $scope.editBugModel.editedBug = new Bug(data.id, data.title, data.description, data.state,
                        data.author, data.developer, data.lastUpdateDate, data.creationDate);
                }).error(function (data) {
                    errorService.setError(data.message);
                    $location.path("/bugs");
                });
        } else {
            var bug = new Bug();
            $scope.editBugModel.editMode = false;
            $scope.editBugModel.selectedBug = bug;
            $scope.editBugModel.editedBug = new Bug(bug.id, bug.title, bug.description, bug.state, bug.author,
                bug.developer, bug.lastUpdateDate, bug.creationDate);
        }

        /**
         * Saves the changes.
         * @param bugForm The bugForm.
         */
        this.saveBug = function (bugForm) {
            var selected = $scope.editBugModel.selectedBug;
            var edited = $scope.editBugModel.editedBug;

            if (bugForm.$valid && selected && edited) {
                selected.title = edited.title;
                selected.description = edited.description;
                bugService.saveBugWithPromise(selected)
                    .success(function (data) {
                        $location.path("/bugs/" + data.id);
                        errorService.closeError();
                    }).error(function (data) {
                        errorService.setError(data.message);
                    });
            } else {
                errorService.setError("Fehler konnte nicht angelegt werden");
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
                /**
                 * load comments for the bug with showBugController creation
                 * @param $routeParams.bugId The bugId.
                 */
                commentService.listCommentsWithPromise($routeParams.bugId)
                    .success(function (data) {
                        $scope.showBugModel.comments = data;
                    })
                    .error(function (data) {
                        errorService.setError(data.message);
                    });

                /**
                 * load possible toStates for the bug with showBugController creation
                 * @param $routeParams.bugId The bugId.
                 */
                stateService.listToStatesWithPromise($routeParams.bugId)
                    .success(function (data) {
                        $scope.showBugModel.toStates = data;
                    }).error(function (data) {
                        errorService.setError(data.message);
                    });
            }).error(function (data) {
                errorService.setError(data.message);
                $location.path("/bugs");
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
             * load the states with commentController creation when stateId given
             */
            if ($routeParams.stateId) {
                bugService.loadBugWithPromise($routeParams.bugId)
                    .success(function (data) {
                        $scope.commentModel.fromState = data.state;
                        stateService.loadStateWithPromise($routeParams.stateId)
                            .success(function (data) {
                                $scope.commentModel.toState = data;
                            }).error(function (data) {
                                errorService.setError(data.message);
                                $location.path("/bugs/" + $routeParams.bugId);
                            });
                    }).error(function (data) {
                        errorService.setError(data.message);
                        $location.path("/bugs");
                    });
            }

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
                            errorService.setError(data.message);
                            $location.path("/bugs");
                        });
                } else {
                    errorService.setError("Formular nicht vollständig!");
                }
            };

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
                                .success(function () {
                                    errorService.closeError();
                                }).error(function (data) {
                                    errorService.setError(data.message);
                                });
                            $location.path("/bugs/" + $routeParams.bugId);
                        }).error(function (data) {
                            errorService.setError(data.message);
                            $location.path("/bugs/" + $routeParams.bugId);
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