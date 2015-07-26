var express = require("express");
var schema = require('../models/mygola.js');
var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost/mygola', function(err,db){
	if(!err){
		console.log("Successfully connected");
	}else{
		console.log("Unable to connect");
		console.log(err);
	}
});

exports.places = function(request,response){
/*	var model = ''; 
	if(request.query.ln == 'hi'){
		model = 'hindi';
		
	}else if(request.query.ln == 'en'){
		model = 'english';
	}else if(request.query.ln == 'ta'){
		model = 'tamil';
	}else if(request.query.ln == 'ml'){
		model = 'malayalam';
	}else{
		model = 'notfound';
	}

	if(! request.query.topic){
		model = model.concat('top');
	}
	if(request.query.topic=='w'){
		model = model.concat('world');
	}
	if(request.query.topic =='n'){
		model = model.concat('india');
	}
	if(request.query.topic =='s'){
		model = model.concat('sportes');
	}
	if(request.query.topic =='e'){
		model = model.concat('entertainment');
	}
	if(request.query.topic =='b'){
		model = model.concat('business');
	}
	if(request.query.topic =='h'){
		model = model.concat('more');
	}
	if(model == 'malayalam' && request.query.topic == 'kl'){
		model = model.concat('kerala');
	}
	if(model == 'malayalam' && request.query.topic == 'awkl'){
		model = model.concat('arbic');
	}
	if(model == 'tamil' && request.query.topic == 'tn'){
		model = model.concat('tamil');
	}
	if(model == 'tamil' && request.query.topic == 'sn'){
		model = model.concat('srilanka');
	}
	if(model == 'english' && request.query.topic == 'm'){
		model = model.concat('health');
	}
	if(model == 'english' && request.query.topic == 'tc'){
		model = model.concat('tech');
	}
	if(model == 'english' && request.query.topic == 'snc'){
		model = model.concat('science');
	}
	if(model == 'english' && request.query.topic == 'ir'){
		model = model.concat('spotlight');
	}


	if(model.match(/notfound/) || model == 'tamil' || model == 'hindi' || model == 'english' || model == 'malayalam'){
		console.log(model," topic ",request.query.topic);
		response.status(404).send('404 -HTTP NOT FOUND');
	}else{
		mongoose.model(model).find({},{},{skip:request.query.pg*10,limit:10},function(err, feed){
			response.send(feed);
		}).sort({_id:-1});
	}*/
	console.log("returning places collection");
	mongoose.model("places").find(function(err, feed){
			response.send(feed);

	}).sort({_id:-1});
	

};
