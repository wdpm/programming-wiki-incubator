{
    // microtask in current call stack. blocking
    function test() {
        return process.nextTick(() => test());
    }

    test();

    setImmediate(() => {
        console.log('setImmediate');
    })
}