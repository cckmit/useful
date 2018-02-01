define(['tiWebUtil'],
    function(tiWebUtil) {
        return ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $urlRouterProvider.otherwise("/index");
                $stateProvider
                .state('index', tiWebUtil.prepareUiRouterState({
                    url: '/index',
                    templateUrl: 'html/index.html',
                    controller: 'FirstController'
                }));
            }];
    }
);