angular.module('app').controller 'DashboardCtrl', ($scope, StoryService) ->
  $scope.types = StoryService.getTypesAndColors()
  $scope.statuses = StoryService.getStatuses()

  StoryService.getStories().then (result) ->
      $scope.stories = result
      $scope.dataReady = true
