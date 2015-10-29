'use strict';

angular.module('admin2App')
    .controller('ProgramDetailController', function ($scope, $rootScope, $stateParams, entity, Program) {
        $scope.program = entity;
        $scope.load = function (id) {
            Program.get({id: id}, function(result) {
                $scope.program = result;
            });
        };
        $rootScope.$on('admin2App:programUpdate', function(event, result) {
            $scope.program = result;
        });
    });
