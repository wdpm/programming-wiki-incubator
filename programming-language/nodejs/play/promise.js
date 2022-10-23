// macrotask in next event loop
setTimeout(function () {
    console.log(1)
}, 0);

new Promise(function executor(resolve) {
    console.log(2);
    for (var i = 0; i < 10000; i++) {
        i == 9999 && resolve();
    }
    console.log(3);
}).then(function () {
    // in the end of current event loop
    console.log(4);
});

// main thread
console.log(5);

// 2 3 5 4 1