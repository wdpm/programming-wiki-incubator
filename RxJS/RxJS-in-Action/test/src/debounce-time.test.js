let Rx = require('rxjs/Rx');
let expect = require('chai').expect;

function assertDeepEqual(actual, expected) {
    expect(actual).to.deep.equal(expected);
}

describe('Marble test with debounceTime', function () {
    it('Should delay all element by the specified time', function () {
        let scheduler = new Rx.TestScheduler(assertDeepEqual);

        let source = scheduler.createHotObservable(
            '-a--------b------c----|');

        let expected = '------a--------b------(c|)';

        let r = source.debounceTime(50, scheduler);
        scheduler.expectObservable(r).toBe(expected);
        scheduler.flush();
    });
});