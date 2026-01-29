// if (process.platform === 'darwin') {


const MAX_WIDTH = 60000;
const MAX_HEIGHT = 60000;

module.exports = mergeImages;

// Example
// mergeImages({
//     images: [
//         { width: 440, height: 622, name: 'C:/Users/User/Dropbox/EagleApp/app/1.jpg' },
//         { width: 448, height: 641, name: 'C:/Users/User/Dropbox/EagleApp/app/2.jpg' },
//     ],
//     output: 'C:/Users/User/Dropbox/EagleApp/app/output-vertical',
//     orientation: 'vertical',
//     align: 'center',
//     format: 'jpg'
// }, (err) => {
//     if (err) {
//         console.error(err);
//     }
//     else {
//         console.log("vertical 输出成功");
//     }
// });

// mergeImages({
//     images: images,
//     output: 'output-horizontal',
//     orientation: 'horizontal',
//     align: 'middle',
//     format: 'jpg'
// }, (err) => {
//  if (err) {
//      console.error(err);
//  }
//  else {
//      console.log("horizontal 输出成功");
//  }
// });

function mergeImages (options, callback) {
    var { images, orientation, output, align, margin, output, format='png' } = options;
    try {
        switch (orientation) {
            case 'vertical':
                mergeImagesVertical({images, align, format, margin}, (err, canvas) => {
                    if (err) {
                        callback(err);
                    }
                    else {
                        try {
                            if (format === "jpg") {
                                base64 = canvas.toDataURL("image/jpeg", 1.0);
                            }
                            else {
                                base64 = canvas.toDataURL();
                            }
                            var decode = decodeBase64Image(base64);
                            fs.writeFileSync(`${output}.${format}`, decode.data);
                            callback(undefined);
                        }
                        catch (err) {
                            callback(err);
                        }
                    }
                });
                break;
            case 'horizontal':
                mergeImagesHorizontal({images, align, format, margin}, (err, canvas) => {
                    if (err) {
                        callback(err);
                    }
                    else {
                        try {
                            if (format === "jpg") {
                                base64 = canvas.toDataURL("image/jpeg", 1.0);
                            }
                            else {
                                base64 = canvas.toDataURL();
                            }
                            var decode = decodeBase64Image(base64);
                            fs.writeFileSync(`${output}.${format}`, decode.data);
                            callback(undefined);
                        }
                        catch (err) {
                            callback(err);
                        }
                    }
                });
                break;
        }
    }
    catch (err) {
        callback(err);
    }
};

function mergeImagesVertical ({images, align, format, margin}, callback) {

    let totalHeight = 0;
    let totalWidth = 0;

    images.forEach(function (image) {
        var { width=0, height=0 } = image;
        totalHeight += height;
        if (width > totalWidth) {
            totalWidth = width;
        }
    });
    if (margin > 0) {
        totalHeight += margin * (images.length - 1);
    }
    var currentPosY = 0;
    images.forEach(function (image, index) {
        var { width=0, height=0 } = image;
        switch (align) {
            case 'left':
                image.drawPos = { x: 0, y: currentPosY }
                break;
            case 'right':
                image.drawPos = { x: totalWidth - width, y: currentPosY }
                break;
            default: 
                image.drawPos = { x: totalWidth / 2 - width / 2, y: currentPosY }
        }
        currentPosY += height;

        if (margin > 0 && index !== images.length - 1) {
            currentPosY += margin;
        }
    });

    if (totalWidth <= 0 && totalHeight <= 0) return callback ("Unable to parse image size.");
    if (totalHeight > MAX_HEIGHT) return callback ("The maximum size of the merged image is 60,000 pixels.");

    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');
    canvas.height = totalHeight;
    canvas.width = totalWidth;

    if (format !== 'png') {
        ctx.fillStyle = "white";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }

    var remain = images.length;
    var finish = function () {
        if (remain === 0) {
            callback(undefined, canvas);
        }
    };

    images.forEach(function (image) {
        var img = new Image();
        img.onload = function() {
            remain--;
            ctx.drawImage(img, image.drawPos.x, image.drawPos.y, image.width, image.height);
            finish();
        };
        img.onerror = function () {
            remain--;
            finish();
        };
        img.src = image.name.replace(/\#/g, "%23").replace(/\?/g, "%3F") + `?v=${Date.now()}`;
    });
};

function mergeImagesHorizontal ({images, align, format, margin}, callback) {

    let totalHeight = 0;
    let totalWidth = 0;

    images.forEach(function (image) {
        var { width=0, height=0 } = image;
        totalWidth += width;
        if (height > totalHeight) {
            totalHeight = height;
        }
    });
    if (margin > 0) {
        totalWidth += margin * (images.length - 1);
    }
    var currentPosX = 0;
    images.forEach(function (image, index) {
        var { width=0, height=0 } = image;
        switch (align) {
            case 'top':
                image.drawPos = { x: currentPosX, y: 0 }
                break;
            case 'bottom':
                image.drawPos = { x: currentPosX, y: totalHeight - height }
                break;
            default: 
                image.drawPos = { x: currentPosX, y: totalHeight / 2 - height / 2 }
        }
        currentPosX += width;

        if (margin > 0 && index !== images.length - 1) {
            currentPosX += margin;
        }
    });

    if (totalWidth <= 0 && totalHeight <= 0) return callback ("Unable to parse image size.");
    if (totalWidth > MAX_WIDTH) return callback ("The maximum size of the merged image is 60,000 pixels.");

    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');
    canvas.height = totalHeight;
    canvas.width = totalWidth;

    if (format !== 'png') {
        ctx.fillStyle = "white";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }

    var remain = images.length;
    var finish = function () {
        if (remain === 0) {
            callback(undefined, canvas);
        }
    };

    images.forEach(function (image) {
        var img = new Image();
        img.onload = function() {
            remain--;
            ctx.drawImage(img, image.drawPos.x, image.drawPos.y, image.width, image.height);
            finish();
        };
        img.onerror = function () {
            remain--;
            finish();
        };
        img.src = image.name.replace(/\#/g, "%23").replace(/\?/g, "%3F") + `?v=${Date.now()}`;
    });
};


// }
// else {
//     const MAX_WIDTH = 60000;
//     const MAX_HEIGHT = 60000;
//     module.exports = mergeImages;

//     var Sharp = require("sharp");
//     Sharp.cache(false);

//     function mergeImages (options, callback) {
//         var { images, orientation, output, align, output, margin, format='png' } = options;
//         try {
//             switch (orientation) {
//                 case 'vertical':
//                     mergeImagesVertical({images, align, format, margin}, (err, result) => {
//                         if (err) {
//                             callback(err);
//                         }
//                         else {
//                             try {
//                                 result.toFile(`${output}.${format}`)
//                                     .then(function (info) {
//                                         callback(undefined);
//                                     })
//                                     .catch(function (err) {
//                                         callback(err);
//                                     });
//                             }
//                             catch (err) {
//                                 callback(err);
//                             }
//                         }
//                     });
//                     break;
//                 case 'horizontal':
//                     mergeImagesHorizontal({images, align, format, margin}, (err, result) => {
//                         if (err) {
//                             callback(err);
//                         }
//                         else {
//                             try {
//                                 result.toFile(`${output}.${format}`)
//                                     .then(function (info) {
//                                         callback(undefined);
//                                     })
//                                     .catch(function (err) {
//                                         callback(err);
//                                     });
//                             }
//                             catch (err) {
//                                 callback(err);
//                             }
//                         }
//                     });
//                     break;
//             }
//         }
//         catch (err) {
//             callback(err);
//         }
//     };

//     async function mergeImagesVertical ({images, align, format, margin}, callback) {

//         let totalHeight = 0;
//         let totalWidth = 0;

//         images.forEach(function (image) {
//             var { width=0, height=0 } = image;
//             totalHeight += height;
//             if (width > totalWidth) {
//                 totalWidth = width;
//             }
//         });
//         if (margin > 0) {
//             totalHeight += margin * (images.length - 1);
//         }

//         var currentPosY = 0;
//         images.forEach(function (image, index) {
//             var { width=0, height=0 } = image;
//             switch (align) {
//                 case 'left':
//                     image.drawPos = { x: 0, y: currentPosY }
//                     break;
//                 case 'right':
//                     image.drawPos = { x: totalWidth - width, y: currentPosY }
//                     break;
//                 default: 
//                     image.drawPos = { x: totalWidth / 2 - width / 2, y: currentPosY }
//             }
//             currentPosY += height;
//             if (margin > 0 && index !== images.length - 1) {
//                 currentPosY += margin;
//             }
//         });

//         if (totalWidth <= 0 && totalHeight <= 0) return callback ("Unable to parse image size.");
//         if (totalHeight > MAX_HEIGHT) return callback ("The maximum size of the merged image is 60,000 pixels.");

//         // 創建一個全新的畫布
//         let result;
//         if (format !== 'png') { 
//             result = Sharp(null, {
//                 create: {
//                     width: Math.floor(totalWidth),
//                     height: Math.floor(totalHeight),
//                     channels: 3,
//                     background: '#ffffff'
//                 }
//             });
//         }
//         else {
//             result = Sharp(null, {
//                 create: {
//                     width: Math.floor(totalWidth),
//                     height: Math.floor(totalHeight),
//                     channels: 3,
//                     background: { r: 255, g: 255, b: 255, alpha: 0 }
//                 }
//             });
//         }

//         var nativeImage = electron.nativeImage;
//         for (var i = 0; i < images.length; i++) {
//             var image = images[i];
//             try {
//                 var newImage = nativeImage.createFromPath(image.name);
//                 var size = newImage.getSize();
//                 if (size.width !== image.width) {
//                     var buf = newImage.resize({
//                         width:image.width, 
//                         height: image.height
//                     }).toPNG();
//                     result.overlayWith(buf, { 
//                         left: Math.floor(image.drawPos.x),
//                         top: Math.floor(image.drawPos.y),
//                     });
//                 }
//                 else {
//                     result.overlayWith(image.name, { 
//                         left: Math.floor(image.drawPos.x),
//                         top: Math.floor(image.drawPos.y),
//                     });
//                 }
//                 if (format !== 'png') { 
//                     result.jpeg({ quality: 100 })
//                 }
//                 else {
//                     result.png()
//                 }
//                 result = Sharp(await result.toBuffer());
//             }
//             catch (err) {
//                 return callback (err);
//             }
//         }
//         callback(undefined, result);
//     };

//     async function mergeImagesHorizontal ({images, align, format, margin}, callback) {

//         let totalHeight = 0;
//         let totalWidth = 0;

//         images.forEach(function (image) {
//             var { width=0, height=0 } = image;
//             totalWidth += width;
//             if (height > totalHeight) {
//                 totalHeight = height;
//             }
//         });
//         if (margin > 0) {
//             totalWidth += margin * (images.length - 1);
//         }

//         var currentPosX = 0;
//         images.forEach(function (image, index) {
//             var { width=0, height=0 } = image;
//             switch (align) {
//                 case 'top':
//                     image.drawPos = { x: currentPosX, y: 0 }
//                     break;
//                 case 'bottom':
//                     image.drawPos = { x: currentPosX, y: totalHeight - height }
//                     break;
//                 default: 
//                     image.drawPos = { x: currentPosX, y: totalHeight / 2 - height / 2 }
//             }
//             currentPosX += width;
//             if (margin > 0 && index !== images.length - 1) {
//                 currentPosX += margin;
//             }
//         });

//         if (totalWidth <= 0 && totalHeight <= 0) return callback ("Unable to parse image size.");
//         if (totalWidth > MAX_WIDTH) return callback ("The maximum size of the merged image is 60,000 pixels.");
 
//         // 創建一個全新的畫布
//         let result;
//         if (format !== 'png') { 
//             result = Sharp(null, {
//                 create: {
//                     width: Math.floor(totalWidth),
//                     height: Math.floor(totalHeight),
//                     channels: 3,
//                     background: '#ffffff'
//                 }
//             });
//         }
//         else {
//             result = Sharp(null, {
//                 create: {
//                     width: Math.floor(totalWidth),
//                     height: Math.floor(totalHeight),
//                     channels: 3,
//                     background: { r: 255, g: 255, b: 255, alpha: 0 }
//                 }
//             });
//         }

//         for (var i = 0; i < images.length; i++) {
//             var image = images[i];
//             try {
//                 var newImage = nativeImage.createFromPath(image.name);
//                 var size = newImage.getSize();
//                 if (size.width !== image.width) {
//                     var buf = newImage.resize({
//                         width:image.width, 
//                         height: image.height
//                     }).toPNG();
//                     result.overlayWith(buf, { 
//                         left: Math.floor(image.drawPos.x),
//                         top: Math.floor(image.drawPos.y),
//                     });
//                 }
//                 else {
//                     result.overlayWith(image.name, { 
//                         left: Math.floor(image.drawPos.x),
//                         top: Math.floor(image.drawPos.y),
//                     });
//                 }
//                 if (format !== 'png') { 
//                     result.jpeg({ quality: 100 })
//                 }
//                 else {
//                     result.png()
//                 }
//                 result = Sharp(await result.toBuffer());
//             }
//             catch (err) {
//                 return callback (err);
//             }
//         }

//         callback(undefined, result);
//     };
// }