var exec = require('cordova/exec');

var didiJK = {
  didiPluginFun: function ( successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'didiPlugin', 'didiPluginFun', []);
  }
};
module.exports = didiJK;
