import { Parse }     from 'parse/node';
import * as triggers from '../triggers';

function validateClassNameForTriggers(className) {
  const restrictedClassNames = [ '_Session' ];
  if (restrictedClassNames.indexOf(className) != -1) {
    throw `Triggers are not supported for ${className} class.`;
  }
  return className;
}

function getClassName(parseClass) {
  if (parseClass && parseClass.className) {
    return validateClassNameForTriggers(parseClass.className);
  }
  return validateClassNameForTriggers(parseClass);
}

var ParseCloud = {};
Parse.Cloud.define("pushNews", function(request, response) {
  var user = request.params.userObject;
  var pushTitle = request.params.pushTitle;
  var pushBody = request.params.pushBody;
  var pushtype = request.params.pushtype;
  var newsID = request.params.newsID;
  var phoneID = request.params.phoneID;
  var tabletID = request.params.tabletID;
  var channel = request.params.channel;
  var appName = request.params.appName;
  var pushTime = null;
  if(request.params.pushTime != null){
    pushTime = request.params.pushTime;
  }
 
  if(user != null && pushBody != null && pushBody != "" && pushTitle != null && pushTitle != ""){
    var query = new Parse.Query(Parse.Installation);
    query.contains('appIdentifier', 'express.wrapper');

    if (channel == "Beta") {
        query.equalTo('channels', "Beta");
    }

    Parse.Push.send({
                  where: query,
                  data: {
                    alert: pushBody,
                    title: pushTitle,
                    badge: 0,
                    'content-available': "1",
                     'sound': "0",
                    url: newsID,
                    link: phoneID,
                    link_tablet: tabletID,
                    "priority": "high"
                  },
                  push_time: (pushTime != null ? pushTime : new Date())
                },{
                  success: function(){
                    console.log("pushed "+pushTitle);
                    // push successfully sent..
                    response.success("All pushed !");
                     
                  }, 
                  error: function(err){
                     console.log("pushed error 1 "+err.message);
                    response.error("error pushing to channel " + err.message);
                  }
                });
  } else{
    console.log("pushed error 2 "+user);
    response.error("missing data");
  }
});


ParseCloud.define = function(functionName, handler, validationHandler) {
  triggers.addFunction(functionName, handler, validationHandler, Parse.applicationId);
};

ParseCloud.job = function(functionName, handler) {
  triggers.addJob(functionName, handler, Parse.applicationId);
};

ParseCloud.beforeSave = function(parseClass, handler) {
  var className = getClassName(parseClass);
  triggers.addTrigger(triggers.Types.beforeSave, className, handler, Parse.applicationId);
};

ParseCloud.beforeDelete = function(parseClass, handler) {
  var className = getClassName(parseClass);
  triggers.addTrigger(triggers.Types.beforeDelete, className, handler, Parse.applicationId);
};

ParseCloud.afterSave = function(parseClass, handler) {
  var className = getClassName(parseClass);
  triggers.addTrigger(triggers.Types.afterSave, className, handler, Parse.applicationId);
};

ParseCloud.afterDelete = function(parseClass, handler) {
  var className = getClassName(parseClass);
  triggers.addTrigger(triggers.Types.afterDelete, className, handler, Parse.applicationId);
};

ParseCloud.beforeFind = function(parseClass, handler) {
  var className = getClassName(parseClass);
  triggers.addTrigger(triggers.Types.beforeFind, className, handler, Parse.applicationId);
};

ParseCloud._removeAllHooks = () => {
  triggers._unregisterAll();
}

ParseCloud.httpRequest = require("./httpRequest");

module.exports = ParseCloud;
