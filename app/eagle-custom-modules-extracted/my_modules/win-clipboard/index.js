module.exports = {
	readPaths: readPaths,
	writePaths: writePaths
};

function writePaths (paths, callback) {
	var edge = require('electron-edge-js');
	var CLIP = edge.func(function() {
        /*
	        #r "System.Windows.Forms.dll"
	        #r "System.Collections.dll"
	        #r "System.Drawing.dll"

	        using System.Windows.Forms;
	        using System.Collections.Specialized;
	        using System.Threading.Tasks;
	        using System.Drawing;
	        using System;

	        public class Startup
	        {
	            public async Task<object> Invoke(object[] list)
	            {
	                DataObject d = new DataObject();
	                StringCollection paths = new StringCollection();
	                foreach (string element in list) {
	                    paths.Add(element);
	                }
	                d.SetFileDropList(paths);

	                if (list.Length == 1) {
	                    try {
	                        using (var fs = new System.IO.FileStream((string)list[0], System.IO.FileMode.Open, System.IO.FileAccess.Read))
	                        {
	                            var bmp = new Bitmap(fs);
	                            d.SetImage((Bitmap) bmp.Clone());
	                            System.IO.MemoryStream ms = new System.IO.MemoryStream();
     							bmp.Save(ms, System.Drawing.Imaging.ImageFormat.Png);
     							d.SetData("PNG", false, ms);
	                            fs.Close();
	                        }
	                    }
	                    catch (Exception e) {}
	                }

	                Clipboard.SetDataObject(d);
	                return null;
	            }
	        }

	    */
    });
    CLIP(paths, function(error, result) {
    	callback && callback(error, result);
    });
}

function readPaths (callback) {
	var edge = require('electron-edge-js');
	var CLIP = edge.func(
        `
            #r "System.Windows.Forms.dll"
            #r "System.Collections.dll"
            using System.Windows.Forms;
            using System.Collections.Specialized;
            using System.Threading.Tasks;
            public class Startup
            {
                public async Task<object> Invoke(object[] list)
                {
                    return Clipboard.GetFileDropList();
                }
            }

        `
    );
    CLIP(undefined, function(error, result) {
    	callback && callback(error, result);
    });
}