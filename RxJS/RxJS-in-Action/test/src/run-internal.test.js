let Rx = require('rxjs/Rx');
let expect = require('chai').expect;

function assertDeepEqual(actual, expected) {
    expect(actual).to.deep.equal(expected);
}

const isEven = num => num % 2 === 0;
const square = num => num * num;
const add = (a, b) => a + b;

const runInterval = (source$) =>
    source$
        .take(10)
        .filter(isEven)
        .map(square)
        .reduce(add);


it('Should square and add even numbers', function () {
    let scheduler = new Rx.TestScheduler(assertDeepEqual);

    let source = scheduler.createColdObservable(
        '-1-2-3-4-5-6-7-8-9-|');

    let expected = '-------------------(s-|';

    let r = runInterval(source);

    scheduler.expectObservable(r).toBe(expected, {'s': 120});

    scheduler.flush();
});