const appRoot = require('app-root-path');
const electron = require('electron');
const app = electron.remote.app;
const path = require("path");
const SUPPORT_LIVE_THUMBNAIL = { ppt: true, pptx: true, potx: true, mp4: true, mov: true, m4v: true, webm: true, mkv: true, avi: true, wmv: true, mpg: true };
const URL_MODULE = require(appRoot + '/my_modules/url');
const USER_DATA_PATH = app.getPath('userData');
const EAGLE_THUMBNAIL_TEMP_PATH = path.normalize(USER_DATA_PATH + "/eagle-temp");

let cache = {};
let thumbnailCache = {};

module.exports = {
	getFileIcon: getFileIcon,
	getFileThumbnail: getFileThumbnail,
};

function generateFileIconWithNative (filePath, ext, callback) {
	app.getFileIcon(filePath).then(function (img) {
    	try {
			let base64 = img.toDataURL();
			if (ext !== "exe" && ext !== "msi") {
				cache[ext] = base64;
			}
			else {
				cache[filePath] = base64;
			}
			return callback(base64);
		}
		catch (err) {
			return callback();
		}
	});
}

function getFileIcon (filePath, callback) {
	if (!filePath) return;
	if (!callback) callback = function () {};
	try {
		let extname = path.extname(filePath).toLowerCase();
		let ext = extname.replace(".", "");
		let key = ext;
		if (ext === "exe" || ext === "msi") {
			key = filePath;
		}
        if (cache[key]) {
        	return callback(cache[key]);
        }
        // TODO: 暫時性解決方案，目前 electron app.getFileIcon 在字體大小 > 100% 時會有破圖的問題
        if (devicePixelRatio === 1) {
        	generateFileIconWithNative(filePath, ext, callback);
	    }
	    else {
	    	let tempFolder = `${EAGLE_THUMBNAIL_TEMP_PATH}/thumbs`;
	    	let tempPath = path.normalize(tempFolder + "/" + ext + ".png");
	    	if (!fs.existsSync(tempFolder)) {
		    	fs.mkdirSync(tempFolder);
		    }
		    const extractIcon = require(appRoot + '/my_modules/extract-icon');
		    extractIcon.extractAssociatedIcon([filePath, tempPath], function (err, result) {
		    	if (result === "success") {
		    		try {
			    		const nativeImage = require('electron').nativeImage;
						let image = nativeImage.createFromPath(tempPath);
						let base64 = image.toDataURL();
						if (ext !== "exe" && ext !== "msi") {
							cache[ext] = base64;
						}
						else {
							cache[filePath] = base64;
						}
						return callback(base64);
					}
					catch (err) {
						generateFileIconWithNative(filePath, ext, callback);
					}
		    	}
		    	else {
		    		generateFileIconWithNative(filePath, ext, callback);
		    	}
		    });
	    }
    }
    catch (err) {
        return callback();
    }
}

async function getFileThumbnail (item, filePath, callback) {

	if (process.platform !== 'darwin') {
		getFileIcon(filePath, function (iconBase64) {
    		return callback(iconBase64);
    	});
    	return;
	}
	else {
		let tempFolder = `${EAGLE_THUMBNAIL_TEMP_PATH}/thumbs`;
		let tempPath = path.normalize(tempFolder + "/" + item.id + ".png");
		let extname = path.extname(filePath).toLowerCase();
		let ext = extname.replace(".", "");

		if (SUPPORT_LIVE_THUMBNAIL[ext]) {

			// 如果有緩存，就直接使用緩存
			if (fs.existsSync(tempPath)) {
				return callback(URL_MODULE.pathToFileURL(tempPath).href);
			}

			if (!fs.existsSync(tempFolder)) {
		    	fs.mkdirSync(tempFolder);
		    }

			let result = await ipcRenderer.invoke('nativeImage.createThumbnailFromPath', {
				tempFilePath: path.normalize(tempPath),
		        filePath: filePath,
		        size: 120
		    });

		    if (result) {
		    	return callback(URL_MODULE.pathToFileURL(tempPath).href);
		    }
		    else {
		    	generateFileIconWithNative(filePath, ext, function (iconBase64) {
		    		return callback(iconBase64);
		    	});
		    }
		}
		else {
			generateFileIconWithNative(filePath, ext, function (iconBase64) {
	    		return callback(iconBase64);
	    	});
		} 
	}   
}
