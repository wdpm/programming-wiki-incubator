// macro task in queue. Not blocking main thread
function test() {
    setTimeout(() => test(), 0);
}

test()

setImmediate(() => {
    console.log('setImmediate');
})

// setImmediate