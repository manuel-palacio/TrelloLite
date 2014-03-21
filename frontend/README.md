# An AngularJS app that uses Lineman

[![Build Status](https://palace.ci.cloudbees.com/buildStatus/icon?job=StoryFrontend)](https://palace.ci.cloudbees.com/job/StoryFrontend/)



Small first citizen web application showing the different parts of an AngularJS application: controllers, factories, directives, filters using Coffeescript
It also shows how to do simple authentication and illustrates the use of a REST backend
Uses [Lineman](http://www.linemanjs.com) as a productivity tool, totally decoupling the front and backend parts.

Note: the repository story-backend is a Grails Rest app that needs to be run together with the client. It relies on mongodb so you need to have it installed.



# Instructions

1. `git clone https://github.com/manuel-palacio/story-client`
2. `cd my-lineman-app`
3. `sudo npm install -g lineman`
4. `npm install`
5. `lineman run`
6. open your web browser to localhost:8000

# Running Tests

To run the unit tests:

1. `lineman run` from 1 terminal window
2. `lineman spec` from another terminal window, this will launch Testem and execute specs in Chrome


# Defining your apps angular.module in CoffeeScript

If you are using Coffeescript to define the angular.module for your app, you will need to swap the concat order in `config/application.js` such that coffeescript files are included _before_ javascript, here's a sample config. (If you are using JavaScript for defining the angular.module the default concat order is fine).

```javascript
js: {
  // if using coffeescript and your angular.module is defined in a .coffee file, files.coffee.generated comes first
  src: ["<%= files.js.vendor %>", "<%= files.coffee.generated %>", "<%= files.js.app %>", "<%= files.ngtemplates.dest %>"],
  dest: "<%= files.js.concatenated %>"
},
```
