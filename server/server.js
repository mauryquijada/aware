var http = require('http');
var https = require('https');
var fs = require('fs');
var url = require('url');
var exec = require('child_process').exec;

// Load config and strip out /* comments */... sketchily.
var settings = JSON.parse(fs.readFileSync('../config.js', 'ascii').replace(/(\/\*[^*]*\*\/)/g, ''));

http.createServer(function (req, res) {
	res.writeHead(200);
    res.end('Hello, world!');
}).listen(settings['port']);