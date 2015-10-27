'use strict';

angular.module('admin2App').controller('RegionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Region',
        function($scope, $stateParams, $modalInstance, entity, Region) {

        $scope.region = entity;
        $scope.load = function(id) {
            Region.get({id : id}, function(result) {
                $scope.region = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('admin2App:regionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.region.id != null) {
                Region.update($scope.region, onSaveFinished);
            } else {
                Region.save($scope.region, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
