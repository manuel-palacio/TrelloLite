describe "service: FlashService", ->
  Given ->
    module("app")

  Given inject (@$rootScope, @FlashService) ->
    @rootScope = $rootScope
    @flashService = FlashService


  describe "can set flash message", ->
    When ->
      @flashService.show("hello")
    Then "should set a message", ->
      expect(@rootScope.alert).toBe('hello')

  describe "can clear flash message", ->
    When ->
      @flashService.clear();
    Then "should clear a message", ->
      expect(@rootScope.alert).toBe(null)
