const fs = require("fs");

module.exports = {
	check: check
};

function check (filePath) {
	try {
		if( process.platform == 'darwin' ) {
			if (fs.statSync(filePath).isDirectory()) {
				let extname = path.extname(filePath);
				if (extname.length > 0) {
					try {
						const execSync = require('child_process').execSync;
				        var stdout = execSync(`mdls -name kMDItemContentTypeTree '${filePath}'`).toString();
				        if (stdout.indexOf("com.apple.package") > -1) {
				        	return false;
				        }
				        else {
				        	return true;
				        }
				    }
				    catch (err) {
				    	return true;
				    }
				}
				else {
					return true;
				}
			}
			else {
				return false;
			}
		}
		else {
			return fs.statSync(filePath).isDirectory();
		}
	}
	catch (err) {
		return false;
	}
}
