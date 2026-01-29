function toVTT(utf8str) {
	try {
		return utf8str
	        .replace(/\{\\([ibu])\}/g, '</$1>')
	        .replace(/\{\\([ibu])1\}/g, '<$1>')
	        .replace(/\{([ibu])\}/g, '<$1>')
	        .replace(/\{\/([ibu])\}/g, '</$1>')
	        .replace(/(\d\d:\d\d:\d\d),(\d\d\d)/g, '$1.$2')
	        .concat('\r\n\r\n');
	}
	catch (err) {
		return;
	}
}

module.exports = function(decoded) {
    return new Promise((resolve, reject) => {
        try {
            const vttString = 'WEBVTT FILE\r\n\r\n';
            const text = vttString.concat(toVTT(decoded));
            const blob = new Blob([text], {
                type: 'text/vtt'
            });
            var objectURL = URL.createObjectURL(blob);
            return resolve(objectURL);
        }
        catch (err) {
            return reject();
        }
    });
};