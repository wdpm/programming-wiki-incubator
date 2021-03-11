const path = require("path");

console.log(path.win32.sep)
console.log(path.win32.normalize('C:\\temp\\foo\\bar\\..'))
console.log(path.win32.basename('C:\\temp\\myfile.html'))
console.log(path.win32.join('/asdf', '/test.html'))
console.log(path.win32.relative('C:\\a', 'c:\\b'))
console.log(path.win32.isAbsolute('C:\\foo\\..'))
let PATH = process.env.PATH;
// console.log(PATH)
console.log(PATH.split(path.win32.delimiter))

let parsedPath = path.parse('C:\\path\\dir\\file.txt');
console.log(parsedPath)