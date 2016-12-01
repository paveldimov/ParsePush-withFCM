//////////////////////////////////////////////////////////////////////
// a cloud push function for pushing scheduled or unscheduled messages
//////////////////////////////////////////////////////////////////////
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
	console.log("TEST");
	//user != null && pushBody != null && pushBody != "" && pushTitle != null && pushTitle != ""       <-- statement 
  var test =true ; 
  if(test){
    var query = new Parse.Query(Parse.Installation,{ useMasterKey: true });
    //query.contains('appIdentifier', 'express.wrapper');
	console.log("PAVEL!");
    if (channel == "Beta") {
        query.equalTo('channels', "Beta");
    }
	

	
			
			Parse.Push.send({
				channels: ["Live"],
				//notification: {
				//	"body":pushBody ,
				//}
				data: {
                    "alert": pushBody,
					"title": pushTitle,
					"badge": 0,
					"content-available": "1",
                    "sound": "0",
					"url": newsID,
					"link": phoneID,
					"link_tablet": tabletID,
					"priority": "high"
				}
		  
		
		  
		}, { useMasterKey: true }).then(() => {
		  console.log('Push ok');
		}, (e) => {
		  console.log('Push error', e);
		});
	
	
	
	
	
	
	
	
	
	
	
	
			/*Parse.Push.send({
		 channels: [ "Live" ],
		  //where: query,
		  data: {
			alert: 'Test',
			badge: 1,
			sound: 'default'
		  }
		}, {
		 
		  success: function() {
			console.log("PUSH SENT");
		  },
		  error: function(error) {
			console.log("PUSH FAILED" + error.description);
		  },
		 useMasterKey: true}); */
	
	
	
	
	
    /*Parse.Push.send({
                  where: query,
                  data: {
                    alert: "test push"  
                   // title: pushTitle,
                   // badge: 0,
                    //'content-available': "1",
                   //  'sound': "0",
                   // url: newsID,
                   // link: phoneID,
                   // link_tablet: tabletID,
                   // "priority": "high"
                  }
                 // push_time: (pushTime != null ? pushTime : new Date())
				 
  },{useMasterKey: true}).then(function() {
	  
	  console.log("SEND");
  },
  function(error) {
	  
	  console.log("FAIL" + error);
	  
	  }); */
	  
  } else{
	  
    console.log("pushed error 2 "+user);
	
    response.error("missing data");
  }
},{ useMasterKey: true });


