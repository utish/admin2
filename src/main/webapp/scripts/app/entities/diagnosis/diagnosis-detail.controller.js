'use strict';

angular.module('admin2App')
    .controller('DiagnosisDetailController', function ($scope, $rootScope, $stateParams, entity, Diagnosis) {
        $scope.diagnosis = entity;
        $scope.load = function (id) {
            Diagnosis.get({id: id}, function(result) {
                $scope.diagnosis = result;
            });
        };
        $rootScope.$on('admin2App:diagnosisUpdate', function(event, result) {
            $scope.diagnosis = result;
        });
    });
