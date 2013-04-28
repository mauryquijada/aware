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

	device: 
		id: 
		location: [lat, lon]
*/

function isValidLocation(location) {
	return location && location.length == 2 && typeof location[0] == 'number' && typeof location[1] == 'number';
}

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
	while (reports[i] && d - reports[i]['time'] > settings['report_timeout'])
		i++;
	reports = reports.slice(i);
}

http.createServer(function (req, res) {
	console.log('request ' + req.url);

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
        req.on('data', function (data) {
            body += data;
        });
        req.on('end', function () {
			fn(body);
        });
	}
	
	// No device given.
	if (!req.headers['device'])
		return r400('No device ID!');

	var deviceId = req.headers['device'];
	
	// Clean up stale data with a certain probability
	if (Math.random() < 1 / deviceLength)
		setTimeout(clean, 0);
			
	switch (req.url) {
		case '/report':
		
			// Need to be a valid user if requesting reports
			if (!devices[deviceId])
				return r400('Unregistered device ID.');
				
			// List the reports
			if (req.method == 'GET') {
				console.log(deviceId + ': listing report');
				return r200(JSON.stringify(reports.filter(function (report) {
					return distance(report['location'], devices[deviceId]['location']) < settings['location_threshold_general'];
					// TODO: remove sensitive information and filter by time
				})));
			}
			
			// Add the report
			else if (req.method == 'POST')
				return postData(function (report) {
					try {
						report = JSON.parse(report);
					} catch (e) {
						return r400('Invalid report', report);
					}
					
					if (!report.description || !isValidLocation(report.location))
						return r400('Invalid report', report);
						
					report.id = deviceId + Date.now();
					report.time = Date.now();
					report.device = deviceId;
					
					console.log(deviceId + ': adding report', report);
					reports.push(report);
					
					return r200('Report successfully added');
				});
			
			return;
			
		case '/device':
			
			// New device
			if (req.method == 'POST')
				return postData(function (device) {
					try {
						device = JSON.parse(device);
					} catch (e) {
						return r400('Invalid device');
					}
					
					if (!isValidLocation(device.location) || !device.registration_id)
						return r400('Invalid device registration');
					
					console.log(deviceId + ': adding device', device);
					if (!device[deviceId])
						deviceLength++;
						
					devices[deviceId] = device;
					return r200('Device successfully added');
				});
			return;
			
		default:
			return r400('Command not found');
	}
}).listen(settings['port']);