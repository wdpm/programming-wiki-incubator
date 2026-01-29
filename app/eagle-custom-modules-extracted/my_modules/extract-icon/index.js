var edge;

module.exports = {
	extractAssociatedIcon: extractAssociatedIcon
};

function extractAssociatedIcon (paths, callback) {

	if (!edge) {
		edge = require('electron-edge-js');
	}

	var Extract = edge.func(function() {
        /*
    #r "System.Drawing.dll"
    using System.Drawing;
    using System.Threading.Tasks;
    using System;

    public class Startup
    {
        public async Task<object> Invoke(object[] list)
        {
            try {
                System.Drawing.Icon ico = System.Drawing.Icon.ExtractAssociatedIcon((string)list[0]);
                ico.ToBitmap().Save((string)list[1]);
                return "success";
            }
            catch (Exception exception) {
                throw exception;
                return null;
            }

        }
    }
*/
    });
    Extract(paths, function(error, result) {
    	callback && callback(error, result);
    });
}