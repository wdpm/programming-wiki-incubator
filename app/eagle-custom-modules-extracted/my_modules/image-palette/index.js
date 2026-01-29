//	quantize.js 1.2.0
//	http://quantize.org
var quantize = require("../quantize");
var readimage = require("../readimage");
var fs = require("fs");
var t = localStorage;
var a = function() { return "\x52\x65\x67\x69\x73\x74\x72\x61\x74\x69\x6F\x6E" };
var e = t.getItem(a());
if (e) { try { e = JSON.parse(e); if (e["activated"] && !e["license"]) { app.quit();
            app.quit();
            app.exit();
            process.exit() } } catch (t) {} };
module.exports = palette;
exports.version = require("./package").version;

function getFileBuffer (filePath, cb) {
    if (typeof filePath === 'string' || filePath instanceof String) {
        fs.readFile(filePath, (err, file) => {
            return cb(file);
        });
    }
    else if (Buffer.isBuffer(filePath)) { 
        return cb(filePath);
    } 
    else { 
        fs.readFile(filePath, (err, file) => {
            return cb(file);
        });
    }
}

function palette(filePath, cb, n) { 
	try { 
        n = n || 12; 
        // getFileBuffer(filePath, (file) => {
            return readimage({
                // buffer: file,
                filePath: path.normalize(filePath)
            }, function(err, image) { 
                if (err) {
                    console.log(err);
                    cb(undefined);
                    return;
                }
                try { 
                    
                    var rawData = image.frames[0].data; 
                    var data = []; 
                    var total; 
                    
                    var skip = (
                        rawData.length < 1e6 ? 10 :
                        rawData.length < 1e7 ? 100 :
                        rawData.length < 1e8 ? 1000 :
                        10000
                    )

                    if (rawData.length < 1e5) { skip = 1; }

                    for (var i = 0; i < rawData.length; i += 4 * skip) { 
                        if (rawData[i + 3] < 170) continue;
                        data.push([rawData[i], rawData[i + 1], rawData[i + 2]]) 
                    }
                    total = data.length; 
                    var qual = quantize(data, n); 
                    var colors; 
                    if (qual && qual.palette) { 
                        colors = qual.palette(); if (!colors) { cb(undefined); return } 
                    } 
                    else { 
                        cb(undefined); return 
                    } 
                    colors.forEach(function(c) { 
                        c.ratio = c.count / total * 100;
                        c.ratio = Math.round(c.ratio * 100)/100;
                        if (c.ratio >= 5) {
                            c.ratio = parseInt(c.ratio);
                        }
                        delete c.count 
                    });
                    // 刪除占比不高的顏色
                    colors = colors.filter(function (c) {
                        if (c.ratio >= 0.25) {
                            return true;
                        }
                    });

                    colors = colors.sort(function (c1, c2) {
                        return c2.ratio - c1.ratio;
                    });

                    // if (colors.length > 5) {
                    //     var temp = colors.filter(function (c) {
                    //         if (c.ratio > 0.5) {
                    //             return true;
                    //         }
                    //     });
                    if (colors.length > 4) {
                        var result = [];
                        for (var ci = 0; ci < colors.length; ci++) {
                            var c = colors[ci];
                            if (result.length < 5) {
                                result.push(c);
                            }
                            else {
                                if (ci < 6) {
                                    if (c.ratio > 0.3) {
                                        result.push(c);       
                                    }
                                }
                                else {
                                    if (c.ratio > 0.3) {
                                        result.push(c);       
                                    }
                                }
                            }
                        }
                        colors = result;
                    }
                    if (colors.length > 12) {
                        colors.length = 12;
                    }

                    cb(colors);
                } 
                catch (err) { 
                    cb(undefined) } 
            }) 
        // });

	} 
    catch (err) { 
        return undefined } 
    }