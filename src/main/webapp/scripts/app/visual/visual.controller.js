'use strict';

angular.module('admin2App')
    .controller('VisualController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        
        
        
    });
