angular.module("app").config  ($routeProvider, $httpProvider) ->


    isLoggedIn = ($q, $timeout, $http, $location, $rootScope, FlashService) ->
        deferred = $q.defer();

        $http.get('/auth/loggedIn').success( (resp) ->
            if (resp != '0')
                $rootScope.loggedIn = true
                deferred.resolve()
            else
                FlashService.show({type: "warning", content: "You need to log in "})
                deferred.reject()
                $rootScope.loggedIn = false
                $location.url('/login')

        ).error((resp) ->
                FlashService.show({type: "error", content: "Could not connect to the server to authenticate the user " + resp.data});

            )

        deferred.promise

    logsOutUserOn401 =  ($rootScope, $location, $q, FlashService) ->

        success = (response) -> response

        error =  (response) ->
            if (response.status == 401)
                $rootScope.loggedIn = false
                $location.path('/login')
                FlashService.show({type: 'danger', content: 'You need to log in'})

            $q.reject(response)

        (promise) -> promise.then(success, error)

    $httpProvider.responseInterceptors.push(logsOutUserOn401)


    $routeProvider
        .when('/story', {
            templateUrl: 'story.html',
            controller: 'StoryCtrl',
            resolve: {
                loggedIn: isLoggedIn
            }
        })
        .when('/login', {
            templateUrl: 'login.html',
            controller: 'LoginCtrl'
        })
        .when('/dashboard', {
            templateUrl: 'dashboard.html',
            controller: 'DashboardCtrl',
            resolve: {
                loggedIn: isLoggedIn
            }
        })
        .otherwise({
            redirectTo: '/story'
        })

.run(($rootScope, $http) ->

      $rootScope.logout = -> $http.post('/auth/logout')

      $rootScope.log =  (thing) -> console.log(thing)

      $rootScope.alert = (thing) -> alert(thing))
