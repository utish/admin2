'use strict';

angular.module('admin2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('region', {
                parent: 'entity',
                url: '/regions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Regions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/region/regions.html',
                        controller: 'RegionController'
                    }
                },
                resolve: {
                }
            })
            .state('region.detail', {
                parent: 'entity',
                url: '/region/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Region'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/region/region-detail.html',
                        controller: 'RegionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Region', function($stateParams, Region) {
                        return Region.get({id : $stateParams.id});
                    }]
                }
            })
            .state('region.new', {
                parent: 'region',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/region/region-dialog.html',
                        controller: 'RegionDialogController',
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
                        $state.go('region', null, { reload: true });
                    }, function() {
                        $state.go('region');
                    })
                }]
            })
            .state('region.edit', {
                parent: 'region',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/region/region-dialog.html',
                        controller: 'RegionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Region', function(Region) {
                                return Region.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('region', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
