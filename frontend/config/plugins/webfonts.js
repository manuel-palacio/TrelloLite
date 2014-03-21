module.exports = function (lineman) {
    return {
        config: {
            webfonts: {

                files: {
                    "vendor/fonts/": "<%= files.webfonts.vendor %>"
                },

                root: "<%= files.webfonts.root %>",
                dev: {
                    dest: "generated"
                },

                dist: {
                    dest: "dist"
                }
            }
        }
    }
};