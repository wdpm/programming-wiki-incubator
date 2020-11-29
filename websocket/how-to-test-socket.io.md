# How to test socket.io

First, Postman can't do it. alternative GUI tools:
- Chrome extension "SocketIo Plugin"
- write your own nodejs code to test, for example
```
const io = require("socket.io-client");
const socket = io('https://api.vtbs.moe');

socket.on('info', (infos) => {
    console.log(`infos.length=${infos.length}`)
  }
)
```
