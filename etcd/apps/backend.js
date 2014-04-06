var os = require('os');
var http = require('http');
var request = require('request');
var etcd = require('nodejs-etcd');

var hostname = os.hostname(),
    port = process.env.PORT || 3000,
    key = '/backends/' + hostname,
    url = 'http://' + hostname + ':' + port;

var e = new etcd({
  url: process.env.ETCD_URL
});

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end("some data from the backend");
}).listen(port);

function register () {
  var cb = e.generator(
    function (error) { console.log(error.text); },
    function (result) { console.log("Successfully registered"); }
  );
  e.write({ key: key, value: url }, cb);
}

register();

function deregister () {
  var cb = e.generator(
    function (error) {
      console.log(error.text);
      process.exit(1);
    },
    function (result) {
      console.log("Successfully de-registered");
      process.exit();
    }
  );

  e.del({ key: key }, cb);
}

process.on('SIGINT', deregister);
process.on('SIGTERM', deregister);
