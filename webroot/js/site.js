// github api
;(function(){
var github = {
	domain : "https://api.github.com",
	api : {
		repos_get : "/repos/:owner/:repo",
		commits_info : "/repos/:owner/:repo/stats/commit_activity",
		commits_list : "/repos/:owner/:repo/commits",
		commits_view : "/repos/:owner/:repo/commits/:sha"
	},
	add : function(name, path){
		this.api[name] = path; return this;
	},
	uri : function(apiname, param, domain) {
		domain = domain || this.domain;
		var path = this.api[apiname];
		if(path == null || path == "") return null;
		$.map(param,function(value,key){ path = path.replace(key,value); });
		return domain + path;
	}
};

this.github = github;

})();

var Site = angular.module('Site', []);

Site.config(function ($routeProvider) {
	$routeProvider
	.when('/main', {templateUrl: 'main.html', controller: 'RouteController'})
	.when('/tree', {templateUrl: 'tree.html', controller: 'RouteController'})
	.when('/commit', {templateUrl: 'commit.html', controller: 'RouteController'})
	.otherwise({redirectTo: '/main'});
});

function AppController ($scope, $rootScope, $http) {
	// Load pages on startup
	//$http.get('pages.json').success(function (data) {
	//	$rootScope.pages = data;
	//});

	// Set the slug for menu active class
	$scope.$on('routeLoaded', function (event, args) {
		$scope.slug = args.slug;
	});
}

function RouteController ($scope, $route) {
	// Getting the slug from $routeParams
	var slug = $route.current.templateUrl.replace('.html','');

	$scope.$emit('routeLoaded', {slug: slug});
	//$scope.page = $rootScope.pages[slug];
}