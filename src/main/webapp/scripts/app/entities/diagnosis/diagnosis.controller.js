'use strict';

angular.module('admin2App')
    .controller('DiagnosisController', function ($scope, Diagnosis) {
        $scope.diagnosiss = [];
        $scope.loadAll = function() {
            Diagnosis.query(function(result) {
               $scope.diagnosiss = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Diagnosis.get({id: id}, function(result) {
                $scope.diagnosis = result;
                $('#deleteDiagnosisConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Diagnosis.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDiagnosisConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.diagnosis = {
                code: null,
                description: null,
                id: null
            };
        };
    });
