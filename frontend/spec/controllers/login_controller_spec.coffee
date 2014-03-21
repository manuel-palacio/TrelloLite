describe "controller: LoginController ($httpBackend.when().respond, jasmine-given, coffeescript)", ->
  Given ->
    module("app")

  Given inject ($controller, $rootScope, $location, AuthenticationService, $httpBackend) ->
    @scope = $rootScope.$new()
    @$httpBackend = $httpBackend
    @redirect = spyOn($location, 'path')
    $controller('LoginCtrl', {$scope: @scope, $location, AuthenticationService})

  Invariant ->
    @$httpBackend.verifyNoOutstandingRequest()
    @$httpBackend.verifyNoOutstandingExpectation()

  describe "when a user successfully logs in", ->
    Given ->
      @$httpBackend.whenPOST('/auth/logIn', {credentials: {username: "", password: ""}}).respond(200)
    When ->
      @scope.login()
    When ->
      @$httpBackend.flush()
    Then "LoginController should redirect you to /home", ->
      expect(@redirect).toHaveBeenCalledWith('/story')


  describe "could not log in", ->
    Given ->
      @$httpBackend.expectPOST('/auth/logIn', {credentials: {username: "", password: ""}}).respond(401)
    When ->
      @scope.login()
    When ->
      @$httpBackend.flush()
    Then "should redirect you to /login", ->
      expect(@redirect).toHaveBeenCalledWith('/login');
