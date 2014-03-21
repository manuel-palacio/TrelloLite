angular.module('app').controller 'LoginCtrl', ($scope, AuthenticationService) ->
  $scope.credentials = {username: "", password: ""}

  $scope.login = ->
    AuthenticationService.login($scope.credentials)


