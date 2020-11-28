const source$ = Rx.Observable.interval(1000)
    .take(10)
    .do(num => {
        console.log(`Running some code with ${num}`);
    });
const shared$ = source$.share();
shared$.subscribe(createObserver('SourceA'));
shared$.subscribe(createObserver('SourceB'));

function createObserver(tag) {
    return {
        next: x => {
            console.log(`Next: ${tag} ${x}`);
        },
        error: err => {
            console.log(`Error: ${err}`);
        },
        complete: () => {
            console.log('Completed');
        }
    };
}