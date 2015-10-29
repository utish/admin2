'use strict';

angular.module('admin2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('program', {
                parent: 'entity',
                url: '/programs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Programs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/program/programs.html',
                        controller: 'ProgramController'
                    }
                },
                resolve: {
                }
            })
            .state('program.detail', {
                parent: 'entity',
                url: '/program/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Program'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/program/program-detail.html',
                        controller: 'ProgramDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Program', function($stateParams, Program) {
                        return Program.get({id : $stateParams.id});
                    }]
                }
            })
            .state('program.new', {
                parent: 'program',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/program/program-dialog.html',
                        controller: 'ProgramDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('program', null, { reload: true });
                    }, function() {
                        $state.go('program');
                    })
                }]
            })
            .state('program.edit', {
                parent: 'program',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/program/program-dialog.html',
                        controller: 'ProgramDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Program', function(Program) {
                                return Program.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('program', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
