'use strict';

angular.module('admin2App')
    .controller('ProgramController', function ($scope, Program) {
        $scope.programs = [];
        $scope.loadAll = function() {
            Program.query(function(result) {
               $scope.programs = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Program.get({id: id}, function(result) {
                $scope.program = result;
                $('#deleteProgramConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Program.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProgramConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.program = {
                code: null,
                description: null,
                id: null
            };
        };
    });
