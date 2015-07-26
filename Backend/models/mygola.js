var mongoose = require("mongoose");
var schema = mongoose.Schema;
var places  = new schema({
	name:{
		type:String,
	},	
	desc:String,
	safety:{
		type:String,
	},
	local:String,
});

exports.places = mongoose.model('places',places,'places');


