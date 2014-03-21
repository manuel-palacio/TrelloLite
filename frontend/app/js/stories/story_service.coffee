angular.module('app').factory 'StoryService', ($q, $http, $rootScope, $resource, FlashService) ->
  class StoryService

    getStories: ->
      later = $q.defer()
      $http.get("/resources/stories").success (result) ->
        later.resolve result
      later.promise


    saveStory: (story) ->
      $http.post("/resources/stories", story).success((result) ->
        $rootScope.$broadcast("storyChanged", ""))


    updateStory: (story) ->
      $http.put("/resources/stories/#{story.id}", story).success((result) ->
        FlashService.show({type: "success", content: "Story updated"}))


    deleteStory: (id) ->
      $http.delete("/resources/stories/#{id}").success((result) ->
        $rootScope.$broadcast("storyChanged", ""))


    getStatuses: ->
      [
        {name: 'Back Log'},
        {name: 'To Do'},
        {name: 'In Progress'},
        {name: 'Verified'},
        {name: 'Done'}
      ]


    getTypesAndColors: ->
      [
        {name: 'Feature', color: 'success'},
        {name: 'Enhancement', color: 'info'},
        {name: 'Bug', color: 'danger'},
        {name: 'Spike', color: 'warning'}
      ]

  new StoryService()





