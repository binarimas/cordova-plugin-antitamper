var exec = require('cordova/exec');

exports.getHashValue = function(success, error) {
    exec(success, error, "AntiTamperPlugin", "getHashValue", []);
};
