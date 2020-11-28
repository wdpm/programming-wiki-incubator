const websocket = new WebSocket('ws://localhost:1337');

let sub = Rx.Observable.fromEvent(websocket, 'message')
    .map(msg => JSON.parse(msg.data))
    .pluck('msg')
    .subscribe(console.log);

websocket.onclose = () => sub.unsubscribe();