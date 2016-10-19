var exec = require('cordova/exec');

var didiJK = {

  didiPluginFun(str): function ( successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'didiPlugin', 'didiPluginFun', [str]);
  }
};
module.exports = didiJK;
