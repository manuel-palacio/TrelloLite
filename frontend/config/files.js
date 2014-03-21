/* Exports an object that defines
 *  all of the paths & globs that the project
 *  is concerned with.
 *
 * The "configure" task will require this file and
 *  then re-initialize the grunt config such that
 *  directives like <config:files.js.app> will work
 *  regardless of the point you're at in the build
 *  lifecycle.
 *
 * You can find the parent object in: node_modules/lineman/config/files.coffee
 */

module.exports = function (lineman) {
    //Override file patterns here
    return {
        js: {
            vendor: [
                "vendor/js/jquery.js",
                "vendor/js/jquery-ui.js",
                "vendor/js/jquery.ui.sortable.js",
                "vendor/js/jquery.flot.js",
                "vendor/js/jquery.flot.categories.js",
                "vendor/js/angular.js",
                "vendor/js/bootstrap.js",
                "vendor/js/angular-strap.js",
                "vendor/js/**/*.js"
            ],
            app: [
                "app/js/app.js",
                "app/js/**/*.js"
            ]
        },

        less: {
            compile: {
                options: {
                    paths: ["vendor/css/normalize.css", "vendor/css/**/*.css", "app/css/**/*.less"]
                }
            }
        }
    };
};
