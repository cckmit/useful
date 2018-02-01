/**
 * Created by jeff.xiao on 15/12/15.
 */

'use strict';

require.config({
    baseUrl: 'js',
    packages: [{
    	name: 'tiweb',
    	location: "../../bower_components/ti-web-js",
    	main: 'ti-web-main.min'
    }],
    paths: {
        'jquery': '../../bower_components/jquery/dist/jquery',
        'angular': '../../bower_components/angular/angular',
        'ngTouch': '../../bower_components/angular-touch/angular-touch',
        'ngAnimate': '../../bower_components/angular-animate/angular-animate',
        'uiRouter': '../../bower_components/angular-ui-router/release/angular-ui-router',
        'uiRouterTabs': '../../bower_components/angular-ui-router-tabs/src/ui-router-tabs',
        'ngSanitize': '../../bower_components/angular-sanitize/angular-sanitize',
        'ngTranslate': '../../bower_components/angular-sanitize/angular-translate',
        'uiBootstrap': '../../bower_components/angular-bootstrap/ui-bootstrap-tpls',        
        'pnotify': '../../bower_components/pnotify/dist/pnotify'
    },
    shim: {
        angular: {
            exports: 'angular'
        },
        uiRouter: {
            deps: ['angular'],
            exports: 'uiRouter'
        },
        uiRouterTabs: {
            deps: ['uiRouter'],
            exports: 'uiRouterTabs'
        },
        ngTouch: {
            deps: ['angular'],
            exports: 'ngTouch'
        },
        ngAnimate: {
            deps: ['angular'],
            exports: 'ngAnimate'
        },
        ngTranslate: {
            deps: ['angular'],
            exports: 'ngTranslate'
        },
        ngSanitize: {
            deps: ['angular'],
            exports: 'ngSanitize'
        }
    }
});

require(['tiweb/ti-web-require-ext'], function() {
    require(['angular'], function(angular) {
        require(['tiweb/ti-web-util'], function (util) {        	
        	require(['./app'], function(app) {
        		require(tiWebUtil.getAmdDefineIds(), function (app) {        			
                    app.init();
                });
        	})
            
        });
    });
});
