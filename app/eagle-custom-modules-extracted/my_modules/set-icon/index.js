module.exports = setIcon;
function setIcon (folderPath, iconPath) {
	const os = require('os');
	const fs = require('fs');
	const path = require('path');
	const exec = require('child_process').exec;
	const appRoot = require('app-root-path');

	if (process.platform == 'darwin') {
		// var shPath = path.normalize(appRoot.path + '/my_modules/set-icon/set-icon-osx.py');
		// var tmpShPath = `${os.tmpdir()}/set-icon-osx.py`;
		var shPath = path.normalize(appRoot.path + '/my_modules/set-icon/set-icon-osx.scpt');
		var tmpShPath = `${os.tmpdir()}/set-icon-osx.scpt`;

		try {
			if (!fs.existsSync(tmpShPath)) {
				fs.copyFileSync(shPath, tmpShPath);
			}
			if (fs.existsSync(iconPath)) {
				exec(`osascript "${tmpShPath}" "${iconPath}" "${folderPath}"`, function (err, stdout, stderr) {
				// exec(`python "${tmpShPath}" "${iconPath}" "${folderPath}"`, function (err, stdout, stderr) {
					if (!stderr) {
						console.log("[main] Change icon successfully");
					}
					else {
						console.log(stderr);
						throw err;
					}
		        });
	        }
		}
		catch (err) {
			console.log(err);
			throw err;
		}
	}
	else {
		var shPath = path.normalize(appRoot.path + '/my_modules/set-icon/set-icon-win32.bat');
		var tmpShPath = `${os.tmpdir()}\\set-icon-win32.bat`;
		var sudo = require(appRoot + '/my_modules/sudo-prompt');
		var iniPath = path.normalize(`${folderPath}/Desktop.ini`);
		try {
			if (!fs.existsSync(tmpShPath)) {
				fs.copyFileSync(shPath, tmpShPath);
			}
			if (fs.existsSync(iconPath)) {
				exec(`${tmpShPath} "${iconPath}" "${folderPath}" "${iniPath}"`, function (err, stdout, stderr) {
					if (!stderr) {
						console.log("[main] Change icon successfully");
					}
					else {
						console.log(stderr);
						console.log(stdout);
						throw err;
					}
		        });
	        }
		}
		catch (err) {
			console.log(err);
			throw err;
		}
	}
}