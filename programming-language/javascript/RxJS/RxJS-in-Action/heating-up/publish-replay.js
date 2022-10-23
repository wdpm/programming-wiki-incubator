const source$ = Rx.Observable.interval(1000)
    .take(10)
    .do(num => {
        console.log(`Running some code with ${num}`);
    });
// Creates an observable that can store two past events
// and reemit them to any new subscribers
const published$ = source$.publishReplay(2);

// Subscriber A connects subscribers immediately, and begins receiving events from count 0.
published$.subscribe(createObserver('SubA'));

// Subscribing 5 seconds later,subscriber B should begin receiving events starting with
// the number 4, but because of the replay it will first receive 2 and 3.
setTimeout(() => {
    published$.subscribe(createObserver('SubB'));
}, 5000)

published$.connect()