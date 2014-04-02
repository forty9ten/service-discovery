var http = require('http');
var request = require('request');
var etcd = require('nodejs-etcd');

var backends = [];

var e = new etcd({
  url: process.env.ETCD_URL || 'http://192.168.33.10:4001'
});

var cb = e.generator(
  function(error) { console.log(error.text); },
  function(result) {
    backends = [result.value];
    watchBackends();
  }
);

function watchBackends() {
  e.read({'key': '/backends', recursive: true, wait: true}, cb);
}

e.read({'key': '/backends', recursive: true}, cb);

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});

  request(backends[0], function (error, response, body) {
    if (!error && response.statusCode == 200) {
      res.end(body);
    } else {
      res.end(error);
    }
  });
}).listen(process.env.PORT || 3000);
