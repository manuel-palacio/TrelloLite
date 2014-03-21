angular.module('app').factory 'FlashService', ($rootScope) ->
  class FlashService
    constructor: ->
      $rootScope.alert = null

    show: (message) ->
      $rootScope.alert = message

    clear: ->
      $rootScope.alert = null


  new FlashService()


