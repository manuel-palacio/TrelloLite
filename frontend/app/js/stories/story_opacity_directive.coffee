angular.module('app').directive 'hoverable', ->
  linker = (scope, element, attrs) ->
    element.mouseover(->
      element.css({ 'opacity': 0.7 }))
    .mouseout(->
        element.css({ 'opacity': 1.0 }))

  restrict: 'A'
  link: linker



