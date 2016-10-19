var exec = require('cordova/exec');

var didiJK = {

  didiPluginFun: function (str, successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'didiPlugin', 'didiPluginFun', [str]);
  }
};
module.exports = didiJK;
