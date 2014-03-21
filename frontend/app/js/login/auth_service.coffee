angular.module('app').factory 'AuthenticationService', ($http, $location, $rootScope, FlashService) ->
  class AuthenticationService
    success: ->
      FlashService.show({type: "success", content: 'Authentication successful!'})
      $location.url('/story')


    error: ->
      FlashService.show({type: "warning", content: 'Authentication failed.'})
      $location.url('/login')

    login: (credentials) ->
      $rootScope.log("Logging in with " + credentials.username);
      $http.post('/auth/logIn', {credentials: credentials}).success(@success).error(@error)


  new AuthenticationService()
