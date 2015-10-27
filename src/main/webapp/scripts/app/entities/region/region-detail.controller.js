'use strict';

angular.module('admin2App')
    .controller('RegionDetailController', function ($scope, $rootScope, $stateParams, entity, Region) {
        $scope.region = entity;
        $scope.load = function (id) {
            Region.get({id: id}, function(result) {
                $scope.region = result;
            });
        };
        $rootScope.$on('admin2App:regionUpdate', function(event, result) {
            $scope.region = result;
        });
    });
