let Rx = require('rxjs/Rx');
let expect = require('chai').expect;

describe('Adding numbers', function () {
    it('Should add numbers together', function () {
        const adder = (total, delta) => total + delta;
        Rx.Observable.from([1, 2, 3, 4, 5, 6, 7, 8, 9])
            .reduce(adder)
            .subscribe(total => {
                expect(total).to.equal(45);
            });
    });

    it('Should add numbers together with delay', function () {
        Rx.Observable.from([1, 2, 3, 4, 5, 6, 7, 8, 9])
            .reduce((total, delta) => total + delta)
            .delay(1000)
            .subscribe(total => {
                expect(total).to.equal(45);
            });
    });
});