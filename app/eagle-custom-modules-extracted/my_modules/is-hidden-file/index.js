module.exports = {
	check: check
};

const path = require("path");
const hiddenFiles = {
	".ds_store": true,
	".spotlight-v100": true,
	".trashes": true,
	"ehthumbs.db": true,
	"thumbs.db": true,
	"desktop.ini": true
};

function check (filePath) {
	try {
		let basename = path.basename(filePath).toLowerCase();
		if (hiddenFiles[basename]) {
			return true;
		}
		else if (basename.charAt(0) === '.') {
			return true;
		}
		return false;
	}
	catch (err) {
		return false;
	}
}
