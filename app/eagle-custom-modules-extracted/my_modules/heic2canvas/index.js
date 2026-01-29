module.exports = heic2canvas;

function heic2canvas (url, callback) {
    var appRoot = require('app-root-path');
    var libheif = require(appRoot.path + "/app/js/vendors/libheif.js");
    var decoder = new libheif.HeifDecoder();
    var loadUrl = function(url, callback) {
        try {
            if (!fs.existsSync(url)) {
                return callback("image_data is empty");
            }
            else {
                fs.readFile(url, function (err, data) {
                    var buffer = data.buffer;
                    var filename = url.substring(url.lastIndexOf('/')+1);
                    var image_data = decoder.decode(buffer);
                    if (!image_data || !image_data.length) {
                        // 发生错误
                        return callback("image_data is empty");
                    }
                    else {
                        return callback(undefined, image_data[0]);
                    }
                });
            }
        }
        catch (err) {
            return callback(err);
        }
    };

    loadUrl(url, function (err, imageData) {

        if (err) {
            callback(err);
            return;
        }

        var w = imageData.get_width();
        var h = imageData.get_height();

        var canvas = document.createElement('canvas');
        var context = canvas.getContext('2d');
        var data = context.createImageData(w, h);
        var image_data = data.data;

        canvas.width = w;
        canvas.height = h;
        
        // Start with a white image.
        for (var i=0; i<w*h; i++) {
            image_data[i*4+3] = 255;
        }

        imageData.display(data, function (display_image_data) {
            if (!display_image_data) {
                callback("error-format");
            }
            // window.requestAnimationFrame(function() {
                if (display_image_data) {
                    try {
                        context.putImageData(display_image_data, 0, 0);
                        callback(undefined, canvas);
                    }
                    catch (err) {
                        callback(err);
                    }
                }
            // });
        })

    });
}
