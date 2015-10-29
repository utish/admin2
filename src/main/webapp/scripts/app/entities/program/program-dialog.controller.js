'use strict';

angular.module('admin2App').controller('ProgramDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Program',
        function($scope, $stateParams, $modalInstance, entity, Program) {

        $scope.program = entity;
        $scope.load = function(id) {
            Program.get({id : id}, function(result) {
                $scope.program = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('admin2App:programUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.program.id != null) {
                Program.update($scope.program, onSaveFinished);
            } else {
                Program.save($scope.program, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
