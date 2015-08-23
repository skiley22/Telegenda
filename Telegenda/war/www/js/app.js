angular.module('telegendaApp', [
	'ngRoute',
	'telegendaControllers',
	'ng.httpLoader',
	'pageFilter'
])

.config(['httpMethodInterceptorProvider',
  function (httpMethodInterceptorProvider) {
  httpMethodInterceptorProvider.whitelistDomain('appspot.com');
 }
])

;