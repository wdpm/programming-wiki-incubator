# Stream Usage
> https://github.com/workshopper/stream-adventure

- pipe
```js
const fs = require('fs')
const file = process.argv[2]

fs.createReadStream(file).pipe(process.stdout)
```
---

- input/output
```js
process.stdin.pipe(process.stdout)
```
--- 

- Readable streams
```js
const { Readable } = require('stream')

class ReadableStream extends Readable {
  _read (size) {
  }
}

const stream = new ReadableStream()
stream.push(process.argv[2])
stream.pipe(process.stdout)
```

Consuming a Readable Stream
- readable.pipe(writable), attaching Writable stream to the readable, cause it to switch automatically into flowing mode and push all of its data to the attached Writable.

- readable.on('readable', ...), here the stream (readable) is in paused mode and have to use the read(size) method for start consuming the data.

- readable.on('data', ...), adding the data event handler switch the stream to a flowing mode. We can pause and resume the stream by using pause() and resume() methods respectively. This is useful when you need to do some time-consuming action with the stream's data (such as writing to a database)

Adding data to stream
- You can use the push() method to add content into the readable internal Buffer.

Docs
- https://github.com/workshopper/stream-adventure/blob/master/problems/read_it/problem.md
---

- Writable Streams
```js
const { Writable } = require('stream')

class MyWritable extends Writable {
  _write (chunk, encoding, callback) {
    console.log(`writing: ${chunk.toString()}`)
    callback()
  }
}

const stream = new MyWritable()
process.stdin.pipe(stream)
```
---

- Transform
```js
const through = require('through2')

const tr = through(function (buf, _, next) {
  // call this.push() to produce output data 
  this.push(buf.toString().toUpperCase())
  // and call next() when you're ready to receive the next chunk
  next()
})
// pipe process.stdin -> your transform stream -> process.stdout
process.stdin.pipe(tr).pipe(process.stdout)
```
- lines
```js
const through = require('through2')
const split2 = require('split2')

let lineCount = 0
const tr = through(function (buf, _, next) {
  const line = buf.toString()
  this.push(lineCount % 2 === 0
    ? line.toLowerCase() + '\n'
    : line.toUpperCase() + '\n'
  )
  lineCount++
  next()
})
process.stdin
  .pipe(split2())
  .pipe(tr)
  .pipe(process.stdout)
```
---

- concat
```js
const concat = require('concat-stream')

process.stdin.pipe(concat(function (src) {
  const s = src.toString().split('').reverse().join('')
  process.stdout.write(s)
}))
```
---

- http server
```js
const http = require('http')
const through = require('through2')

const server = http.createServer(function (req, res) {
  //req -> tr -> res
  if (req.method === 'POST') {
    req.pipe(through(function (buf, _, next) {
      this.push(buf.toString().toUpperCase())
      next()
    })).pipe(res)
  } else res.end('send me a POST\n')
})
server.listen(parseInt(process.argv[2]))
```
test
```
$ node server.js 8000 &
$ echo hack the planet | curl -d@- http://localhost:8000
```
---
- http client
```js
const { request } = require('http')

const options = { method: 'POST' }
const req = request('http://localhost:8099', options, (res) => {
  res.pipe(process.stdout)
})
process.stdin.pipe(req)
// stdin -> req -> res -> stdout
```
---
- websockets client
```js
const WebSocket = require('ws')

const ws = new WebSocket('ws://localhost:8099')
const stream = WebSocket.createWebSocketStream(ws)
// client send hello to server side
stream.write('hello\n')
// wait for server side response
stream.pipe(process.stdout)
```
how to write the server side code: https://github.com/websockets/ws

---
- HTML stream
```js
const trumpet = require('trumpet')
const through = require('through2')
const tr = trumpet()

const loud = tr.select('.loud').createStream()
loud.pipe(through(function (buf, _, next) {
  this.push(buf.toString().toUpperCase())
  next()
})).pipe(loud)

process.stdin.pipe(tr).pipe(process.stdout)
```
input
```
    <p>
      How we are offstage in a great livermore, testing weather stations, or any
      station so conceived in altogether fitting and little note, nor long
      remember, they who fought here and take increased devoation,
      that <span class="loud">government love the people, beside the people,
      four of the people, shall not perish from this earth.</span>
    </p>
```
output
```
    <p>
      How we are offstage in a great livermore, testing weather stations, or any
      station so conceived in altogether fitting and little note, nor long
      remember, they who fought here and take increased devoation,
      that <span class="loud">GOVERNMENT LOVE THE PEOPLE, BESIDE THE PEOPLE,
      FOUR OF THE PEOPLE, SHALL NOT PERISH FROM THIS EARTH.</span>
    </p>
```
---
- duplexer
Write a program that exports a function that spawns a process from a `cmd` string and an `args` array 
and returns a single duplex stream joining together the stdin and stdout of the spawned process.
```js
const { spawn } = require('child_process')
const duplexer = require('duplexer2')

module.exports = function (cmd, args) {
  const ps = spawn(cmd, args)
  return duplexer(ps.stdin, ps.stdout)
}
```
---
- duplexer-redux
```js
const duplexer = require('duplexer2')
const through = require('through2').obj

// counter: a readable stream
module.exports = function (counter) {
  const counts = {}
  const input = through(write, end)
  return duplexer({ objectMode: true }, input, counter)

  function write (row, _, next) {
    counts[row.country] = (counts[row.country] || 0) + 1
    next()
  }
  function end (done) {
    counter.setCounts(counts)
    done()
  }
}
```
- combiner
```js
const combine = require('stream-combiner')
const stream = combine(a, b, c, d)
// internally do a.pipe(b).pipe(c).pipe(d)
```
---
- crypto
```js
const crypto = require('crypto')

// Your program will be given a passphrase on process.argv[2], an initialization value on process.argv[3] 
// and 'aes256' encrypted data will be written to stdin.
process.stdin
  .pipe(crypto.createDecipheriv('aes256', process.argv[2], process.argv[3]))
  .pipe(process.stdout)
```
- secretz
```js
const crypto = require('crypto')
const tar = require('tar')
const concat = require('concat-stream')

const parser = new tar.Parse()
parser.on('entry', function (e) {
  if (e.type !== 'File') return e.resume()

  const h = crypto.createHash('md5', { encoding: 'hex' })
  e.pipe(h).pipe(concat(function (hash) {
    console.log(hash + ' ' + e.path)
  }))
})

const cipher = process.argv[2]
const key = process.argv[3]
const iv = process.argv[4]
process.stdin
  .pipe(crypto.createDecipheriv(cipher, key, iv))
  .pipe(parser)
```