'use strict';

angular.module('admin2App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


