angular.module('app').directive 'chart', ->
  parseDataForCharts = (sourceArray, sourceProp, referenceArray, referenceProp) ->
    data = []

    referenceArray.each((r) ->
      count = sourceArray.count((s) ->
        s[sourceProp] == r[referenceProp])
      data.push([r[referenceProp], count]))

    data

  linker = (scope, element, attrs) ->
    scope.data = parseDataForCharts(scope.sourceArray, attrs['sourceProp'], scope.referenceArray,
      attrs['referenceProp'])
    if(element.is(":visible"))
      $.plot(element, [scope.data],
        { series: { bars: { show: true, barWidth: 0.6, align: "center" } }, xaxis: { mode: "categories", tickLength: 0 } })

  restrict: 'A'
  link: linker
  scope: { sourceArray: '=', referenceArray: '=' }