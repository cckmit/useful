/**
 * Created by seabao on 15/12/10.
 */

define(
    ['angular', 'jquery', 'ngTouch', 'ngAnimate', 'uiRouter',
        'ngSanitize', 'uiBootstrap', 'tiWebUtil', 'uiRouterTabs',
        './controllers', 'tiweb'],
    function (angular, jquery) {
        'use strict';

        // define root app module
        var angularApp = angular.module('angularApp', [
            'ui.router',
            'ui.bootstrap',
            'ui.router.tabs',
            'angularAppControllers',
            'tiWebUtilFilters',
            'tiweb.components'
        ]);

        // async load route.js
        tiWebUtil.loadAmdModuleConfig(angularApp, './route');
        
        return {
            init: function () {
            	// load remote permission and then startup the angular app;
                jquery.get('../permissions', function(principal) {
                	console.log("loaded remote permissions [" + principal.permissions + "]");
                	angularApp.config(function(authzProvider) {                		
                		authzProvider.setPermissions(principal.permissions);        		
                    });
                	angular.bootstrap(document, ['angularApp']);
                    console.log("The angularApp booted.");
                });
                
            }
        };
    }
);