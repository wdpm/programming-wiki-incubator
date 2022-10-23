let Rx = require('rxjs/Rx');
let expect = require('chai').expect;

it('Emits values synchronously on default scheduler', function () {
    let temp = [];
    Rx.Observable.range(1, 5)
        .do([].push.bind(temp))
        .subscribe(value => {
            expect(temp).to.have.length(value);
            expect(temp).to.contain(value);
        });
});

it('Emits values on an asynchronous scheduler', function (done) {
    let temp = [];
    Rx.Observable.range(1, 5, Rx.Scheduler.async)
        .do(console.log)
        .do([].push.bind(temp))
        .subscribe(value => {
            expect(temp).to.have.length(value);
            expect(temp).to.contain(value);
        }, done, done);
});

