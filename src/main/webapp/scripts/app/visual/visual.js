'use strict';

angular.module('admin2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('visualization', {
                parent: 'site',
                url: '/visual',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Hospitalization'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/visual/visual.html',
                        controller: 'VisualController'
                    }
                },
                resolve: {
                    
                }
            });
    });
