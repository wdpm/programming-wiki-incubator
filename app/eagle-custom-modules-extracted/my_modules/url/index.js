const URL_NATIVE = require("url");
const path = require("path");

module.exports = {
	pathToFileURL: pathToFileURL
};

function pathToFileURL (filePath) {
	try {
        if (process.platform === 'darwin') {
            return URL_NATIVE.pathToFileURL(filePath);
        }
        else {
        	filePath = path.normalize(filePath);
        	if (filePath[0] === '\\' && filePath[1] === '\\') {
        		return {
        			href: "file:/" + URL_NATIVE.pathToFileURL(filePath).pathname
        		}
        	}
            else {
                return URL_NATIVE.pathToFileURL(filePath);
            }
        }
    }
    catch (err) {
        return "";
    }
}
