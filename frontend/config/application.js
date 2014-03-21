// <your-application>/config/application.js

module.exports = require(process.env['LINEMAN_MAIN']).config.extend('application', {
    server: {
        pushState: false,
        apiProxy: {
            enabled: true,
            port: 8080
        }
    }, uglify: {
        options: {
            mangle: false
        }
    },
    concat_sourcemap: {
        js: {
          src: [
            "<%= files.js.vendor %>",
            "<%= files.coffee.generated %>",
            "<%= files.js.app %>",
            "<%= files.ngtemplates.dest %>"
          ]
        }
      }
});
