'use strict';

angular.module('admin2App')
    .factory('Diagnosis', function ($resource, DateUtils) {
        return $resource('api/diagnosiss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
