var exec = require('child_process').exec
var os = require('os')
var fs = require('fs')
var path = require('path')
var util = require('util')

// freeware nircmd http://www.nirsoft.net/utils/nircmd.html
var nircmdc = path.resolve(__dirname, '../bin/nircmdc.exe')

function captureCommand (path, mode, format) {
  if (process.env.CAPTURE_COMMAND) {
    return util.format(process.env.CAPTURE_COMMAND, path)
  } else {
    switch (os.platform()) {
      case 'win32':
        return '"' + nircmdc + '" savescreenshot ' + path
      case 'freebsd':
        return 'scrot -s ' + path
      case 'darwin':
        if (mode == 1) {
          return `screencapture -x -i -o -t ${format} '${path}'`
        }
        else if (mode == 2) {
          return `screencapture -x -w -o -t ${format} '${path}'` 
        }
        else if (mode == 3) {
          return `screencapture -x -C -o -t ${format} '${path}'`
        }
      case 'linux':
        return 'import ' + path
      default:
        throw new Error('unsupported platform')
    }
  }
}

exports.capture = function (options, callback) {
  var filePath = options.filePath;
  var format = options.format || "png";
  var mode = options.mode || 1;
  exec(captureCommand(filePath, mode, format), function (err) {
    // nircmd always exits with err even though it works
    if (err && os.platform() !== 'win32') callback(err)
    fs.exists(filePath, function (exists) {
      // check exists for success/fail instead
      if (!exists) {
        return callback(new Error('Screenshot failed'))
      }
      callback(null, filePath)
    })
  })
}
