const fs = require('fs');
const path = require('path');
const dcraw = require('./dcraw.js');

function extractRawThumbnail (buf, output, callback) {
	try {
		const jpegBuffer = dcraw(buf, { extractThumbnail: true });
		if (!jpegBuffer) {
			callback({});
			return;
		}
		fs.writeFileSync(output, jpegBuffer);
		callback(undefined, output);
	}
	catch (err) {
		callback(err);
	}
}

function parseRawMetadata (metadata) {
	// Filename: raw_buf
	// Timestamp: Tue Nov  6 17:15:19 2018
	// Camera: Pentax K-1 Mark II
	// DNG Version: 1.2.0.0
	// ISO speed: 200
	// Shutter: 1/320.0 sec
	// Aperture: f/3.2
	// Focal length: 200.0 mm
	// Embedded ICC profile: no
	// Number of raw images: 1
	// Thumb size:  7360 x 4912
	// Full size:   7392 x 4950
	// Image size:  7376 x 4932
	// Output size: 7376 x 4932
	// Raw colors: 3
	// Filter pattern: RG/GB
	// Daylight multipliers: 2.336611 0.956155 1.283195
	// Camera multipliers: 2.164062 1.000000 1.734375 0.000000
	try {
		var result = {};
		var strs = metadata.split("\n");
		var metadataMapping = {};
		var keys = ["Timestamp", "Camera", "ISO speed", "Shutter", "Aperture", "Focal length", "Full size"]
		for (var i = 0; i < strs.length; i++) {
			var str = strs[i];
			keys.forEach(function (key) {
				if (str.indexOf(`${key}:`) > -1) {
					metadataMapping[`${key}`] = str.replace(`${key}:`, "").replace(/'/g, "").replace(",", "").trim();
				}
			});
		}
		if (metadataMapping['Timestamp']) {
			result.timestamp = new Date(metadataMapping['Timestamp']).getTime();
		}
		if (metadataMapping['Camera']) {
			result.camera = metadataMapping['Camera'];
		}
		if (metadataMapping['ISO speed']) {
			result.isoSpeed = parseInt(metadataMapping['ISO speed']);
		}
		if (metadataMapping['Focal length']) {
			result.focalLength = metadataMapping['Focal length'];
		}
		if (metadataMapping['Shutter']) {
			result.shutter = metadataMapping['Shutter'];
		}
		if (metadataMapping['Aperture']) {
			result.aperture = metadataMapping['Aperture'];
		}
		if (metadataMapping['Full size']) {
			result.width = parseInt(metadataMapping['Full size'].split(" x ")[0]);
			result.height = parseInt(metadataMapping['Full size'].split(" x ")[1]);
		}
		return result;
	}
	catch (err) {
		return {};
	}
}

function extractRawMetadata (buf, callback) {
	var result = {
		// timestamp: '',		// 拍攝時間
		// camera: '',			// 相機 & 型號
		// isoSpeed: '',		// 感光度
		// focalLength: '',	// 焦距
		// shutter: '',		// 曝光時間
		// aperture: '',		// 光圈
		// width: '',			// 寬度
		// height: ''			// 高度
	};

	try {
		var metadata = dcraw(buf, { verbose: true, identify: true });
		result = parseRawMetadata(metadata);
		callback(undefined, result);
	}
	catch (err) {
		callback(err);
	}	
}

function getTiffData (buf, callback) {
	try {
		var tiffData = dcraw(buf, { exportAsTiff: true });
		return callback(undefined, tiffData);
	}
	catch (err) {
		return callback(err);
	}
}

module.exports = {
	getTiffData: getTiffData,
	extractRawThumbnail: extractRawThumbnail,
	extractRawMetadata: extractRawMetadata,
	parseRawMetadata: parseRawMetadata,
};