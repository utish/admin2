'use strict';

angular.module('admin2App')
    .controller('RegionController', function ($scope, Region) {
        $scope.regions = [];
        $scope.loadAll = function() {
            Region.query(function(result) {
               $scope.regions = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Region.get({id: id}, function(result) {
                $scope.region = result;
                $('#deleteRegionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Region.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRegionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.region = {
                code: null,
                description: null,
                id: null
            };
        };
    });
