/**
 * Created by seabao on 15/12/10.
 */
define(['angular', 'tiWebUtil'], function (angular) {
        'use strict';

        // define controllers module
        var angularAppControllers = angular.module('angularAppControllers', []);

        // async load controller js src
        tiWebUtil.loadAmdControllers(angularAppControllers, [{
        	name: 'FirstController', src: './first-controller'
        }]);
    }
);