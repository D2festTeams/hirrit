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

function RouteController ($scope, $rootScope, $routeParams) {
	// Getting the slug from $routeParams
	var slug = $routeParams.slug;

	$scope.$emit('routeLoaded', {slug: slug});
	//$scope.page = $rootScope.pages[slug];
}