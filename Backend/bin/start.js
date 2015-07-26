
var app = require('../app.js');
var http = require('http');

app.set('port','3000');
var server = app.listen(3000, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('My Gola engine started listening at http://%s:%s', host, port);

});


