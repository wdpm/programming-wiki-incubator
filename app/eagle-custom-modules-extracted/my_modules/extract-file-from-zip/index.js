// Examples
// var input = "/Users/augus/Downloads/测试.xd";
// var output = "/Users/augus/Downloads/测试.png";
// var fileName = "preview.png";

// // 从文件中抓出特定的文件
// extractPreviewFromZipFile({
// 	input: input, 
// 	fileName: fileName, 
// 	output: output
// }, (err) => {
// 	if (err) {
// 		console.error(err);
// 	}
// 	else {
// 		console.log("%s has been extract to %s", fileName, output);
// 	}
// });

module.exports = extractPreviewFromZipFile;

function extractPreviewFromZipFile (params, callback) {

	try  {
		var input = params.input;
		var output = params.output;
		var fileName = params.fileName;

		var fs = require('fs');
		var AdmZip = require('adm-zip');
		var zip = new AdmZip(input);
		var zipEntries = zip.getEntries();

		if (!input || !output || !fileName) {
			callback(new Error('参数不正确'));
			return;
		}

		if (zipEntries.length === 0) {
			callback(new Error('无法解析文件'));
			return;	
		}

		for (var i = 0; i < zipEntries.length; i++) {
			var zipEntry = zipEntries[i];
			if (zipEntry.entryName.indexOf(fileName) > -1) {
			// if (zipEntry.entryName === fileName) {
				var fileBuffer = zipEntry.getData();
				try {
			    	fs.writeFileSync(output, zipEntry.getData());
			    	callback();
		    	}
		    	catch (err) {
		    		return callback(err);
		    	}
			    return;
			}
		}
		
		callback(new Error('找不到指定的文件'));
	}
	catch (err) {
		callback(err);	
	}
};