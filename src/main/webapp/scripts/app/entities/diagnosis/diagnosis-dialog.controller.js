'use strict';

angular.module('admin2App').controller('DiagnosisDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Diagnosis',
        function($scope, $stateParams, $modalInstance, entity, Diagnosis) {

        $scope.diagnosis = entity;
        $scope.load = function(id) {
            Diagnosis.get({id : id}, function(result) {
                $scope.diagnosis = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('admin2App:diagnosisUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.diagnosis.id != null) {
                Diagnosis.update($scope.diagnosis, onSaveFinished);
            } else {
                Diagnosis.save($scope.diagnosis, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
