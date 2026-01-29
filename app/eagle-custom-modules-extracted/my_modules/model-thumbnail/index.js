module.exports = {
	getDataUrl: ({ filePath, type, size }) => {
		return new Promise((resolve, reject) => {
			const appRoot = require('app-root-path');
			const URL_MODULE = require(appRoot + '/my_modules/url');
			var fileUrl = URL_MODULE.pathToFileURL(filePath).href;
			var callbackFunctionName = `threeJSReadyCallback${guid()}`;
			// TODO: 要有 timeout 机制
			var $iframe = $(`<iframe src="../app/model-viewer/index.html?url=${fileUrl}&type=${type}" style="width: ${size}px; height: ${size}px; pointer-events: none; opacity: 0;" callback="${callbackFunctionName}"></iframe>`);
			window[callbackFunctionName] = (err, base64) => {
				$iframe.attr("src", "");
				$iframe.remove();
				delete window[callbackFunctionName];
				if (base64 && base64.length > 0) {
					resolve(base64);
				}
				else {
					reject(err);
				}
			};
			$("body").append($iframe);
		});
	}
}