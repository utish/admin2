'use strict';

angular.module('admin2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('diagnosis', {
                parent: 'entity',
                url: '/diagnosiss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Diagnosiss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/diagnosis/diagnosiss.html',
                        controller: 'DiagnosisController'
                    }
                },
                resolve: {
                }
            })
            .state('diagnosis.detail', {
                parent: 'entity',
                url: '/diagnosis/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Diagnosis'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/diagnosis/diagnosis-detail.html',
                        controller: 'DiagnosisDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Diagnosis', function($stateParams, Diagnosis) {
                        return Diagnosis.get({id : $stateParams.id});
                    }]
                }
            })
            .state('diagnosis.new', {
                parent: 'diagnosis',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/diagnosis/diagnosis-dialog.html',
                        controller: 'DiagnosisDialogController',
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
                        $state.go('diagnosis', null, { reload: true });
                    }, function() {
                        $state.go('diagnosis');
                    })
                }]
            })
            .state('diagnosis.edit', {
                parent: 'diagnosis',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/diagnosis/diagnosis-dialog.html',
                        controller: 'DiagnosisDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Diagnosis', function(Diagnosis) {
                                return Diagnosis.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('diagnosis', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
