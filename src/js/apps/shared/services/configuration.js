var app = angular.module("alchemytec.config", []);

// Some environmental specific config options
var gulpEnvConfig = { /*{{gulp-env-config}}*/ };

app.constant("config", {
	apiroot: 'http://localhost:8081/challenge/api',
	staticRoot: gulpEnvConfig.staticRoot
});
