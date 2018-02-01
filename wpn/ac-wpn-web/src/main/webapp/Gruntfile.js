module.exports = function(grunt) {
	var requirejsOptions = {};

	var matches = grunt.file.expand('app/*/js/main.js');
	if (matches.length > 0) {
		for (var x = 0; x < matches.length; x++) {
			var path = matches[x].replace(/\/main\.js/, '');
			var jsFiles = grunt.file.expand(path + '/**/*.js');
			var deps = [];
			for (var i = 0; i < jsFiles.length; i++) {
				var dep = jsFiles[i].substring(path.length + 1);
				dep = dep.substring(0, dep.length - 3);
				if (dep == 'main' || dep == 'main.min') {
					continue;
				}

				deps.push(dep);
			}

			requirejsOptions['task' + x] = {
				"options": {
					"baseUrl": path,
					"wrap": true,
					"name": "main",
					"out": path + "/main.min.js",
					"optimize": "uglify2",
					"uglify2": {
						"mangle": false
					},
					"paths": {
						'tiweb/ti-web-require-ext': 'empty:',
						'tiweb/ti-web-util': 'empty:',
						'tiweb': 'empty:',
						'tiWebUtil': 'empty:',
						'jquery': 'empty:',
						'angular': 'empty:',
						'ngRoute': 'empty:',
						'ngTouch': 'empty:',
						'ngAnimate': 'empty:',
						'ngRoute' : 'empty:',
						'ngSanitize' : 'empty:',
						'uiBootstrap' : 'empty:',
						'ngDialog' : 'empty:',
						'uiRouter': 'empty:',
						'uiRouterTabs': 'empty:'
					},
					"deps": deps,
					"findNestedDependencies": true,
					"done": function (done, output) {
						done();
					}
				}
			};
		}
	};

	var objPackage = grunt.file.readJSON('package.json');
	grunt.initConfig({
		pkg: objPackage,
		requirejs: requirejsOptions
	});

	grunt.loadNpmTasks('grunt-contrib-requirejs');
	grunt.registerTask('default', ['requirejs']);
};