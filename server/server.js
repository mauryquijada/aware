var http = require('http');
var https = require('https');
var fs = require('fs');
var qs = require('querystring');

// Load config and strip out /* comments */... sketchily.
var settings = JSON.parse(fs.readFileSync('../config.js', 'ascii').replace(/(\/\*[^*]*\*\/)/g, ''));
var reports = [];
var devices = {};
var deviceLength = 0;

/*
	report:
		id: 
		device: 
		time: 
		data: string

	device: 
		id: 
		location: [lat, lon]
*/

// Returns distance in degrees (small-angle approximation)
function distance(l1, l2) {
	return ((l1[0] - l2[0]) * (l1[0] - l2[0]) + (l1[1] - l2[1]) * (l1[1] - l2[1]));
}

function clean() {
	console.log('cleaning');
	var d = Date.now();
	for (device in devices) 
		if (d - devices[device]['time'] > settings['device_timeout']) {
			delete devices[device];
			deviceLength--;
		}
	
	var i = 0;
	while (t - reports[i]['time'] > settings['report_timeout'])
		i++;
	reports = reports.slice(i);
}

http.createServer(function (req, res) {

	function r400(err) {
		console.log('400 ' + err);
		res.writeHead(400);
		res.end(err);
	}
	function r200(out) {
		res.writeHead(200);
		res.end(out);
	}
	function postData(fn) {
		var body = '';
        request.on('data', function (data) {
            body += data;
        });
        request.on('end', function () {
			fn(body);
        });
	}

	// Nope.
	if (!message.headers['Device'])
		return r400('No device ID!');
	
	var device = message.headers['Device'];
	
	// Clean up stale data with a certain probability
	if (Math.random() < 1 / deviceLength)
		setTimeout(clean, 0);
			
	switch (req.url) {
		case '/report':
		
			// Need to be a valid user if requesting reports
			if (devices[device])
				return r400('Unregistered device ID.');
				
			// List the reports
			if (req.method == 'GET') {
				console.log(device + ': listing report');
				r200(JSON.stringify(reports.filter(function (report) {
					return distance(report['location'], devices[device]['location']) < settings['location_threshold_general'];
					// TODO: remove sensitive information
				})));
			}
			
			// Add the report
			else if (req.method == 'POST')
				postData(function (report) {
				
					// Format is lat,long|extra-data-blah-blah-blah
					var separator = report.indexOf('|');
					var location = report.substr(0, separator).split(',').map(Number);
					var data = report.substr(separator + 1);
					if (!report || location.length != 2 || location.some(isNaN) || !data)
						return r400('Invalid report');
					
					console.log(device + ': adding report (' + report + ')');
					reports.push({
						'id': device + Date.now(),
						'time': Date.now(),
						'device': device,
						'location': location,
						'data': data
					});
					
					return r200('Report successfully added');
				});
			
			break;
			
		case '/device':
			
			// New client
			postData(function (location) {
				if (!report)
					return r400('Invalid device registration');
				
				console.log(device + ': adding device');
				if (!device[device])
					deviceLength++;
					
				devices[device] = {
					'id': device,
					'location': location.split(',').map(Number)
				};
				return r200('Device successfully added');
			});
			
			break;
			
		default:
			return r400('Command not found');
	}
}).listen(settings['port']);