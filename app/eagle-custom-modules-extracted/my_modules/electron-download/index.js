'use strict';
const path = require('path');
const electron = require('electron');
const unusedFilename = require('unused-filename');
const app = electron.app;
// const shell = electron.shell;
const RETRY_TIMES = 60; 	// 重複嘗試次數（每0.5秒檢查一次）

function registerListener(win, opts = {}, cb = () => {}) {

	var id = win.webContents.id;
	
	const listener = (e, item, webContents) => {

		opts.downloadItem = item;

		if (!webContents || id != webContents.id) return;

		const dir = opts.directory || app.getPath('downloads');
		let filePath;
		
		if (opts.filename) {
			filePath = path.join(dir, opts.filename);
		} else {
			filePath = unusedFilename.sync(path.join(dir, item.getFilename()));
		}

		const errorMessage = opts.errorMessage || 'The download of {filename} was interrupted';
		const errorTitle = opts.errorTitle || 'Download Error';

		if (!opts.saveAs) {
			item.setSavePath(filePath);
		}

		item.on('done', (e, state) => {
			// console.log("下载任务完成, ", state);
			clearInterval(opts.checkStateInterval);
			cb(null, filePath);
		});
	};

	win.webContents.on('destroyed', function (e) {
		// console.log("下载任务结束");
		// clearInterval(opts.checkStateInterval);
	});
	
	win.webContents.session.on('will-download', listener);
}

module.exports = (opts = {}) => {
	app.on('browser-window-created', (e, win) => {
		registerListener(win, opts);
	});
};

module.exports.download = (win, url, opts) => new Promise((resolve, reject) => {

	opts = Object.assign({}, opts, {unregisterWhenDone: true});
	registerListener(win, opts, (err, item) => err ? reject(err) : resolve(item));

	var interruptedCount = 0;
	var retryCount = 0;
	var lastRatio;

	// Timer 机制，避免任务卡死
	opts.checkStateInterval = setInterval(function () {
		
		var item = opts.downloadItem;
		if (!item) {
		// if (!item || item.isDestroyed()) {
			retryCount++;
			// console.log("卡住了....%d次", retryCount);
			if (retryCount > RETRY_TIMES) {
				clearInterval(opts.checkStateInterval);
				opts.onInterrupted();
			}
			return;
		}

		const totalBytes = item.getTotalBytes();
		var state = item.getState();
		var ratio = item.getReceivedBytes() / totalBytes;

		// console.log("【下载任务进度检查】：%s, 当前进度 %d%", state, ratio * 100);
		
		if (state === 'interrupted') {
			opts.onInterrupted();
			interruptedCount++;
			clearInterval(opts.checkStateInterval);
			item.cancel();
		}
		else {
			if (lastRatio == ratio) {
				retryCount++;
				// console.log("卡住了....%d次", retryCount);
			}
			else {
				retryCount = 0;	
			}
			if (retryCount > RETRY_TIMES) {
				clearInterval(opts.checkStateInterval);
				opts.onInterrupted();
				item.cancel();
			}
			else {
				if (lastRatio != ratio) {
					opts.onProgress(ratio);
				}
				lastRatio = ratio;
			}
		}
	}, 500);

	win.webContents.downloadURL(url);
});
