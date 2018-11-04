'use strict';
var rn_bridge = require('rn-bridge');
var fs = require('fs');
var NaturalLanguageUnderstandingV1 = require('watson-developer-cloud/natural-language-understanding/v1.js');

var nlu = new NaturalLanguageUnderstandingV1({
  url: 'https://gateway.watsonplatform.net/natural-language-understanding/api',
  version: '2018-09-21',
  iam_apikey: 'uHlKcElC1hyOP97W07mylWbqGCjRKYiMk4IOkgs1hV1V',
  //iam_url: '<iam_url>', optional - the default value is https://iam.bluemix.net/identity/token
});


nlu.analyze(
	{
    	html: "I love apples! I do not like oranges.", // Buffer or String
    	features: {
    	  concepts: {},
      	sentiment: {
       	 targets: [
       	 "apples",
       	 "oranges",
       	 "broccoli"
       	 ]
     	 },
     	 keywords: {
     	   emotion: true
     	 }
    	}
  	},
 	 function(err, response) {
 	   if (err) {
 	     	console.log('error:', err);
 	   } else {
   			rn_bridge.channel.send(JSON.stringify(response, null, 2));
    	  	console.log(JSON.stringify(response, null, 2));
    	}
  	}
);
// Echo every message received from react-native.
rn_bridge.channel.on('message', (msg) => {
  rn_bridge.channel.send(msg);
} );

// Inform react-native node is initialized.
rn_bridge.channel.send("Node was initialized.");
rn_bridge.channel.send("HHHHHH.");