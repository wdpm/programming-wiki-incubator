const nativeImage = require('electron').nativeImage;
module.exports = function (imagePath, croppedImage, top, left, width, height, callback) {
    var ext = croppedImage.ext;
    if (ext === "jpg" || ext === "png") {
        var nativeImage = electron.nativeImage;
        var newImage = nativeImage.createFromPath(imagePath);

        var cropParams = {
            x: parseInt(left),
            y: parseInt(top),
            width: parseInt(width),
            height: parseInt(height)
        };

        // 带有 orientation 属性的图片
        // if (croppedImage.orientation && croppedImage.orientation !== 1) {}

        electronLog.info(JSON.stringify(cropParams));

        newImage = newImage.crop(cropParams);

        electronLog.info(`[app] Crop successfully: ${imagePath}`);

        var outputBuffer;
        if (croppedImage.ext === "png") {
            electronLog.info(`[app] Crop format: PNG`);
            outputBuffer = newImage.toPNG();
        }
        else {
            electronLog.info(`[app] Crop format: JPEG`);
            outputBuffer = newImage.toJPEG(100);
        }
        callback(undefined, outputBuffer);
    }
    else if (ext === "webp") {
        electronLog.info(`[app] Crop format: WebP`);
        var img = new Image();
        img.onload = function() {
            canvasHelper.crop(img, {
                toCropImgX: parseInt(left),
                toCropImgY: parseInt(top),
                toCropImgW: width,
                toCropImgH: height,
                imgChangeRatio: 1,
                mimeType: "image/webp",
                quality: 100
            }, function (base64string) {
                var buffer = decodeBase64Image(base64string).data;
                callback(undefined, buffer);
            });
        };
        img.onerror = function(event) {
            callback(true);
            return;
        };
        img.src = URL_MODULE.pathToFileURL(imagePath).href + "?v=" + Date.now();
    }
}