var http = require('http');
var request = require('request');
var etcd = require('nodejs-etcd');

var backends = [];

var e = new etcd({
  url: process.env.ETCD_URL || 'http://192.168.33.10:4001'
});

var changeCb = e.generator(
  function (error) { console.log(error.text); },
  function (result) {
    readBackends()
    watchBackends();
  }
);

var readCb = e.generator(
  function(error) { console.log(error.text); },
  function(result) {
    if (result.nodes) {
      backends = result.nodes.map(function (n) { return n.value; });
    } else {
      backends = [];
    }
  }
);

function readBackends() {
  e.read({'key': '/backends', recursive: true}, readCb);
}

function watchBackends() {
  e.read({'key': '/backends', recursive: true, wait: true}, changeCb);
}

readBackends();
watchBackends();

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});

  var backend = backends[Math.floor(Math.random() * backends.length)];
  if (backend) {
    request(backend, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        res.end(body);
      } else {
        res.end(error);
      }
    });
  } else {
    res.end("No backend available");
  }
}).listen(process.env.PORT || 3000);
