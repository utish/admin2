'use strict';

angular.module('admin2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('visual', {
                parent: 'site',
                url: '/visual',
                data: {
                    authorities: []
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
