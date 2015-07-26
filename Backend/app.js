var express = require('express');
var app = express();
var compression = require('compression');
app.use(compression());

/*var ping = require('./service/ping.js');
ping.ping();
*/
var router = require('./routes/router.js');
app.use('/find',router.places)

module.exports = app;