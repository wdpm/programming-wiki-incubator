"use strict";

const appRoot = require('app-root-path');
const URL_MODULE = require(appRoot + '/my_modules/url');
// var jpeg = require("jpeg-js")
// var png = require("pngparse")
// var gif = require("omggif")
// var bufferEqual = require("buffer-equal")
var isnumber = require("isnumber")

module.exports = read
module.exports.Image = Image
module.exports.Frame = Frame

function read(params, callback) {
    return parseImage(params.filePath, callback);
}

function parseImage (filePath, callback) {

    if (!filePath) return callback(null);

    try {
        var img = new window.Image();
        img.onload = function() {
            try {
                var canvas = document.createElement('canvas');
                var ctx = canvas.getContext('2d');
                var base64;
                var W  = Math.min(img.width, 360);
                var ratio = img.width / W;
                var H = img.height / ratio;

                // canvas.width = img.width;
                // canvas.height = img.height;
                canvas.width = W;
                canvas.height = H;

                ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

                var data = ctx.getImageData(0, 0, canvas.width, canvas.height).data;
                var result = new Image(canvas.height, canvas.width)
                result.addFrame(data);
                return callback(null, result)
            }
            catch (err) {
                return callback(null);
            }
        };
        img.onerror = function() {
            return callback(null);
        };

        img.src = URL_MODULE.pathToFileURL(filePath).href + "?v=" + (Math.random()*101|0);
    }
    catch (err) {
        return callback(null);
    }
}

function Image(height, width) {
    this.height = +height
    this.width = +width
    this.frames = []
}

Image.prototype.addFrame = function(rgba, delay) {
    this.frames.push(new Frame(rgba, delay))
}

function Frame(rgba, delay) {
    if (!(this instanceof Frame)) {
        return new Frame(rgba, delay)
    }
    this.data = rgba
    this.delay = delay
}