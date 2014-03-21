angular.module('app').directive 'sortable', (StoryService) ->

  linker = (scope, element, attrs) ->
          status = scope.status.name
          element.sortable({
              items: 'li',
              connectWith: ".list",
              receive: (event, ui) ->
                  prevScope = angular.element(ui.item.prev()).scope()
                  curScope = angular.element(ui.item).scope()
                  scope.$apply(->
                      StoryService.updateStory(curScope.story)
                      curScope.story.status = status)

          })


  restrict: 'A'
  link: linker



